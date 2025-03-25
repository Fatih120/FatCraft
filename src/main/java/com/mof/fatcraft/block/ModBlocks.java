package com.mof.fatcraft.block;

import com.mof.fatcraft.events.RegisterHelper;
import net.minecraft.block.Block;

/** Manages all mod blocks. */
public class ModBlocks
{
    // Blocks
    public static Block dollPlaced;
    public static Block waterSensor;
    public static Block oreCharcoal;
    public static Block rope;
    public static Block conveyorBelt;
    public static Block gear;
    public static Block lapisTorch;

    /** Instantiates and registers all mod blocks. */
    public static void registerModBlocks()
    {
        dollPlaced = new BlockDoll();
        RegisterHelper.registerBlock(dollPlaced);
        waterSensor = new BlockWaterDetector();
        RegisterHelper.registerBlock(waterSensor);
        oreCharcoal = new BlockCharcoalOre();
        RegisterHelper.registerBlock(oreCharcoal);
        conveyorBelt = new BlockConveyorBelt();
        RegisterHelper.registerBlock(conveyorBelt);
        rope = new BlockRope();
        RegisterHelper.registerBlock(rope);
        gear = new BlockGear();
        RegisterHelper.registerBlock(gear);
        lapisTorch = new BlockLapisTorch();
        RegisterHelper.registerBlock(lapisTorch);
    }
}
