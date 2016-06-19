package com.tamashenning.lotiscraft.proxy;

import com.tamashenning.lotiscraft.LotisCraft;
import com.tamashenning.lotiscraft.api.WasherRegistry;
import com.tamashenning.lotiscraft.api.models.WashableResult;
import com.tamashenning.lotiscraft.client.gui.GuiHandler;
import com.tamashenning.lotiscraft.common.blocks.Blocks;
import com.tamashenning.lotiscraft.common.config.Config;
import com.tamashenning.lotiscraft.common.handlers.RegisterWasherRecipesHandler;
import com.tamashenning.lotiscraft.common.items.Items;
import com.tamashenning.lotiscraft.common.util.IProvideEvent;
import com.tamashenning.lotiscraft.common.util.IProvideRecipe;
import com.tamashenning.lotiscraft.common.util.IProvideSmelting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerBlocks() {
        Blocks.registerBlocks();
    }

    @Override
    public void registerItems() {
        Items.registerItems();
    }

    @Override
    public void registerFurnaceRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideSmelting)
                ((IProvideSmelting) item.getItem()).RegisterSmelting();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideSmelting)
                ((IProvideSmelting) block.getBlock()).RegisterSmelting();
        }
    }

    @Override
    public void registerRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideRecipe)
                ((IProvideRecipe) item.getItem()).RegisterRecipes();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideRecipe)
                ((IProvideRecipe) block.getBlock()).RegisterRecipes();
        }

        RegisterWasherRecipesHandler.register();
    }

    @Override
    public void registerEvents() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(item.getItem());
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(block.getBlock());
        }
    }

    @Override
    public void registerGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(LotisCraft.instance, new GuiHandler());
    }

    @Override
    public void registerRenderers() {
        /** Client Side Only **/
    }

    @Override
    public void registerConfiguration(File configFile) {
        LotisCraft.configuration = Config.initConfig(configFile);
    }
}
