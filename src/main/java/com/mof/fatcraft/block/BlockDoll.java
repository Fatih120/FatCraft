package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.entity.tile.TileEntityDoll;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockDoll extends BlockContainer {
    private static final String name = "Doll";
    public BlockDoll() {
        super(Material.cloth);
        this.setBlockName(name);
        this.setBlockTextureName(Main.MODID+":charcoal_ore");
        this.setBlockTextureName("wool_colored");
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return -1;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityDoll();
    }
}
