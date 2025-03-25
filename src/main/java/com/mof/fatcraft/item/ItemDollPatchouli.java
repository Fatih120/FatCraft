package com.mof.fatcraft.item;

import com.mof.fatcraft.Main;
import net.minecraft.item.Item;

import static com.mof.fatcraft.Main.tabFatCraft;

public class ItemDollPatchouli extends Item {
    public ItemDollPatchouli() {
        super();
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("dollPatchouli");
        this.setTextureName(Main.MODID + ":dollPatchouli");
        this.setCreativeTab(tabFatCraft);

    }
}
