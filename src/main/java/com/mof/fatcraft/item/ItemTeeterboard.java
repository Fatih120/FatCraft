package com.mof.fatcraft.item;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.entity.EntityTeeterboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.mof.fatcraft.Main.tabFatCraft;

public class ItemTeeterboard extends Item {

    public ItemTeeterboard() {
        super();
        this.setUnlocalizedName("teeterboard");
        this.setTextureName(Main.MODID+"teeterboard");
        this.setCreativeTab(tabFatCraft);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            EntityTeeterboard teeterboard = new EntityTeeterboard(world);
            teeterboard.setPosition(x + 0.5, y + 1, z + 0.5);
            world.spawnEntityInWorld(teeterboard);

            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
        }
        return true;
    }
}
