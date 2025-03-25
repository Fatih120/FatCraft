package com.mof.fatcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
    @SideOnly(Side.CLIENT)
    public static class EntityBlueFlameFX extends EntityFlameFX {
        public EntityBlueFlameFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.particleRed = 0.0F;
            this.particleGreen = 0.0F;
            this.particleBlue = 1.0F;
        }
    }

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
//    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoll.class, new RenderTEDoll());

}
