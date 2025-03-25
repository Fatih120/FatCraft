package com.mof.fatcraft.events;

import com.mof.fatcraft.item.ItemQuiver;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class QuiverHandler {
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack pickedItem = event.item.getEntityItem();

        if (pickedItem.getItem() == Items.arrow) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof ItemQuiver) {
                    ItemQuiver quiver = (ItemQuiver) stack.getItem();
                    int arrowsToAdd = pickedItem.stackSize;
                    quiver.addArrows(stack, arrowsToAdd);
                    pickedItem.stackSize = 0;
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

}
