package com.mof.fatcraft.events;

import com.mof.fatcraft.entity.EntityBrickThrown;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import static com.mof.fatcraft.Main.LOG;

public class BrickThrowHandler {
    @SubscribeEvent
    public void onItemUse(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR ||
            event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {

            EntityPlayer player = event.entityPlayer;
            ItemStack heldItem = player.getCurrentEquippedItem();

            if (heldItem != null &&
                ( heldItem.getItem() == Items.brick || heldItem.getItem() == Items.netherbrick ) ) {
//                LOG.info("Brick thrown!");
                player.worldObj.playSoundAtEntity(player, "step.stone", 0.5F, 0.4F / (player.worldObj.rand.nextFloat() * 0.4F + 0.8F));

                if (!player.worldObj.isRemote) {
                    EntityBrickThrown brickThrown = new EntityBrickThrown(player.worldObj, player);
                    brickThrown.setThrowableHeading(brickThrown.motionX, brickThrown.motionY, brickThrown.motionZ, 0.75F, 1.1F);
                    player.worldObj.spawnEntityInWorld(brickThrown);
                }

                event.useItem = Event.Result.DENY;

                if (!player.capabilities.isCreativeMode) {
                    --heldItem.stackSize;
                    if (heldItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }
            }
        }
    }
}
