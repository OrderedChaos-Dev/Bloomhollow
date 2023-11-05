package bloomhollow.common.blocks;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

public class WillowSaplingBlock extends SaplingBlock {

	public WillowSaplingBlock(AbstractTreeGrower grower, int lightLevel) {
		super(grower, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).lightLevel((state) -> lightLevel).emissiveRendering((state, level, pos) -> true));
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.BASE_STONE_OVERWORLD);
	}
	
	public static class BasicTreeGrower extends AbstractTreeGrower {
		
		private Supplier<RegistryObject<ConfiguredFeature<?, ?>>> tree;
		
		public BasicTreeGrower(Supplier<RegistryObject<ConfiguredFeature<?, ?>>> tree) {
			this.tree = tree;
		}
		
		protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean canSpawnBeehive) {
			return tree.get().getHolder().get();
		}
		
	}
}
