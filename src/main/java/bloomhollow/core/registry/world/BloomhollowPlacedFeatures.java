package bloomhollow.core.registry.world;

import java.util.List;
import java.util.function.Supplier;

import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowPlacedFeatures {
	
	public static final HeightRangePlacement OCEAN_FLOOR_HEIGHT = HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48));

	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<PlacedFeature> GRASS_PATCH = register("grass_patch", BloomhollowConfiguredFeatures.GRASS_PATCH, basicCave(25));
	public static final RegistryObject<PlacedFeature> BRIGHTSHROOM_PATCH_RARE = register("brightshroom_patch_rare", BloomhollowConfiguredFeatures.BRIGHTSHROOM_PATCH, basicCave(5));
	public static final RegistryObject<PlacedFeature> BRIGHTSHROOM_PATCH = register("brightshroom_patch", BloomhollowConfiguredFeatures.BRIGHTSHROOM_PATCH, basicCave(25));
	public static final RegistryObject<PlacedFeature> SHINING_ANGEL_FUNGUS_PATCH = register("shining_angel_fungus_patch", BloomhollowConfiguredFeatures.SHINING_ANGEL_FUNGUS_PATCH, basicCave(5));
	public static final RegistryObject<PlacedFeature> SHINING_TEAL_FUNGUS_PATCH = register("shining_teal_fungus_patch", BloomhollowConfiguredFeatures.SHINING_TEAL_FUNGUS_PATCH, basicCave(5));
	public static final RegistryObject<PlacedFeature> SHINING_INDIGO_FUNGUS_PATCH = register("shining_indigo_fungus_patch", BloomhollowConfiguredFeatures.SHINING_INDIGO_FUNGUS_PATCH, basicCave(5));
	
	public static final RegistryObject<PlacedFeature> DRIZZLEWOOD_TREE = tree("drizzlewood_tree", BloomhollowConfiguredFeatures.DRIZZLEWOOD_TREE, () -> Blocks.OAK_SAPLING);
	public static final RegistryObject<PlacedFeature> DRIZZLEWOOD_TREE_PATCH = register("drizzlewood_tree_patch", BloomhollowConfiguredFeatures.DRIZZLEWOOD_TREE_PATCH, basicCaveFoliage(169));
	public static final RegistryObject<PlacedFeature> AZURE_WILLOW_TREE = tree("azure_willow_tree", BloomhollowConfiguredFeatures.AZURE_WILLOW_TREE, BloomhollowBlocks.AZURE_WILLOW_SAPLING);
	public static final RegistryObject<PlacedFeature> AZURE_WILLOW_TREE_PATCH = register("azure_willow_tree_patch", BloomhollowConfiguredFeatures.AZURE_WILLOW_TREE_PATCH, basicCaveFoliage(50));
	public static final RegistryObject<PlacedFeature> BERYL_WILLOW_TREE = tree("beryl_willow_tree", BloomhollowConfiguredFeatures.BERYL_WILLOW_TREE, BloomhollowBlocks.BERYL_WILLOW_SAPLING);
	public static final RegistryObject<PlacedFeature> BERYL_WILLOW_TREE_PATCH = register("beryl_willow_tree_patch", BloomhollowConfiguredFeatures.BERYL_WILLOW_TREE_PATCH, basicCaveFoliage(50));
	public static final RegistryObject<PlacedFeature> DRIZZLEWOOD_BUSH_PATCH = register("drizzlewood_bush_patch", BloomhollowConfiguredFeatures.DRIZZLEWOOD_BUSH, basicCaveFoliage(100));
	public static final RegistryObject<PlacedFeature> DRIZZLEWOOD_BUSH_PATCH_CEILING = register("drizzlewood_bush_patch_ceiling", BloomhollowConfiguredFeatures.DRIZZLEWOOD_BUSH_CEILING, basicCaveCeiling(150));
	public static final RegistryObject<PlacedFeature> RAINFALL_POOL = register("rainfall_pool", BloomhollowConfiguredFeatures.RAINFALL_POOL, basicCave(35));
	public static final RegistryObject<PlacedFeature> CALCITE_POOL = register("calcite_pool", BloomhollowConfiguredFeatures.CALCITE_POOL, basicCave(5));
	public static final RegistryObject<PlacedFeature> DOWNPOUR = register("downpour", BloomhollowConfiguredFeatures.DOWNPOUR, PlacementUtils.filteredByBlockSurvival(Blocks.BIG_DRIPLEAF));
	public static final RegistryObject<PlacedFeature> DOWNPOUR_PATCH = register("downpour_patch", BloomhollowConfiguredFeatures.DOWNPOUR_PATCH, basicCave(30));
	public static final RegistryObject<PlacedFeature> FLUX_VINES = register("flux_vines", BloomhollowConfiguredFeatures.FLUX_VINES, CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
	public static final RegistryObject<PlacedFeature> GLOWBERRY_VINES = copy("glowberry_vines", CavePlacements.CAVE_VINES);
	public static final RegistryObject<PlacedFeature> SHADESTONE_COLUMNS = register("shadestone_columns", BloomhollowConfiguredFeatures.SHADESTONE_COLUMNS, CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
	public static final RegistryObject<PlacedFeature> DRIPSTONE_CLUSTER = copy("dripstone_cluster", CavePlacements.DRIPSTONE_CLUSTER);
	public static final RegistryObject<PlacedFeature> LUSH_PATCH = register("lush_patch", BloomhollowConfiguredFeatures.LUSH_PATCH, CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	public static final RegistryObject<PlacedFeature> GLOW_LICHEN = copy("glow_lichen", CavePlacements.GLOW_LICHEN);
	public static final RegistryObject<PlacedFeature> GLOWBERRY_VINES_RARE = register("glowberry_vines_rare", CaveFeatures.CAVE_VINE, CountPlacement.of(15), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
	public static final RegistryObject<PlacedFeature> WATERFALL_CEILING = register("waterfall_ceiling", BloomhollowConfiguredFeatures.WATERFALL, basicCaveCeiling(2));
	public static final RegistryObject<PlacedFeature> SPRING_WATER = copy("spring_water", MiscOverworldPlacements.SPRING_WATER);
	public static final RegistryObject<PlacedFeature> KELP = register("kelp", BloomhollowConfiguredFeatures.UNDERGROUND_KELP, basicCaveAquatic(140));
	public static final RegistryObject<PlacedFeature> SEAGRASS = register("seagrass", AquaticFeatures.SEAGRASS_SIMPLE, basicCaveAquatic(100));
	public static final RegistryObject<PlacedFeature> SEAGRASS_TALL = register("seagrass_tall", AquaticFeatures.SEAGRASS_TALL, basicCaveAquatic(100));
	public static final RegistryObject<PlacedFeature> SEA_PICKLE = register("sea_pickle", AquaticFeatures.SEA_PICKLE, basicCaveAquatic(140));
	
	private static RegistryObject<PlacedFeature> copy(String name, Holder<PlacedFeature> placedFeature) {
		return register(name, placedFeature.value().feature(), placedFeature.value().placement());
	}
	
	private static <T extends FeatureConfiguration> RegistryObject<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(Holder.hackyErase(feature), modifiers));
	}
	
	private static <T extends FeatureConfiguration> RegistryObject<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
	}
	
	public static RegistryObject<PlacedFeature> tree(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, Supplier<Block> sapling) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(Holder.hackyErase(feature.getHolder().orElseThrow()), List.of(PlacementUtils.filteredByBlockSurvival(sapling.get()))));
	}
	
	public static RegistryObject<PlacedFeature> register(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(Holder.hackyErase(feature.getHolder().orElseThrow()), modifiers));
	}

	public static RegistryObject<PlacedFeature> register(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
		return register(name, feature, List.of(modifiers));
	}
	
	private static List<PlacementModifier> basicCaveFoliage(int count) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}
	
	private static List<PlacementModifier> basicCaveAquatic(int count) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), OCEAN_FLOOR_HEIGHT, SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -2), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome(), PlacementUtils.filteredByBlockSurvival(Blocks.KELP));
	}
	
	private static List<PlacementModifier> basicCave(int count) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}
	
	private static List<PlacementModifier> basicCaveCeiling(int count) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
	}
}
