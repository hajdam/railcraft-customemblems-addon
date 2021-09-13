package mods.railcraft_cemblem.common.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.railcraft_cemblem.common.modules.ModuleEmblem;

@Mod(modid = Railcraft_Cemblem.MODID, name = Railcraft_Cemblem.MODNAME, version = Railcraft_Cemblem.MODVER, dependencies = "after:Railcraft")
public class Railcraft_Cemblem {

    public static final String MODID = "railcraft_cemblem";
    public static final String MODNAME = "Railcraft Custom Emblems Addon";
    public static final String MODVER = "0.1.0";

    @Instance("railcraft_cemblem")
    public static Railcraft_Cemblem instance;

    private static ModuleEmblem moduleEmblem = new ModuleEmblem();

    @SidedProxy(clientSide = "mods.railcraft_cemblem.common.core.ClientProxy", serverSide = "mods.railcraft_cemblem.common.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInitClient();

        moduleEmblem.preInit();

        initFirst();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.initClient();

        initSecond();

        postInit();
    }

    private static void initFirst() {
        moduleEmblem.initFirst();
    }

    private static void initSecond() {
        moduleEmblem.initSecond();
    }

    private static void postInit() {
        moduleEmblem.postInit();
    }

    public static Railcraft_Cemblem getMod() {
        return instance;
    }
}
