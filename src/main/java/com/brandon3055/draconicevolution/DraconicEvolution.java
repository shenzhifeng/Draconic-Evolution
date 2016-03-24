package com.brandon3055.draconicevolution;

import com.brandon3055.brandonscore.common.config.ModConfigProcessor;
import com.brandon3055.brandonscore.common.config.ModFeatureParser;
import com.brandon3055.draconicevolution.client.creativetab.DETab;
import com.brandon3055.draconicevolution.common.CommonProxy;
import com.brandon3055.draconicevolution.common.DEConfig;
import com.brandon3055.draconicevolution.common.DEFeatures;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import com.brandon3055.draconicevolution.common.world.DEWorldGenHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = DraconicEvolution.MODID, name = DraconicEvolution.MODNAME, version = DraconicEvolution.VERSION, canBeDeactivated = false, guiFactory = DraconicEvolution.GUI_FACTORY,  dependencies = DraconicEvolution.DEPENDENCIES)
public class DraconicEvolution {
	public static final String MODID	= "DraconicEvolution";
	public static final String MODNAME	= "Draconic Evolution";
	public static final String VERSION	= "2.0.0";//todo Automate version
	public static final String PROXY_CLIENT = "com.brandon3055.draconicevolution.client.ClientProxy";
	public static final String PROXY_SERVER = "com.brandon3055.draconicevolution.common.CommonProxy";
	public static final String DEPENDENCIES = "after:NotEnoughItems;after:NotEnoughItems;after:ThermalExpansion;after:ThermalFoundation;required-after:BrandonsCore@[1.0.0.11,);"; //todo Automate version
	public static final String GUI_FACTORY 	= "com.brandon3055.draconicevolution.client.gui.DEGUIFactory";
	public static final String networkChannelName = "DEvolutionNC";
	//region Misc Fields
	public static CreativeTabs tabToolsWeapons = new DETab(CreativeTabs.getNextID(), DraconicEvolution.MODID, "toolsAndWeapons", 0);
	public static CreativeTabs tabBlocksItems = new DETab(CreativeTabs.getNextID(), DraconicEvolution.MODID, "blocksAndItems", 1);

	public static SimpleNetworkWrapper network;

	public static boolean debug = false;//todo

	public static Enchantment reaperEnchant;

	public static Configuration configuration;
	//endregion

	@Mod.Instance(DraconicEvolution.MODID)
	public static DraconicEvolution instance;

	@SidedProxy(clientSide = DraconicEvolution.PROXY_CLIENT, serverSide = DraconicEvolution.PROXY_SERVER)
	public static CommonProxy proxy;

	public static ModFeatureParser featureParser = new ModFeatureParser(MODID, new CreativeTabs[]{tabBlocksItems, tabToolsWeapons});
	public static ModConfigProcessor configProcessor = new ModConfigProcessor();

	public DraconicEvolution()
	{
		LogHelper.info("Hello Minecraft!!!");
	}
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configProcessor.processConfig(DEConfig.class, configuration);
		featureParser.loadFeatures(DEFeatures.class);
		featureParser.loadFeatureConfig(configuration);
		featureParser.registerFeatures();

		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		DEWorldGenHandler.initialize();

		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

	//FMLInterModComms.sendMessage("DraconicEvolution", "addChestRecipe:item.coal", new ItemStack(Items.diamond, 2));
	@Mod.EventHandler
	public void processMessage(FMLInterModComms.IMCEvent event) {
		for (FMLInterModComms.IMCMessage m : event.getMessages()) {
			LogHelper.info(m.key);
			if (m.isItemStackMessage() && m.key.contains("addChestRecipe:")) {
				String s = m.key.substring(m.key.indexOf("addChestRecipe:") + 15);
//				OreDoublingRegistry.resultOverrides.put(s, m.getItemStackValue());			//TODO Update ore doubling registry
				LogHelper.info("Added Chest recipe override: " + s + " to " + m.getItemStackValue());
			}
		}
	}
}