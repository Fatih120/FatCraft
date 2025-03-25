package com.mof.fatcraft.block;

import com.mof.fatcraft.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static com.mof.fatcraft.Main.tabFatCraft;

public class BlockCharcoalOre extends Block {
    public BlockCharcoalOre() {
        super(Material.rock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypePiston);
        this.setBlockName("oreCharcoal");
        this.setBlockTextureName(Main.MODID+":charcoal_ore");
        this.setCreativeTab(tabFatCraft);
    }

    public Item getItemDropped(int meta, Random random, int fortune) { return Items.coal; }

    // Drops with damage 1 which is Charcoal
    public int damageDropped(int meta)
    {
        return 1;
    }

    // Nether
    public int quantityDroppedWithBonus(int maxBonus, Random random)
    {
        if (maxBonus > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, maxBonus))
        {
            int j = random.nextInt(maxBonus + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    public void dropBlockAsItemWithChance(World worldIn, int x, int y, int z, int meta, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, x, y, z, meta, chance, fortune);
    }

    // Identical to Coal
    private Random rand = new Random();
    @Override
    public int getExpDrop(IBlockAccess worldIn, int meta, int fortune)
    {
        int j1 = 0;
        j1 = MathHelper.getRandomIntegerInRange(rand, 0, 2);
        return j1;
    }
}
