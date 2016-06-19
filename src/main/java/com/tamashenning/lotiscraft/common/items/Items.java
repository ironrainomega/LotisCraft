package com.tamashenning.lotiscraft.common.items;

import com.tamashenning.lotiscraft.common.items.misc.ItemFiber;
import com.tamashenning.lotiscraft.common.items.misc.ItemMesh;
import com.tamashenning.lotiscraft.common.items.ores.ItemOreBitGold;
import com.tamashenning.lotiscraft.common.items.ores.ItemOreBitIron;
import com.tamashenning.lotiscraft.common.items.ores.ItemOreBitRock;
import com.tamashenning.lotiscraft.common.items.washer.ItemSample;
import com.tamashenning.lotiscraft.common.items.washer.ItemWasher;
import com.tamashenning.lotiscraft.common.util.RegistrationHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum Items {
    SAMPLE_ITEM(ItemSample.class),
    WASHER_ITEM(ItemWasher.class),
    FIBER_ITEM(ItemFiber.class),
    MESH_ITEM(ItemMesh.class),

    OREBIT_IRON_ITEM(ItemOreBitIron.class),
    OREBIT_GOLD_ITEM(ItemOreBitGold.class),
    OREBIT_ROCK_ITEM(ItemOreBitRock.class)
    ;

    private final Class<? extends Item> itemClass;
    private Item item;

    Items(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
    }

    public static void registerItems() {
        for (Items i : Items.values()) {
            i.registerItem();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(item);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(item, size);
    }

    public ItemStack getStack(int size, int damage) {
        return new ItemStack(item, size, damage);
    }

    public Item getItem() {
        return this.item;
    }

    private void registerItem() {
        item = RegistrationHelper.registerItem(itemClass);
    }
}
