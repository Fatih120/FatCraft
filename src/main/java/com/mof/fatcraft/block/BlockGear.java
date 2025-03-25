package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.entity.tile.TileEntityGear;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockGear extends BlockContainer {

    public BlockGear() {
        super(Material.iron);
        this.setBlockName("gear_block");
        this.setBlockTextureName(Main.MODID+":gear");
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityGear();
    }

    @Override
    public boolean renderAsNormalBlock() { return false; }

    @Override
    public boolean isOpaqueCube() { return false; }

    @Override
    public int getRenderType() {
        return -1; // TESR
    }
}
