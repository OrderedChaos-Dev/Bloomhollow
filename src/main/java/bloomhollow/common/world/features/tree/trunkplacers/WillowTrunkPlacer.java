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

public class WillowTrunkPlacer extends TrunkPlacer {
	public static final Codec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.create((x) -> {
		return trunkPlacerParts(x).apply(x, WillowTrunkPlacer::new);
	});

	public WillowTrunkPlacer(int baseHeight, int heightA, int heightB) {
		super(baseHeight, heightA, heightB);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return BloomhollowTreePlacers.Trunk.WILLOW_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, Random rand, int height, BlockPos pos, TreeConfiguration config) {
		List<FoliagePlacer.FoliageAttachment> leafPlacements = new ArrayList<>();
		Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		Direction dir2 = rand.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();
		
		BlockPos.MutableBlockPos blockposmutable = pos.mutable();
		
		for(Direction d : Direction.Plane.HORIZONTAL) {
			placeLog(world, placer, rand, blockposmutable.offset(d.getNormal()), config);
		}
		
		for(int i = 0; i < height; i++) {
			placeLog(world, placer, rand, blockposmutable, config);
			if(rand.nextFloat() < 0.3F) {
				blockposmutable.move(dir);
				placeLog(world, placer, rand, blockposmutable, config);
			}
			blockposmutable.move(Direction.UP);
		}
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.below().immutable(), 0, false));
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.immutable(), 0, false));
		
		blockposmutable = pos.mutable();
		for(int i = 0; i < height; i++) {
			placeLog(world, placer, rand, blockposmutable, config);
			if(rand.nextFloat() < 0.5F) {
				blockposmutable.move(dir2);
				placeLog(world, placer, rand, blockposmutable, config);
			}
			blockposmutable.move(Direction.UP);
		}
		
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.below().immutable(), 0, false));
		leafPlacements.add(new FoliagePlacer.FoliageAttachment(blockposmutable.immutable(), 0, false));
		
		return leafPlacements;
	}
		
}