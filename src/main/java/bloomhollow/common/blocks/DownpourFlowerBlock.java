package bloomhollow.common.blocks;

import java.util.Random;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class DownpourFlowerBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
	
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public DownpourFlowerBlock() {
		super(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS_CARPET));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState below = level.getBlockState(blockpos);
		return below.is(this) || below.is(BloomhollowBlocks.DOWNPOUR_STEM.get()) || below.is(BlockTags.DIRT) || (level.getFluidState(pos).is(Fluids.WATER) && below.isFaceSturdy(level, pos.below(), Direction.UP));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (state.getValue(WATERLOGGED)) {
				level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			}

			return direction == Direction.UP && state2.is(this) ? BloomhollowBlocks.DOWNPOUR_STEM.get().withPropertiesOf(state) : super.updateShape(state, direction, state2, level, pos, pos2);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext contect) {
		BlockState blockstate = contect.getLevel().getBlockState(contect.getClickedPos().below());
		FluidState fluidstate = contect.getLevel().getFluidState(contect.getClickedPos());
		boolean flag = blockstate.is(BloomhollowBlocks.DOWNPOUR_FLOWER.get()) || blockstate.is(BloomhollowBlocks.DOWNPOUR_STEM.get());
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.isSourceOfType(Fluids.WATER))).setValue(FACING, flag ? blockstate.getValue(FACING) : contect.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		double x = pos.getX() + (state.getValue(FACING).getStepX() * 0.125) + 0.5;
		double z = pos.getZ() + (state.getValue(FACING).getStepZ() * 0.125) + 0.5;
		
		for(int i = 0; i < 7; i++) {
			double dx = (random.nextDouble() - random.nextDouble()) / 16.0D;
			double dz = (random.nextDouble() - random.nextDouble()) / 16.0D;
			level.addParticle(ParticleTypes.DRIPPING_WATER, x + dx, pos.getY() + (3.0 / 16.0), z + dz, 0, 0, 0);
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClientSide) {
		BlockState blockstate = level.getBlockState(pos.above());
		return canReplace(blockstate);
	}

	@Override
	public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
	      BlockPos blockpos = pos.above();
	      BlockState blockstate = level.getBlockState(blockpos);
	      if (canPlaceAt(level, blockpos, blockstate)) {
	         Direction direction = state.getValue(FACING);
	         DownpourStemBlock.place(level, pos, state.getFluidState(), direction);
	         place(level, blockpos, blockstate.getFluidState(), direction);
	      }
	}
	
	protected static boolean canPlaceAt(LevelHeightAccessor level, BlockPos pos, BlockState state) {
		return !level.isOutsideBuildHeight(pos) && canReplace(state);
	}

	protected static boolean place(LevelAccessor level, BlockPos pos, FluidState state, Direction dir) {
		BlockState blockstate = BloomhollowBlocks.DOWNPOUR_FLOWER.get().defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(state.isSourceOfType(Fluids.WATER))).setValue(FACING, dir);
		return level.setBlock(pos, blockstate, 3);
	}
	
	private static boolean canReplace(BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}
}
