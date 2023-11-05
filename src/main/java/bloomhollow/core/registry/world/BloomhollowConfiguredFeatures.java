package bloomhollow.core.registry.world;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

import bloomhollow.common.blocks.FluxVinesPlantBlock;
import bloomhollow.common.blocks.FluxVinesPlantBlock.BerryColor;
import bloomhollow.common.world.features.WaterfallFeature.DirectionalConfiguration;
import bloomhollow.common.world.features.tree.decorators.AzureWillowVineDecorator;
import bloomhollow.common.world.features.tree.decorators.BerylWillowVineDecorator;
import bloomhollow.common.world.features.tree.foliageplacers.DrizzlewoodFoliagePlacer;
import bloomhollow.common.world.features.tree.trunkplacers.DrizzlewoodTrunkPlacer;
import bloomhollow.common.world.features.tree.trunkplacers.WillowTrunkPlacer;
import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowConfiguredFeatures {
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<ConfiguredFeature<?, ?>> GRASS_PATCH = CONFIGURED_FEATURES.register("grass_patch", () -> simpleRandomSelector(PlacementUtils.inlinePlaced(VegetationFeatures.PATCH_GRASS), PlacementUtils.inlinePlaced(VegetationFeatures.PATCH_TALL_GRASS)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DRIZZLEWOOD_TREE = CONFIGURED_FEATURES.register("drizzlewood_tree", () -> tree(new DrizzlewoodTrunkPlacer(8, 3, 3), new DrizzlewoodFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), BloomhollowBlocks.DRIZZLEWOOD_LOG.get(), BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get(), new TwoLayersFeatureSize(1, 1, 1)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> AZURE_WILLOW_TREE = CONFIGURED_FEATURES.register("azure_willow_tree", () -> tree(new WillowTrunkPlacer(6, 2, 2), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 125), BloomhollowBlocks.AZURE_WILLOW_LOG.get(), BloomhollowBlocks.AZURE_WILLOW_LEAVES.get(), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()), List.of(AzureWillowVineDecorator.INSTANCE)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BERYL_WILLOW_TREE = CONFIGURED_FEATURES.register("beryl_willow_tree", () -> tree(new WillowTrunkPlacer(6, 2, 2), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 125), BloomhollowBlocks.BERYL_WILLOW_LOG.get(), BloomhollowBlocks.BERYL_WILLOW_LEAVES.get(), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()), List.of(BerylWillowVineDecorator.INSTANCE)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DRIZZLEWOOD_TREE_PATCH = CONFIGURED_FEATURES.register("drizzlewood_tree_patch", () -> simpleRandomPatch(10, BloomhollowPlacedFeatures.DRIZZLEWOOD_TREE.getHolder().get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> AZURE_WILLOW_TREE_PATCH = CONFIGURED_FEATURES.register("azure_willow_tree_patch", () -> simpleRandomPatch(7, BloomhollowPlacedFeatures.AZURE_WILLOW_TREE.getHolder().get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BERYL_WILLOW_TREE_PATCH = CONFIGURED_FEATURES.register("beryl_willow_tree_patch", () -> simpleRandomPatch(7, BloomhollowPlacedFeatures.BERYL_WILLOW_TREE.getHolder().get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DRIZZLEWOOD_BUSH = CONFIGURED_FEATURES.register("drizzlewood_bush", () -> tree(new DrizzlewoodTrunkPlacer(1, 0, 0), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), BloomhollowBlocks.DRIZZLEWOOD_LOG.get(), BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get(), new TwoLayersFeatureSize(0, 0, 0)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DRIZZLEWOOD_BUSH_CEILING = CONFIGURED_FEATURES.register("drizzlewood_bush_ceiling", () -> new ConfiguredFeature<>(BloomhollowFeatures.DRIZZLEWOOD_BUSH_CEILING.get(), NoneFeatureConfiguration.INSTANCE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> RAINFALL_POOL = CONFIGURED_FEATURES.register("rainfall_pool", () -> new ConfiguredFeature<>(Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(BloomhollowBlocks.GRASSY_SHADESTONE.get()), PlacementUtils.inlinePlaced(DRIZZLEWOOD_TREE.getHolder().get()), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.3F, UniformInt.of(4, 7), 0.7F)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> CALCITE_POOL = CONFIGURED_FEATURES.register("calcite_pool", () -> new ConfiguredFeature<>(Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.CALCITE), PlacementUtils.inlinePlaced(CaveFeatures.GLOW_LICHEN), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.3F, UniformInt.of(4, 7), 0.7F)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BRIGHTSHROOM_PATCH = CONFIGURED_FEATURES.register("brightshroom_patch", () -> simpleRandomPatch(BloomhollowBlocks.BRIGHTSHROOM.get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SHINING_ANGEL_FUNGUS_PATCH = CONFIGURED_FEATURES.register("shining_angel_fungus_patch", () -> simpleRandomPatch(BloomhollowBlocks.SHINING_ANGEL_FUNGUS.get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SHINING_TEAL_FUNGUS_PATCH = CONFIGURED_FEATURES.register("shining_teal_fungus_patch", () -> simpleRandomPatch(BloomhollowBlocks.SHINING_TEAL_FUNGUS.get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SHINING_INDIGO_FUNGUS_PATCH = CONFIGURED_FEATURES.register("shining_indigo_fungus_patch", () -> simpleRandomPatch(BloomhollowBlocks.SHINING_INDIGO_FUNGUS.get()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DOWNPOUR = CONFIGURED_FEATURES.register("downpour", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(makeDownpour(Direction.EAST), makeDownpour(Direction.WEST), makeDownpour(Direction.SOUTH), makeDownpour(Direction.NORTH)))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DOWNPOUR_PATCH = CONFIGURED_FEATURES.register("downpour_patch", () -> simpleRandomPatch(25, BloomhollowPlacedFeatures.DOWNPOUR.getHolder().get()));
	private static final Supplier<WeightedStateProvider> FLUX_VINES_BODY_PROVIDER = () -> new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BloomhollowBlocks.FLUX_VINES_PLANT.get().defaultBlockState(), 7).add(BloomhollowBlocks.FLUX_VINES_PLANT.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.ORANGE), 1).add(BloomhollowBlocks.FLUX_VINES_PLANT.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.YELLOW), 1).add(BloomhollowBlocks.FLUX_VINES_PLANT.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.BLUE), 1));
	private static final Supplier<RandomizedIntStateProvider> FLUX_VINES_HEAD_PROVIDER = () -> new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BloomhollowBlocks.FLUX_VINES.get().defaultBlockState(), 7).add(BloomhollowBlocks.FLUX_VINES.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.ORANGE), 1).add(BloomhollowBlocks.FLUX_VINES.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.YELLOW), 1).add(BloomhollowBlocks.FLUX_VINES.get().defaultBlockState().setValue(FluxVinesPlantBlock.COLOR, BerryColor.BLUE), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25));
	public static final RegistryObject<ConfiguredFeature<?, ?>> FLUX_VINES = CONFIGURED_FEATURES.register("flux_vines", () -> new ConfiguredFeature<>(Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 2).add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 10).build()), FLUX_VINES_BODY_PROVIDER.get()), BlockColumnConfiguration.layer(ConstantInt.of(1), FLUX_VINES_HEAD_PROVIDER.get())), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SHADESTONE_COLUMNS = CONFIGURED_FEATURES.register("shadestone_columns", () -> new ConfiguredFeature<>(BloomhollowFeatures.SHADESTONE_COLUMNS.get(), new LargeDripstoneConfiguration(30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> UNDERGROWTH_VEGETATION = CONFIGURED_FEATURES.register("undergrowth_vegetation", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BloomhollowBlocks.BRIGHTSHROOM.get().defaultBlockState(), 2).add(Blocks.MOSS_CARPET.defaultBlockState(), 15).add(Blocks.GRASS.defaultBlockState(), 50).add(Blocks.TALL_GRASS.defaultBlockState(), 10)))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> LUSH_PATCH = CONFIGURED_FEATURES.register("lush_patch", () -> new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BloomhollowBlocks.SHADESTONE.get().defaultBlockState(), 1).add(BloomhollowBlocks.GRASSY_SHADESTONE.get().defaultBlockState(), 2).add(Blocks.MOSS_BLOCK.defaultBlockState(), 2)), PlacementUtils.inlinePlaced(UNDERGROWTH_VEGETATION.getHolder().get()), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> WATERFALL = CONFIGURED_FEATURES.register("waterfall", () -> new ConfiguredFeature<>(BloomhollowFeatures.WATERFALL.get(), new DirectionalConfiguration(Direction.DOWN)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> UNDERGROUND_KELP = CONFIGURED_FEATURES.register("underground_kelp", () -> new ConfiguredFeature<>(BloomhollowFeatures.UNDERGROUND_KELP.get(), NoneFeatureConfiguration.INSTANCE));
	
	@SafeVarargs
	private static ConfiguredFeature<?, ?> simpleRandomSelector(Holder<PlacedFeature>... features) {
		return new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(features)));
	}
	
	private static ConfiguredFeature<?, ?> simpleRandomPatch(int count, Holder<PlacedFeature> feature) {
		return new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(count, feature));
	}
	
	private static ConfiguredFeature<?, ?> simpleRandomPatch(Block block) {
		return new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block))));
	}

	private static ConfiguredFeature<?, ?> tree(TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, Block log, Block leaves, FeatureSize featureSize) {
		return new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), trunkPlacer,
																						BlockStateProvider.simple(leaves),
																						foliagePlacer, featureSize).ignoreVines().build());
	}
	
	private static ConfiguredFeature<?, ?> tree(TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, Block log, Block leaves, FeatureSize featureSize, List<TreeDecorator> decorators) {
		return new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), trunkPlacer,
																						BlockStateProvider.simple(leaves),
																						foliagePlacer, featureSize).ignoreVines().decorators(decorators).build());
	}
	
	private static Holder<PlacedFeature> makeDownpour(Direction dir) {
		return PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN,
				new BlockColumnConfiguration(
						List.of(BlockColumnConfiguration.layer(
								new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
										.add(UniformInt.of(0, 6), 2).add(ConstantInt.of(0), 1).build()),
								BlockStateProvider.simple(BloomhollowBlocks.DOWNPOUR_STEM.get().defaultBlockState()
										.setValue(BlockStateProperties.HORIZONTAL_FACING, dir))),
								BlockColumnConfiguration.layer(ConstantInt.of(1),
										BlockStateProvider.simple(BloomhollowBlocks.DOWNPOUR_FLOWER.get().defaultBlockState()
												.setValue(BlockStateProperties.HORIZONTAL_FACING, dir)))),
						Direction.UP, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, true));
	}
}
