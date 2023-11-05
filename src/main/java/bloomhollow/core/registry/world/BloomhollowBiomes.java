package bloomhollow.core.registry.world;

import bloomhollow.common.biomes.BloomhollowBiomeBuilder;
import bloomhollow.core.Bloomhollow;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowBiomes {

	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<Biome> RAINFALL_CANOPY = BIOMES.register("rainfall_canopy", BloomhollowBiomeBuilder::rainfallCanopy);
	public static final RegistryObject<Biome> SHROOMLIT_GARDENS = BIOMES.register("shroomlit_gardens", BloomhollowBiomeBuilder::shroomlitGardens);
	public static final RegistryObject<Biome> RESTLESS_UNDERGROWTH = BIOMES.register("restless_undergrowth", BloomhollowBiomeBuilder::restlessUndergrowth);
	public static final RegistryObject<Biome> LUMINOUS_OOLUMNS = BIOMES.register("luminous_columns", BloomhollowBiomeBuilder::restlessUndergrowth);
	public static final RegistryObject<Biome> CRYSTAL_CHASM = BIOMES.register("crystal_chasm", BloomhollowBiomeBuilder::restlessUndergrowth);
}
