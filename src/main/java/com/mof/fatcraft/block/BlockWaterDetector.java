package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static com.mof.fatcraft.Main.tabFatCraft;


public class BlockWaterDetector extends Block {
    public BlockWaterDetector() {
        super(Material.rock);
        this.setBlockName("waterDetector");
        this.setBlockTextureName(Main.MODID+":water_detector");
        this.setTickRandomly(true);
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            updatePowerState(world, x, y, z);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);

        // Notify neighbors of the block change
        world.notifyBlocksOfNeighborChange(x, y, z, this);
        for (int i = 0; i < 6; i++) {
            world.notifyBlocksOfNeighborChange(
                x + Facing.offsetsXForSide[i],
                y + Facing.offsetsYForSide[i],
                z + Facing.offsetsZForSide[i],
                this
            );
        }
    }

    private void updatePowerState(World world, int x, int y, int z) {
        boolean shouldBePowered = isWaterAdjacent(world, x, y, z);
        int currentMetadata = world.getBlockMetadata(x, y, z);
        boolean isPowered = (currentMetadata & 1) != 0;

        if (isPowered != shouldBePowered) {
            world.setBlockMetadataWithNotify(x, y, z, shouldBePowered ? 1 : 0, 3);
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            for (int i = 0; i < 6; i++) {
                world.notifyBlocksOfNeighborChange(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i], this);
            }
        }
    }

    private boolean isWaterAdjacent(World world, int x, int y, int z) {
        for (int i = 0; i < 6; i++) {
            if (world.getBlock(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i]).getMaterial() == Material.water) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        return (world.getBlockMetadata(x, y, z) & 1) != 0 ? 15 : 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return (world.getBlockMetadata(x, y, z) & 1) != 0 ? 15 : 0;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (!world.isRemote) {
            updatePowerState(world, x, y, z);
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            updatePowerState(world, x, y, z);
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        if (!world.isRemote) {
            updatePowerState(world, x, y, z);
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
    }

    @Override
    public int tickRate(World world) {
        return 20;
    }
}
