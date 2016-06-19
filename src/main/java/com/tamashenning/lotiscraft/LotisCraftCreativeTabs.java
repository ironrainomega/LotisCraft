package com.tamashenning.lotiscraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class LotisCraftCreativeTabs {
    public static final CreativeTabs tabGeneral = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.APPLE;
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".general";
        }
    };

    public static final CreativeTabs tabOres = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.IRON_INGOT;
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".ores";
        }
    };


}
