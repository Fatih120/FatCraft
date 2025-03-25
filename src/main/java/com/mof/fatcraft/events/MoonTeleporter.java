package com.mof.fatcraft.events;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MoonTeleporter extends Teleporter {
    public MoonTeleporter(WorldServer world) {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float yaw) {
        int moonSurfaceY = 256;
        entity.setPosition(x, moonSurfaceY, z);
        entity.motionX = entity.motionY = entity.motionZ = 0.0;
    }

}
