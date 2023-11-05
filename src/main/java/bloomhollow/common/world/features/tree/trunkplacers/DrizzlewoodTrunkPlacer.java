package bloomhollow.common.world.features.tree.trunkplacers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import bloomhollow.core.registry.BloomhollowBlocks;
import bloomhollow.core.registry.world.BloomhollowTreePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class DrizzlewoodTrunkPlacer extends TrunkPlacer {
	public static final Codec<DrizzlewoodTrunkPlacer> CODEC = RecordCodecBuilder.create((x) -> {
		return trunkPlacerParts(x).apply(x, DrizzlewoodTrunkPlacer::new);
	});

	public DrizzlewoodTrunkPlacer(int baseHeight, int heightA, int heightB) {
		super(baseHeight, heightA, heightB);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return BloomhollowTreePlacers.Trunk.DRIZZLEWOOD_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, Random rand, int height, BlockPos pos, TreeConfiguration config) {
		List<FoliagePlacer.FoliageAttachment> leafPlacements = new ArrayList<>();
		Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		BlockState log = config.trunkProvider.getState(rand, pos);

		BlockPos.MutableBlockPos blockposmutable = pos.mutable();
		int offsetHeight = (height / 2) - rand.nextInt(5) + rand.nextInt(3);
		
		for(int i = 0; i < height; i++) {
			placeLog(world, placer, rand, blockposmutable, config);
			if(rand.nextFloat() < 0.2F && i > 4 && i < height - 3) {
				Direction branchDir = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
				BlockPos.MutableBlockPos branchPos = new BlockPos(blockposmutable).mutable().move(branchDir);
				placer.accept(branchPos, log.setValue(RotatedPillarBlock.AXIS, branchDir.getAxis()));
				branchPos.move(branchDir).move(Direction.UP);
				placer.accept(branchPos, log.setValue(RotatedPillarBlock.AXIS, branchDir.getAxis()));
				leafPlacements.add(new FoliagePlacer.FoliageAttachment(branchPos.immutable(), 0, false));
			}
			if(i == offsetHeight) {
				blockposmutable.move(dir);
			}
			
			blockposmutable.move(Direction.UP);
		}
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.below().immutable(), 0, false));
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.immutable(), 0, false));
		return leafPlacements;
	}
		
}