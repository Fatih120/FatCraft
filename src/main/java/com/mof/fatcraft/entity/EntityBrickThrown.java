package com.mof.fatcraft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBrickThrown extends EntityThrowable
{
    public EntityBrickThrown(World world)
    {
        super(world);
    }

    public EntityBrickThrown(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
    }

    public EntityBrickThrown(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (movingObjectPosition.entityHit != null)
        {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 6.0F);
        }

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("blockcrack_45_0", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
