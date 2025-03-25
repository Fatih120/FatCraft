package com.mof.fatcraft.block;

import com.mof.fatcraft.ClientProxy;
import com.mof.fatcraft.Main;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockLapisTorch extends BlockTorch {

    public BlockLapisTorch() {
        super();
        this.setBlockName("lapisTorch");
        this.setBlockTextureName(Main.MODID+":lapis_torch");
        this.setCreativeTab(tabFatCraft);
    }

    //torch
    public MovingObjectPosition collisionRayTrace(World worldIn, int x, int y, int z, Vec3 startVec, Vec3 endVec)
    {
        int l = worldIn.getBlockMetadata(x, y, z) & 7;
        float f = 0.15F;

        if (l == 1)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (l == 2)
        {
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (l == 3)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (l == 4)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
        else
        {
            f = 0.1F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }

        return super.collisionRayTrace(worldIn, x, y, z, startVec, endVec);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random)
    {
        int l = worldIn.getBlockMetadata(x, y, z);
        double d0 = (double)((float)x + 0.5F);
        double d1 = (double)((float)y + 0.7F);
        double d2 = (double)((float)z + 0.5F);
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;

        if (l == 1)
        {
            worldIn.spawnParticle("smoke", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
            spawnBlueFlameFX(worldIn, d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 2)
        {
            worldIn.spawnParticle("smoke", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
            spawnBlueFlameFX(worldIn, d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 3)
        {
            worldIn.spawnParticle("smoke", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
            spawnBlueFlameFX(worldIn, d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 4)
        {
            worldIn.spawnParticle("smoke", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
            spawnBlueFlameFX(worldIn, d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            worldIn.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
            spawnBlueFlameFX(worldIn, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnBlueFlameFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        ClientProxy.EntityBlueFlameFX particle = new ClientProxy.EntityBlueFlameFX(world, x, y, z, motionX, motionY, motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}

