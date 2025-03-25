package com.mof.fatcraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBabyCreeper extends EntityMob {
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;
    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;
    private int fuseTime = 30;
    /** Explosion radius for this creeper. */
    private int explosionRadius = 0;

    public EntityBabyCreeper(World p_i1733_1_) {
        super(p_i1733_1_);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanic(this, 0.8D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.400D);
    }

//    public boolean isPeacefulCreature() { return true; }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled() {
        return true;
    }

//    /**
//     * The number of iterations PathFinder.getSafePoint will execute before giving up.
//     */
//    public int getMaxSafePointTries() {
//        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
//    }

//    /**
//     * Called when the mob is falling. Calculates and applies fall damage.
//     */
//    protected void fall(float p_70069_1_) {
//        super.fall(p_70069_1_);
//        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + p_70069_1_ * 1.5F);
//
//        if (this.timeSinceIgnited > this.fuseTime - 5) {
//            this.timeSinceIgnited = this.fuseTime - 5;
//        }
//    }

//    protected void entityInit() {
//        super.entityInit();
//        this.dataWatcher.addObject(26, Byte.valueOf((byte) - 1)); //explosion state
//        this.dataWatcher.addObject(27, Byte.valueOf((byte)0)); // elec powered
//        this.dataWatcher.addObject(28, Byte.valueOf((byte)0));
//    }

//    /**
//     * (abstract) Protected helper method to write subclass entity data to NBT.
//     */
//    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
//        super.writeEntityToNBT(p_70014_1_);
//
//        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
//            p_70014_1_.setBoolean("powered", true);
//        }
//
//        p_70014_1_.setShort("Fuse", (short)this.fuseTime);
//        p_70014_1_.setByte("ExplosionRadius", (byte)this.explosionRadius);
//        p_70014_1_.setBoolean("ignited", this.func_146078_ca());
//    }

//    /**
//     * (abstract) Protected helper method to read subclass entity data from NBT.
//     */
//    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
//        super.readEntityFromNBT(p_70037_1_);
//        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(p_70037_1_.getBoolean("powered") ? 1 : 0)));
//
//        if (p_70037_1_.hasKey("Fuse", 99)) {
//            this.fuseTime = p_70037_1_.getShort("Fuse");
//        }
//
//        if (p_70037_1_.hasKey("ExplosionRadius", 99)) {
//            this.explosionRadius = p_70037_1_.getByte("ExplosionRadius");
//        }
//
//        if (p_70037_1_.getBoolean("ignited")) {
//            this.func_146079_cb();
//        }
//    }

@Override
public boolean interact(EntityPlayer player) {
    if (!this.worldObj.isRemote) {
        this.timeSinceIgnited++;
        if (this.timeSinceIgnited >= this.fuseTime) {
            this.asplode();
        }
    }
    return true;
}

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            EntityPlayer closestPlayer = this.worldObj.getClosestPlayerToEntity(this, 3);
            if (closestPlayer != null && this.rand.nextFloat() < 0.01F) {
                this.timeSinceIgnited++;
                if (this.timeSinceIgnited >= this.fuseTime) {
                    this.asplode();
                }
            } else {
                this.timeSinceIgnited = 0;
            }
        }
        super.onUpdate();
    }


    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

//    /**
//     * Called when the mob's health reaches 0.
//     */
//    public void onDeath(DamageSource p_70645_1_) {
//        super.onDeath(p_70645_1_);

//        if (p_70645_1_.getEntity() instanceof EntitySkeleton) {
//            int i = Item.getIdFromItem(Items.record_13);
//            int j = Item.getIdFromItem(Items.record_wait);
//            int k = i + this.rand.nextInt(j - i + 1);
//            this.dropItem(Item.getItemById(k), 1);
//    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        return false;
    }

//    /**
//     * Returns true if the creeper is powered by a lightning bolt.
//     */
//    public boolean getPowered() {
//        return this.dataWatcher.getWatchableObjectByte(17) == 1;
//    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
    }

    protected Item getDropItem() {
        return Items.gunpowder;
    }

//    /**
//     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
//     */
//    public int getCreeperState() {
//        return this.dataWatcher.getWatchableObjectByte(16);
//    }
//
//    /**
//     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
//     */
//    public void setCreeperState(int p_70829_1_) {
//        this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_70829_1_));
//    }

//    /**
//     * Called when a lightning bolt hits the entity.
//     */
//    public void onStruckByLightning(EntityLightningBolt p_70077_1_) {
//        super.onStruckByLightning(p_70077_1_);
//        this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
//    }

//    protected boolean interact(EntityPlayer p_70085_1_) {
//        ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
//
//        if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
//            this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
//            p_70085_1_.swingItem();
//
//            if (!this.worldObj.isRemote) {
//                this.func_146079_cb();
//                itemstack.damageItem(1, p_70085_1_);
//                return true;
//            }
//        }
//
//        return super.interact(p_70085_1_);
//    }

    private void asplode() {
        this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        this.motionY = 0.5; // Adjust this value to change the height of the pop
        this.isAirBorne = true;
    }

//    public boolean getState() {
//        return this.dataWatcher.getWatchableObjectByte(18) != 0;
//    }
//
//    public void setState() {
//        this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
//    }
}
