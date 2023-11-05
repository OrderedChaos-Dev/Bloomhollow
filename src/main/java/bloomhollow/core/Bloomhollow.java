package bloomhollow.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import bloomhollow.client.BlockRendering;
import bloomhollow.common.event.BloomhollowEvents;
import bloomhollow.core.data.BloomhollowBlockTagsProvider;
import bloomhollow.core.registry.BloomhollowBlocks;
import bloomhollow.core.registry.BloomhollowItems;
import bloomhollow.core.registry.BloomhollowParticles;
import bloomhollow.core.registry.world.BloomhollowBiomes;
import bloomhollow.core.registry.world.BloomhollowConfiguredFeatures;
import bloomhollow.core.registry.world.BloomhollowDimensions;
import bloomhollow.core.registry.world.BloomhollowFeatures;
import bloomhollow.core.registry.world.BloomhollowPlacedFeatures;
import bloomhollow.core.registry.world.BloomhollowTreePlacers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(Bloomhollow.MOD_ID)
public class Bloomhollow
{
	public static final String MOD_ID = "bloomhollow";
	
	public static final Logger LOGGER = LogUtils.getLogger();

    public Bloomhollow() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	BloomhollowParticles.PARTICLES.register(bus);
    	BloomhollowBlocks.BLOCKS.register(bus);
    	BloomhollowItems.ITEMS.register(bus);
    	BloomhollowDimensions.POI_TYPES.register(bus);
    	BloomhollowFeatures.FEATURES.register(bus);
    	BloomhollowTreePlacers.Trunk.TRUNK_PLACERS.register(bus);
    	BloomhollowTreePlacers.Foliage.FOLIAGE_PLACERS.register(bus);
    	BloomhollowTreePlacers.Decorator.DECORATORS.register(bus);
    	BloomhollowConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
    	BloomhollowPlacedFeatures.PLACED_FEATURES.register(bus);
    	BloomhollowBiomes.BIOMES.register(bus);
    	
    	bus.addListener(this::data);
    	bus.addListener(this::setup);
    	bus.addListener(this::clientSetup);
    	MinecraftForge.EVENT_BUS.register(new BloomhollowEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {

    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
    	BlockRendering.registerRenderers();
    }
    
    private void data(GatherDataEvent event) {
    	DataGenerator gen = event.getGenerator();
    	ExistingFileHelper helper = event.getExistingFileHelper();
    	
    	if(event.includeServer()) {
    		BloomhollowBlockTagsProvider blockTags = new BloomhollowBlockTagsProvider(gen, helper);
    		gen.addProvider(blockTags);
    	}
    	
    	if(event.includeClient()) {
    		
    	}
    }
    

}
