package com.mof.fatcraft.entity;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.events.MoonTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityTeeterboard extends Entity {
    private static final float LENGTH = 5.0F;
    private static final float WIDTH = 1.0F;
    private static final float HEIGHT = 0.25F;
    private static final float MAX_TILT = 15.0F; // Maximum tilt angle in degrees
    private static final int DW_TILT = 20;
    private static final double MOON_LAUNCH_SPEED = 1.0F; // Adjust this value as needed


    public EntityTeeterboard(World world) {
        super(world);
        this.setSize(LENGTH, HEIGHT);
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(DW_TILT, 0.0F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            updateTiltAndLaunch();
        }
    }

    private static final float LAUNCH_FORCE = 1.0F; // Adjust this value to change launch strength
    private static final int TILT_COOLDOWN = 10; // ticks
    private int tiltCooldownTimer = 0;
    private Map<Entity, Integer> entityLaunchCooldowns = new HashMap<>();

    private void updateTiltAndLaunch() {
        AxisAlignedBB fullBoard = this.getBoundingBox().expand(0, 1, 0);
        AxisAlignedBB leftSide = fullBoard.copy();
        AxisAlignedBB rightSide = fullBoard.copy();
        leftSide.maxX = this.posX;
        rightSide.minX = this.posX;

        List<Entity> leftEntities = this.worldObj.getEntitiesWithinAABB(Entity.class, leftSide);
        List<Entity> rightEntities = this.worldObj.getEntitiesWithinAABB(Entity.class, rightSide);

        boolean leftPressed = !leftEntities.isEmpty();
        boolean rightPressed = !rightEntities.isEmpty();

        float targetTilt = 0;
        if (tiltCooldownTimer <= 0) {
            if (leftPressed && !rightPressed) {
                targetTilt = MAX_TILT; // Inverted for visual purposes
            } else if (rightPressed && !leftPressed) {
                targetTilt = -MAX_TILT; // Inverted for visual purposes
            } else if (leftPressed && rightPressed) {
                double leftActivity = calculateActivity(leftEntities);
                double rightActivity = calculateActivity(rightEntities);

                if (leftActivity > rightActivity) {
                    targetTilt = MAX_TILT * (float) (leftActivity / (leftActivity + rightActivity));
                } else if (rightActivity > leftActivity) {
                    targetTilt = -MAX_TILT * (float) (rightActivity / (leftActivity + rightActivity));
                }
            }
        }

        float currentTilt = this.dataWatcher.getWatchableObjectFloat(DW_TILT);
        float newTilt = currentTilt + (targetTilt - currentTilt) * 0.2F;
        this.dataWatcher.updateObject(DW_TILT, newTilt);

        if (Math.abs(newTilt) > 5.0F) {
            tiltCooldownTimer = TILT_COOLDOWN;
        }

        if (Math.abs(newTilt) > 1.0F) {
            List<Entity> entitiesToLaunch = (newTilt < 0) ? leftEntities : rightEntities;
            for (Entity entity : entitiesToLaunch) {
                if ((entity.onGround || entity.isCollidedVertically) && !entityLaunchCooldowns.containsKey(entity)) {
                    launchEntity(entity);
                    entityLaunchCooldowns.put(entity, 20);
                }
            }
        }

        tiltCooldownTimer = Math.max(0, tiltCooldownTimer - 1);
        for (Iterator<Map.Entry<Entity, Integer>> it = entityLaunchCooldowns.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Entity, Integer> entry = it.next();
            int cooldown = entry.getValue() - 1;
            if (cooldown <= 0) {
                it.remove();
            } else {
                entry.setValue(cooldown);
            }
        }

//        Main.LOG.info("Teeterboard active. Left: " + leftPressed + ", Right: " + rightPressed + ", Tilt: " + newTilt);
    }

    private double calculateActivity(List<Entity> entities) {
        double totalActivity = 0;
        for (Entity entity : entities) {
            totalActivity += Math.abs(entity.motionY);
            totalActivity += entity.onGround ? 1 : 0;
        }
        return totalActivity;
    }




    private void launchEntity(Entity entity) {
        entity.motionY = LAUNCH_FORCE;
        if (entity instanceof EntityPlayerMP) {
            ((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(entity));
        }
        entity.fallDistance = 0;

        // Check for moon launch if needed
        if (LAUNCH_FORCE >= MOON_LAUNCH_SPEED) {
            teleportToMoon(entity);
        }
    }



    private void teleportToMoon(Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            int moonDimensionId = 30; // You'll need to set this up in your mod's configuration
            if (player.dimension != moonDimensionId) {
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, moonDimensionId, new MoonTeleporter(MinecraftServer.getServer().worldServerForDimension(moonDimensionId)));
            }
        }
        // For non-player entities, you might want to remove them or handle differently
    }




    private double getEntityWeight(Entity entity) {
//        if (entity instanceof EntityPlayer) return 80;
        if (entity instanceof EntityItem) return 1;
        return entity.height * entity.width * 20; // Approximate weight based on size
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return AxisAlignedBB.getBoundingBox(
            this.posX - LENGTH/2, this.posY, this.posZ - WIDTH/2,
            this.posX + LENGTH/2, this.posY + HEIGHT, this.posZ + WIDTH/2
        );
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompound) {
        this.dataWatcher.updateObject(DW_TILT, tagCompound.getFloat("Tilt"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setFloat("Tilt", this.dataWatcher.getWatchableObjectFloat(DW_TILT));
    }

    public float getTilt() {
        return this.dataWatcher.getWatchableObjectFloat(DW_TILT);
    }


}
