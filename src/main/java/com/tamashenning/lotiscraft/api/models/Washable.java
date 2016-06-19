package com.tamashenning.lotiscraft.api.models;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironr on 6/17/2016.
 */
public class Washable {
    public ItemStack inItemStack;
    public List<WashableResult> outWashableResult;

    public Washable(ItemStack inItemStack, List<WashableResult> outWashableResult)
    {
        this.inItemStack = inItemStack;
        this.outWashableResult = outWashableResult;
    }
}
