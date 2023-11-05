package bloomhollow.common.biomes;

import bloomhollow.core.registry.BloomhollowParticles;
import bloomhollow.core.registry.world.BloomhollowPlacedFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class BloomhollowBiomeBuilder {

	public static Biome rainfallCanopy() {
		
		BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();
		BloomhollowDefaultFeatures.addGrassPatch(biomeGenBuilder);
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.DRIZZLEWOOD_TREE_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.DRIZZLEWOOD_BUSH_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.DRIZZLEWOOD_BUSH_PATCH_CEILING.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.RAINFALL_POOL.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.DOWNPOUR_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.BRIGHTSHROOM_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.WATERFALL_CEILING.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SPRING_WATER.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.KELP.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEAGRASS.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEAGRASS_TALL.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEA_PICKLE.getHolder().get());
		
		MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
		mobSpawnBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new SpawnerData(EntityType.GLOW_SQUID, 20, 4, 7));
		
		return new Biome.BiomeBuilder()
			.biomeCategory(BiomeCategory.FOREST)
			.downfall(1.0F)
			.temperature(0.5F)
			.precipitation(Precipitation.RAIN)
			.generationSettings(biomeGenBuilder.build())
			.mobSpawnSettings(mobSpawnBuilder.build())
			.specialEffects(new BiomeSpecialEffects.Builder()
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.fogColor(0x80bab1)
					.waterColor(0x70c6db)
					.waterFogColor(0x70c6db)
					.skyColor(0x80bab1)
					.ambientParticle(new AmbientParticleSettings(ParticleTypes.RAIN, 0.0125F))
					.build())
			.build();
	}
	
	public static Biome shroomlitGardens() {
		
		BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.CALCITE_POOL.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.AZURE_WILLOW_TREE_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.BERYL_WILLOW_TREE_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.FLUX_VINES.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.BRIGHTSHROOM_PATCH_RARE.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SHINING_ANGEL_FUNGUS_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SHINING_TEAL_FUNGUS_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SHINING_INDIGO_FUNGUS_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.WATERFALL_CEILING.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SPRING_WATER.getHolder().get());
		
		MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
		mobSpawnBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new SpawnerData(EntityType.GLOW_SQUID, 20, 4, 7));
		
		return new Biome.BiomeBuilder()
				.biomeCategory(BiomeCategory.FOREST)
				.downfall(0.7F)
				.temperature(0.5F)
				.precipitation(Precipitation.RAIN)
				.generationSettings(biomeGenBuilder.build())
				.mobSpawnSettings(mobSpawnBuilder.build())
				.specialEffects(new BiomeSpecialEffects.Builder()
						.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.fogColor(0x80bab1)
						.waterColor(4159204)
						.waterFogColor(4159204)
						.skyColor(0x80bab1)
						.ambientParticle(new AmbientParticleSettings(BloomhollowParticles.BRIGHTSHROOM_SPORE.get(), 0.0025F))
						.build())
				.build();
	}
	
	public static Biome restlessUndergrowth() {
		BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SHADESTONE_COLUMNS.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.DRIPSTONE_CLUSTER.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.LUSH_PATCH.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.GLOW_LICHEN.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.GLOWBERRY_VINES_RARE.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.WATERFALL_CEILING.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SPRING_WATER.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.KELP.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEAGRASS.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEAGRASS_TALL.getHolder().get());
		biomeGenBuilder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.SEA_PICKLE.getHolder().get());
		
		MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
		mobSpawnBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new SpawnerData(EntityType.GLOW_SQUID, 20, 4, 7));
		
		return new Biome.BiomeBuilder()
				.biomeCategory(BiomeCategory.FOREST)
				.downfall(0.7F)
				.temperature(0.6F)
				.precipitation(Precipitation.RAIN)
				.generationSettings(biomeGenBuilder.build())
				.mobSpawnSettings(mobSpawnBuilder.build())
				.specialEffects(new BiomeSpecialEffects.Builder()
						.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.fogColor(0x80bab1)
						.waterColor(4159204)
						.waterFogColor(4159204)
						.skyColor(0x80bab1)
						.build())
				.build();
	}
}
