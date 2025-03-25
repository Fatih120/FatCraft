package com.mof.fatcraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemQuiver extends Item {
    public ItemQuiver() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(256);
        this.setNoRepair();
        this.setUnlocalizedName("quiver");
        this.setTextureName("yourmodid:quiver");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setItemDamage(0);
    }

    public void addArrows(ItemStack quiverStack, int count) {
        int currentArrows = quiverStack.getMaxDamage() - quiverStack.getItemDamage();
        int newArrows = Math.min(currentArrows + count, 256);
        quiverStack.setItemDamage(quiverStack.getMaxDamage() - newArrows);
    }

    public int removeArrows(ItemStack quiverStack, int count) {
        int currentArrows = quiverStack.getMaxDamage() - quiverStack.getItemDamage();
        int removedArrows = Math.min(currentArrows, count);
        quiverStack.setItemDamage(quiverStack.getMaxDamage() - (currentArrows - removedArrows));
        return removedArrows;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int arrowCount = stack.getMaxDamage() - stack.getItemDamage();
        list.add(EnumChatFormatting.GRAY + "Arrows: " + arrowCount + "/256");
    }
}
