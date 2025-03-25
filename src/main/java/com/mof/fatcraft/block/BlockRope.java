package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockRope extends Block {
    public BlockRope() {
        super(Material.web);
        this.setBlockName("rope");
        this.setBlockTextureName(Main.MODID + ":rope");
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public boolean isOpaqueCube() { return false; }

    @Override
    public boolean renderAsNormalBlock() {
        return false; }

    @Override
    public int getRenderType() {
        return 1; // cross
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getCurrentEquippedItem();
        if (heldItem != null && heldItem.getItem() == Item.getItemFromBlock(this)) {
            int newY = y - 1;
            while (world.isAirBlock(x, newY, z)) {
                world.setBlock(x, newY, z, this);
                newY--;
                if (!player.capabilities.isCreativeMode) {
                    heldItem.stackSize--;
                    if (heldItem.stackSize <= 0) break;
                }
            }
            return true;
        }
        return false;
    }

    // TODO this sucks
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.isSneaking()) {
                player.motionY = 0;
            } else {
                float pitch = player.rotationPitch;

                boolean isLookingLevel = pitch > -20 && pitch < 20;

                if (isLookingLevel) {
                    // Stop vertical movement when looking relatively level
                    player.motionY = 0;
                } else if (pitch <= -20) {
                    player.motionY = 0.3;
                } else if (pitch >= 20) {
                    player.motionY = -0.1;
                }
            }

            player.motionX *= 0.5;
            player.motionZ *= 0.5;

            player.fallDistance = 0.0F;
        }
    }
}
