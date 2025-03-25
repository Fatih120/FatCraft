package com.mof.fatcraft.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems
{
    public static Item patchouli;
    public static Item quiver;

    public static void registerModItems()
    {
        patchouli = new ItemDollPatchouli();
        GameRegistry.registerItem(patchouli, "dollPatchouli");
        quiver = new ItemQuiver();
        GameRegistry.registerItem(quiver, "quiver");
    }
}
