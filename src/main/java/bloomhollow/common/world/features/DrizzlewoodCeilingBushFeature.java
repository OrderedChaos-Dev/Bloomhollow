package bloomhollow.common.world.features;

import com.mojang.serialization.Codec;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DrizzlewoodCeilingBushFeature extends Feature<NoneFeatureConfiguration> {

	public DrizzlewoodCeilingBushFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		
		if(level.isEmptyBlock(pos)) {
			level.setBlock(pos, BloomhollowBlocks.DRIZZLEWOOD_LOG.get().defaultBlockState(), 3);
			if(level.isEmptyBlock(pos.below())) {
				level.setBlock(pos.below(), BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1), 3);
			}
			for(int i = -2; i <= 2; i++) {
				for(int j = -2; j <= 2; j++) {
					BlockPos temp = pos.offset(i, 0, j);
					if(level.isEmptyBlock(temp) && (Math.abs(i) != 2 && Math.abs(j) != 2)) {
						level.setBlock(temp, BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1), 3);
						if(context.random().nextBoolean()) {
							if(level.isEmptyBlock(temp.below())) {
								level.setBlock(temp.below(), BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1), 3);
							}
						}
					}
				}
			}
		}
		return false;
	}

}
