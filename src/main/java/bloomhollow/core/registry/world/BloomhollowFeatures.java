package bloomhollow.core.registry.world;

import bloomhollow.common.world.features.DrizzlewoodCeilingBushFeature;
import bloomhollow.common.world.features.ShadestoneColumnsFeature;
import bloomhollow.common.world.features.UndergroundKelpFeature;
import bloomhollow.common.world.features.WaterfallFeature;
import bloomhollow.common.world.features.WaterfallFeature.DirectionalConfiguration;
import bloomhollow.core.Bloomhollow;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> DRIZZLEWOOD_BUSH_CEILING = FEATURES.register("drizzlewood_bush_ceiling", () -> new DrizzlewoodCeilingBushFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<LargeDripstoneConfiguration>> SHADESTONE_COLUMNS = FEATURES.register("shadestone_columns", () -> new ShadestoneColumnsFeature(LargeDripstoneConfiguration.CODEC));
	public static final RegistryObject<Feature<DirectionalConfiguration>> WATERFALL = FEATURES.register("waterfall", () -> new WaterfallFeature(DirectionalConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> UNDERGROUND_KELP = FEATURES.register("underground_kelp", () -> new UndergroundKelpFeature(NoneFeatureConfiguration.CODEC));
}
