package com.mof.fatcraft.events;

import com.mof.fatcraft.block.BlockLapisTorch;
import com.mof.fatcraft.Main;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LapisLazuliTorchHandler {
    private static final int CHUNK_RADIUS = 2;
    private Map<ChunkCoordIntPair, Integer> torchCountInChunks = new HashMap<>();

    @SubscribeEvent//(priority = EventPriority.HIGHEST)
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.entityLiving instanceof IMob) {
            int chunkX = ((int) event.x) >> 4;
            int chunkZ = ((int) event.z) >> 4;

            if (isNearLapisTorchChunk(chunkX, chunkZ)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    private boolean isNearLapisTorchChunk(int chunkX, int chunkZ) {
        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX + dx, chunkZ + dz);
                if (torchCountInChunks.containsKey(chunk)) {
//                    Main.LOG.info("chunk'd: ({}, {})", chunk.chunkXPos, chunk.chunkZPos);
                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (event.block instanceof BlockLapisTorch) {
            onLapisTorchPlaced(event.world, event.x, event.y, event.z);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.block instanceof BlockLapisTorch) {
            onLapisTorchRemoved(event.world, event.x, event.y, event.z);
        }
    }

    public void onLapisTorchPlaced(World world, int x, int y, int z) {
        ChunkCoordIntPair chunk = new ChunkCoordIntPair(x >> 4, z >> 4);
        torchCountInChunks.put(chunk, torchCountInChunks.getOrDefault(chunk, 0) + 1);
    }

    public void onLapisTorchRemoved(World world, int x, int y, int z) {
        ChunkCoordIntPair chunk = new ChunkCoordIntPair(x >> 4, z >> 4);
        int count = torchCountInChunks.getOrDefault(chunk, 0);
        if (count > 1) {
            torchCountInChunks.put(chunk, count - 1);
        } else {
            torchCountInChunks.remove(chunk);
        }
    }
}
