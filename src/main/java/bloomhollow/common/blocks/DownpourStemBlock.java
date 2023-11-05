package bloomhollow.common.blocks;

import java.util.Optional;
import java.util.Random;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DownpourStemBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {

	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape NORTH_SHAPE = Block.box(5.0D, 0.0D, 9.0D, 11.0D, 16.0D, 15.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 16.0D, 7.0D);
	protected static final VoxelShape EAST_SHAPE = Block.box(1.0D, 0.0D, 5.0D, 7.0D, 16.0D, 11.0D);
	protected static final VoxelShape WEST_SHAPE = Block.box(9.0D, 0.0D, 5.0D, 15.0D, 16.0D, 11.0D);
	
	public DownpourStemBlock() {
		super(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS_CARPET));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.below());
		BlockState above = level.getBlockState(pos.above());
		return (below.is(this) || below.is(BlockTags.DIRT) || (level.getFluidState(pos).is(Fluids.WATER) && below.isFaceSturdy(level, pos.below(), Direction.UP))) && (above.is(this) || above.is(BloomhollowBlocks.DOWNPOUR_FLOWER.get()));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		if ((direction == Direction.DOWN || direction == Direction.UP) && !state.canSurvive(level, pos)) {
			level.scheduleTick(pos, this, 1);
		}

		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, state2, level, pos, pos2);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch((Direction)state.getValue(FACING)) {
		case SOUTH:
			return SOUTH_SHAPE;
		case NORTH:
		default:
			return NORTH_SHAPE;
		case WEST:
			return WEST_SHAPE;
		case EAST:
			return EAST_SHAPE;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClientSide) {
		Optional<BlockPos> optional = BlockUtil.getTopConnectedBlock(level, pos, state.getBlock(), Direction.UP, BloomhollowBlocks.DOWNPOUR_FLOWER.get());
		if (!optional.isPresent()) {
			return false;
		} else {
			BlockPos blockpos = optional.get().above();
			BlockState blockstate = level.getBlockState(blockpos);
			return DownpourFlowerBlock.canPlaceAt(level, blockpos, blockstate);
		}
	}

	@Override
	public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
		Optional<BlockPos> optional = BlockUtil.getTopConnectedBlock(level, pos, state.getBlock(), Direction.UP, BloomhollowBlocks.DOWNPOUR_FLOWER.get());
		if (optional.isPresent()) {
			BlockPos blockpos = optional.get();
			BlockPos blockpos1 = blockpos.above();
			Direction direction = state.getValue(FACING);
			place(level, blockpos, level.getFluidState(blockpos), direction);
			DownpourFlowerBlock.place(level, blockpos1, level.getFluidState(blockpos1), direction);
		}
	}

	protected static boolean place(LevelAccessor level, BlockPos pos, FluidState fluidstate, Direction dir) {
		BlockState blockstate = BloomhollowBlocks.DOWNPOUR_STEM.get().defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.isSourceOfType(Fluids.WATER))).setValue(FACING, dir);
		return level.setBlock(pos, blockstate, 3);
	}
}
