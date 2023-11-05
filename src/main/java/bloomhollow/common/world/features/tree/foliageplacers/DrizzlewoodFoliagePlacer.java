package bloomhollow.common.world.features.tree.foliageplacers;

import java.util.Random;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import bloomhollow.core.registry.BloomhollowBlocks;
import bloomhollow.core.registry.world.BloomhollowTreePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class DrizzlewoodFoliagePlacer extends FoliagePlacer  {

	public static final Codec<DrizzlewoodFoliagePlacer> CODEC = RecordCodecBuilder.create((p) -> {
		return foliagePlacerParts(p).apply(p, DrizzlewoodFoliagePlacer::new);
	});
	
	public DrizzlewoodFoliagePlacer(IntProvider f1, IntProvider f2) {
		super(f1, f2);
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return BloomhollowTreePlacers.Foliage.DRIZZLEWOOD_FOLIAGE_PLACER.get();
	}

	@Override
	protected void createFoliage(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, Random rand, TreeConfiguration config, int p_161350_, FoliagePlacer.FoliageAttachment foliage,
			int p_161352_, int p_161353_, int p_161354_) {
		
		if(world.isStateAtPosition(foliage.pos().above(), (state) -> state.isAir())) {
			placer.accept(foliage.pos().above(), config.foliageProvider.getState(rand, foliage.pos()));
		}
		
		this.placeLeavesRow(world, placer, rand, config, foliage.pos(), 2, 0, foliage.doubleTrunk());
		
		BlockPos pos = foliage.pos().below();
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(rand.nextBoolean() && world.isStateAtPosition(pos.offset(i, 0, j).above(), (state) -> state.is(BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get()))) {
					if(world.isStateAtPosition(pos.offset(i, 0, j), (state) -> state.isAir())) {
						placer.accept(pos.offset(i, 0, j), config.foliageProvider.getState(rand, foliage.pos()));
						if(rand.nextBoolean()) {
							if(world.isStateAtPosition(pos.offset(i, 0, j).below(), (state) -> state.isAir())) {
								placer.accept(pos.offset(i, 0, j).below(), config.foliageProvider.getState(rand, foliage.pos()));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int foliageHeight(Random rand, int h, TreeConfiguration config) {
		return 3;
	}

	@Override
	protected boolean shouldSkipLocation(Random rand, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
		if (p_230373_3_ == 0) {
			return (p_230373_2_ > 1 || p_230373_4_ > 1) && p_230373_2_ != 0 && p_230373_4_ != 0;
		} else {
			return p_230373_2_ == p_230373_5_ && p_230373_4_ == p_230373_5_ && p_230373_5_ > 0;
		}
	}

}
