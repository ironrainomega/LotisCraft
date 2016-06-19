package com.tamashenning.lotiscraft.api.models;

import net.minecraft.item.ItemStack;

/**
 * Created by ironr on 6/17/2016.
 */
public class WashableResult {
    public ItemStack outItemStack;
    public float chance;
    public int luckMultiplier;

    public WashableResult(ItemStack outItemStack, float chance, int luckMultiplier)
    {
        this.outItemStack = outItemStack;
        this.chance = chance;
        this.luckMultiplier = luckMultiplier;
    }
}
