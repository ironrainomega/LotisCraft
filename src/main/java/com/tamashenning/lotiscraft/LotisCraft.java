package com.tamashenning.lotiscraft;

import com.tamashenning.lotiscraft.common.config.Config;
//import com.tamashenning.lotiscraft.common.integrations.IntegrationsManager;
import com.tamashenning.lotiscraft.common.handlers.LotisEventHandler;
import com.tamashenning.lotiscraft.common.util.LogHelper;
import com.tamashenning.lotiscraft.proxy.IProxy;
import com.google.common.base.Stopwatch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, certificateFingerprint = ModInfo.FINGERPRINT, dependencies = ModInfo.DEPENDENCIES, version = ModInfo.VERSION_BUILD, guiFactory = ModInfo.GUI_FACTORY)
public class LotisCraft {
    @Mod.Instance(ModInfo.MOD_ID)
    public static LotisCraft instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static Configuration configuration;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Pre Initialization (Started)");

        proxy.registerConfiguration(event.getSuggestedConfigurationFile());

        proxy.registerBlocks();
        proxy.registerItems();

        proxy.registerGUIs();

        proxy.registerFurnaceRecipes();
        proxy.registerEvents();

        proxy.registerRenderers();

        //IntegrationsManager.instance().index();
        //IntegrationsManager.instance().preInit();

        LogHelper.info("Pre Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Initialization (Started)");

        proxy.registerRecipes();

        //IntegrationsManager.instance().init();

        LogHelper.info("Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Post Initialization (Started)");

        //IntegrationsManager.instance().postInit();
        MinecraftForge.EVENT_BUS.register(new LotisEventHandler());

        LogHelper.info("Post Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModInfo.MOD_ID)) {
            Config.loadConfiguration();
        }
    }
}
