package bloomhollow.common.world.features;

import com.mojang.serialization.Codec;

import bloomhollow.common.world.features.WaterfallFeature.DirectionalConfiguration;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterfallFeature extends Feature<DirectionalConfiguration> {
	
	public static final FluidState WATER = Fluids.WATER.defaultFluidState();

	public WaterfallFeature(Codec<DirectionalConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<DirectionalConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		Direction dir = context.config().direction;
		
		BlockPos blockpos = pos.offset(dir.getOpposite().getNormal());
		
		if(!level.getBlockState(blockpos).is(BloomhollowBlocks.SHADESTONE.get()))
			return false;
		
		for(BlockPos.MutableBlockPos mutable = pos.mutable(); level.isEmptyBlock(mutable); mutable.move(Direction.DOWN)) {
			if(level.getBlockState(mutable.below()).getBlock() == Blocks.WATER) {
				System.out.println(mutable);
				level.setBlock(blockpos, WATER.createLegacyBlock(), 2);
				level.scheduleTick(blockpos, WATER.getType(), 0);
				return true;
			}
		}

		return false;
	}
	
	public static class DirectionalConfiguration implements FeatureConfiguration {
		
		public static final Codec<DirectionalConfiguration> CODEC = Direction.CODEC.fieldOf("direction").xmap(DirectionalConfiguration::new, instance -> instance.direction).codec();
				
		public final Direction direction;

		public DirectionalConfiguration(Direction direction) {
			this.direction = direction;
		}
	}
}
