package bloomhollow.core.registry.world;

import java.util.function.Supplier;

import bloomhollow.common.world.features.tree.decorators.AzureWillowVineDecorator;
import bloomhollow.common.world.features.tree.decorators.BerylWillowVineDecorator;
import bloomhollow.common.world.features.tree.foliageplacers.DrizzlewoodFoliagePlacer;
import bloomhollow.common.world.features.tree.trunkplacers.DrizzlewoodTrunkPlacer;
import bloomhollow.common.world.features.tree.trunkplacers.WillowTrunkPlacer;
import bloomhollow.core.Bloomhollow;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowTreePlacers {

public static class Trunk {
		
		public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registry.TRUNK_PLACER_TYPE_REGISTRY, Bloomhollow.MOD_ID);
		
		public static final RegistryObject<TrunkPlacerType<DrizzlewoodTrunkPlacer>> DRIZZLEWOOD_TRUNK_PLACER = registerTrunkPlacer("drizzlewood_trunk_placer", () -> new TrunkPlacerType<DrizzlewoodTrunkPlacer>(DrizzlewoodTrunkPlacer.CODEC));
		public static final RegistryObject<TrunkPlacerType<WillowTrunkPlacer>> WILLOW_TRUNK_PLACER = registerTrunkPlacer("willow_trunk_placer", () -> new TrunkPlacerType<WillowTrunkPlacer>(WillowTrunkPlacer.CODEC));

		private static <T extends TrunkPlacer> RegistryObject<TrunkPlacerType<T>> registerTrunkPlacer(String name, Supplier<TrunkPlacerType<T>> type) {
			return TRUNK_PLACERS.register(name, type);
		}
	}
	
	public static class Foliage {
		
		public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Bloomhollow.MOD_ID);
		
		public static final RegistryObject<FoliagePlacerType<DrizzlewoodFoliagePlacer>> DRIZZLEWOOD_FOLIAGE_PLACER = registerFoliagePlacer("drizzlewood_foliage_placer", new FoliagePlacerType<DrizzlewoodFoliagePlacer>(DrizzlewoodFoliagePlacer.CODEC));

		private static <T extends FoliagePlacer> RegistryObject<FoliagePlacerType<T>> registerFoliagePlacer(String name, FoliagePlacerType<T> type) {
			return FOLIAGE_PLACERS.register(name, () -> type);
		}
	}
	
	public static class Decorator {
		public static final DeferredRegister<TreeDecoratorType<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Bloomhollow.MOD_ID);

		public static final RegistryObject<TreeDecoratorType<AzureWillowVineDecorator>> AZURE_WILLOW_VINES = registerTreeDecorator("azure_willow_vines", new TreeDecoratorType<>(AzureWillowVineDecorator.CODEC));
		public static final RegistryObject<TreeDecoratorType<BerylWillowVineDecorator>> BERYL_WILLOW_VINES = registerTreeDecorator("beryl_willow_vines", new TreeDecoratorType<>(BerylWillowVineDecorator.CODEC));

		private static <T extends TreeDecorator> RegistryObject<TreeDecoratorType<T>> registerTreeDecorator(String name, TreeDecoratorType<T> type) {
			return DECORATORS.register(name, () -> type);
		}
	}
}
