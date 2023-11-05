package bloomhollow.common.world.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class UndergroundKelpFeature extends Feature<NoneFeatureConfiguration> {
	public UndergroundKelpFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		int i = 0;
		WorldGenLevel worldgenlevel = context.level();
		BlockPos blockpos = context.origin();
		Random random = context.random();
		if (worldgenlevel.getBlockState(blockpos).is(Blocks.WATER)) {
			BlockState blockstate = Blocks.KELP.defaultBlockState();
			BlockState blockstate1 = Blocks.KELP_PLANT.defaultBlockState();
			int k = 1 + random.nextInt(10);

			for(int l = 0; l <= k; ++l) {
				if (worldgenlevel.getBlockState(blockpos).is(Blocks.WATER) && worldgenlevel.getBlockState(blockpos.above()).is(Blocks.WATER) && blockstate1.canSurvive(worldgenlevel, blockpos)) {
					if (l == k) {
						worldgenlevel.setBlock(blockpos, blockstate.setValue(KelpBlock.AGE, Integer.valueOf(random.nextInt(4) + 20)), 2);
						++i;
					} else {
						worldgenlevel.setBlock(blockpos, blockstate1, 2);
					}
				} else if (l > 0) {
					BlockPos blockpos2 = blockpos.below();
					if (blockstate.canSurvive(worldgenlevel, blockpos2) && !worldgenlevel.getBlockState(blockpos2.below()).is(Blocks.KELP)) {
						worldgenlevel.setBlock(blockpos2, blockstate.setValue(KelpBlock.AGE, Integer.valueOf(random.nextInt(4) + 20)), 2);
						++i;
					}
					break;
				}

				blockpos = blockpos.above();
			}
		}

		return i > 0;
	}
}