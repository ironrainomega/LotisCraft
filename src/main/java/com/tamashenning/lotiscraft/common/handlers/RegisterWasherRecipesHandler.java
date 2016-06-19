package com.tamashenning.lotiscraft.common.handlers;

import com.tamashenning.lotiscraft.api.WasherRegistry;
import com.tamashenning.lotiscraft.api.models.WashableResult;
import com.tamashenning.lotiscraft.common.items.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ironr on 6/18/2016.
 */
public class RegisterWasherRecipesHandler {
    public static void register() {
        WasherRegistry.register(new ItemStack(net.minecraft.init.Blocks.GRAVEL), new ArrayList<WashableResult>(Arrays.asList(
                new WashableResult(new ItemStack(Items.OREBIT_IRON_ITEM.getItem()), 0.5f, 0),
                new WashableResult(new ItemStack(Items.OREBIT_GOLD_ITEM.getItem()), 0.5f, 0)
        )));

        WasherRegistry.register(new ItemStack(net.minecraft.init.Blocks.DIRT), new ArrayList<WashableResult>(Arrays.asList(
                new WashableResult(new ItemStack(Items.FIBER_ITEM.getItem()), 0.15f, 3),
                new WashableResult(new ItemStack(Items.OREBIT_ROCK_ITEM.getItem()), 0.10f, 4)
        )));

        MinecraftForge.addGrassSeed(new ItemStack(Items.FIBER_ITEM.getItem()), 8);
    }
}
