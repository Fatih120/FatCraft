package com.mof.fatcraft.world;

import net.minecraft.world.WorldProvider;

public class WorldProviderMoon extends WorldProvider {
    @Override
    public String getDimensionName() {
        return "Moon";
    }

//    @Override
//    public String getInternalNameSuffix() {
//        return "_moon";
//    }

    // Override other methods to customize the moon dimension
}
