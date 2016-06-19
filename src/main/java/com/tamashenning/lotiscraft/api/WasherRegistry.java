package com.tamashenning.lotiscraft.api;

import com.tamashenning.lotiscraft.api.models.Washable;
import com.tamashenning.lotiscraft.api.models.WashableResult;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironr on 6/17/2016.
 */
public class WasherRegistry {
    private static ArrayList<Washable> registry = new ArrayList<Washable>();

    public static void register(ItemStack inItemStack, ArrayList<WashableResult> outWashableResult) {
        registry.add(new Washable(inItemStack, outWashableResult));
    }

    public static boolean containsBlock(ItemStack itemStack)
    {
        for (Washable block : registry) {
            if (itemStack.isItemEqual(block.inItemStack))
                return true;
        }

        return false;
    }

    public static Washable getWashableResult(ItemStack itemStack) {
        for (Washable block : registry) {
            if (itemStack.isItemEqual(block.inItemStack))
                return block;
        }
        return null;
    }

    public static List<ItemStack> getBlocks() {
        ArrayList<ItemStack> blocks = new ArrayList<ItemStack>();
        for (Washable block : registry) {
            blocks.add(block.inItemStack);
        }
        return blocks;
    }
}
