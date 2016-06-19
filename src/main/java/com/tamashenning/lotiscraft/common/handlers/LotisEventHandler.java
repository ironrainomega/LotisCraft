package com.tamashenning.lotiscraft.common.handlers;

import com.tamashenning.lotiscraft.common.items.Items;
import com.tamashenning.lotiscraft.common.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * Created by ironr on 6/18/2016.
 */
public class LotisEventHandler {

    private float fiberDropChance = 0.05f;

    @SubscribeEvent
    public void addDropOnHarvest(net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent event) {

        Random random = new Random();

        if (event.getState().getBlock() == Blocks.LEAVES) {
            if (random.nextFloat() <= fiberDropChance) {
                event.getDrops().add(new ItemStack(Items.FIBER_ITEM.getItem()));
            }
        }
    }
}
