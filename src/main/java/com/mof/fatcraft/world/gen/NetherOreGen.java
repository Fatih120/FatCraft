package com.mof.fatcraft.world.gen;

import com.mof.fatcraft.Main;
import com.mof.fatcraft.block.ModBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.init.Blocks;
import java.util.Random;

public class NetherOreGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == -1) { // Check if it's the Nether
            generateOre(ModBlocks.oreCharcoal, world, random, chunkX * 16, chunkZ * 16, 32, 12, 10, 110); // I forgot it only goes up to 128
        }
    }

    private void generateOre(Block oreBlock, World world, Random random, int chunkX, int chunkZ, int maxVeinSize, int veinsPerChunk, int minY, int maxY) {
        for (int i = 0; i < veinsPerChunk; i++) {
            int x = chunkX + random.nextInt(16);
//            int y = minY + random.nextInt(maxY - minY);
            int y = getWeightedY(random, minY, maxY); // Use weighted Y level generation
            int z = chunkZ + random.nextInt(16);

            generateVein(world, random, x, y, z, maxVeinSize, oreBlock);
        }
    }

    /**
     * Generates a weighted Y level where lower levels are more likely to be chosen.
     */
    private int getWeightedY(Random random, int minY, int maxY) {
        // Invert the range so lower values have higher weight
        int range = maxY - minY;
        double weight = Math.pow(random.nextDouble(), 1.5); // Square the random value for bias toward lower values
        return minY + (int) (weight * range);
    }

    private void generateVein(World world, Random random, int x, int y, int z, int maxVeinSize, Block oreBlock) {
        int veinsPlaced = 0;
        for (int i = 0; i < maxVeinSize; i++) {
            int dx = x + random.nextInt(3) - 1;
            int dy = y + random.nextInt(3) - 1;
            int dz = z + random.nextInt(3) - 1;

            if (world.getBlock(dx, dy, dz) == Blocks.netherrack) {
                world.setBlock(dx, dy, dz, oreBlock, 0, 2);
                veinsPlaced++;
            }
        }
//        if (veinsPlaced > 0) {
//            Main.LOG.info("Generated {} ore vein with {} blocks at ({}, {}, {})", oreBlock.getUnlocalizedName(), veinsPlaced, x, y, z);
//        }
    }
}
