package com.mof.fatcraft.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class SinkingHandler {

    private Random random = new Random();

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (player.isInWater() && player.isSneaking()) {
                // Increase downward velocity when sneaking in water
                player.motionY -= 0.05; // Adjust this value to change sinking speed

                // Spawn bubble particles
                spawnBubbleParticles(player);
            }
        }
    }

    private void spawnBubbleParticles(EntityPlayer player) {
        for (int i = 0; i < 5; i++) { // Spawn 5 particles per tick
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = (random.nextDouble() * 0.5);
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;

            player.worldObj.spawnParticle("bubble",
                player.posX + offsetX,
                player.posY + offsetY,
                player.posZ + offsetZ,
                0, 0.2, 0); // Upward velocity for bubbles
        }
    }
}
