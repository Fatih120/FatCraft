package com.mof.fatcraft;

import com.mof.fatcraft.client.renderer.RenderTEDoll;
import com.mof.fatcraft.client.renderer.RenderTeeterboard;
import com.mof.fatcraft.entity.EntityTeeterboard;
import com.mof.fatcraft.events.*;
import com.mof.fatcraft.world.DimensionMoon;
import com.mof.fatcraft.world.gen.NetherOreGen;
import com.mof.fatcraft.block.ModBlocks;
import com.mof.fatcraft.client.renderer.RenderBabyCreeper;
import com.mof.fatcraft.entity.EntityBabyCreeper;
import com.mof.fatcraft.entity.EntityBrickThrown;
import com.mof.fatcraft.entity.tile.TileEntityDoll;
import com.mof.fatcraft.item.ModItems;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Main.MODID, version = Tags.VERSION, name = Main.NAME, acceptedMinecraftVersions = "[1.7.10]")
public class Main {

    public static final String MODID = "fatcraft";
    public static final String NAME = "FatCraft";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.mof.fatcraft.ClientProxy", serverSide = "com.mof.fatcraft.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabFatCraft = new CreativeTabs("FatCraft") {
        @Override
        public Item getTabIconItem() {
            return Items.dye;
        }
    };







    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        DimensionMoon.init();

        EntityRegistry.registerGlobalEntityID(EntityBabyCreeper.class, "BabyCreeper", EntityRegistry.findGlobalUniqueEntityId());
        RenderingRegistry.registerEntityRenderingHandler(EntityBabyCreeper.class, new RenderBabyCreeper());
        RenderingRegistry.registerEntityRenderingHandler(EntityTeeterboard.class, new RenderTeeterboard());
        GameRegistry.registerWorldGenerator(new NetherOreGen(), 1);


//        GameRegistry.registerTileEntity(TileEntityConveyor.class, "tileEntityConveyor");

        GameRegistry.registerTileEntity(TileEntityDoll.class, Main.MODID + ":tileEntityDoll");
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoll.class, new RenderTEDoll());
//        GameRegistry.registerTileEntity(TileEntityGear.class, Main.MODID + ":tileGear");
//        RenderGearSprite gearSprite = new RenderGearSprite(Main.MODID+":gear");
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGear.class, new RenderTEGear(gearSprite));
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        MinecraftForge.EVENT_BUS.register(new QuiverHandler());
        MinecraftForge.EVENT_BUS.register(new SinkingHandler());
        MinecraftForge.EVENT_BUS.register(new LapisLazuliTorchHandler());


        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTeeterboard.class, "Teeterboard", entityId);
        EntityRegistry.registerModEntity(EntityTeeterboard.class, "Teeterboard", entityId, this, 64, 10, true);

//        MinecraftForge.EVENT_BUS.register(new fuck());
        MinecraftForge.EVENT_BUS.register(new BrickThrowHandler());
        EntityRegistry.registerGlobalEntityID(EntityBrickThrown.class, "BrickThrown", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityBrickThrown.class, "BrickThrown", EntityRegistry.findGlobalUniqueEntityId(), this, 64, 10, true);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityBrickThrown.class, new RenderSnowball(Items.brick));
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

}




