package com.mof.fatcraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.DimensionManager;

public class DimensionMoon {
    public static final int DIMENSION_ID = 30; // Choose an unused dimension ID

    public static void init() {
        DimensionManager.registerProviderType(DIMENSION_ID, WorldProviderHell.class, true);
        DimensionManager.registerDimension(DIMENSION_ID, DIMENSION_ID);
    }
}
