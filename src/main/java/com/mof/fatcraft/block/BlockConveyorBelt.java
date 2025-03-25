package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockConveyorBelt extends BlockDirectional {

    public BlockConveyorBelt() {
        super(Material.iron);
        this.setBlockName("conveyorBelt");
        this.setBlockTextureName(Main.MODID+":conveyor");
        this.setStepSound(soundTypeMetal);
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        // taken from BlockPumpkin::onBlockPlacedBy
        int l = MathHelper.floor_double(entity.rotationYaw * 4 / 360 + 2.5) % 4;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

    }

    private IIcon frontIcon;
    private IIcon sideIcon;
    private IIcon topIcon;
    private IIcon bottomIcon;

    @Override @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.frontIcon = iconRegister.registerIcon(this.getTextureName() + "_front");
        this.sideIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
        this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_side"); // TODO:
        this.bottomIcon = iconRegister.registerIcon(this.getTextureName() + "_bottom");
        this.blockIcon = this.sideIcon;
    }

    @Override @SideOnly(Side.CLIENT)
    public IIcon getIcon(int unkA, int unkB) {
        // based on BlockPumpkin::getIcon
        return unkA == 1 ? this.topIcon :
            unkA == 0 ? this.bottomIcon :
                unkB == 2 && unkA == 2 ? this.frontIcon :
                    unkB == 3 && unkA == 5 ? this.frontIcon :
                        unkB == 0 && unkA == 3 ? this.frontIcon :
                            unkB == 1 && unkA == 4 ? this.frontIcon :
                                this.sideIcon;
    }

}
