package bloomhollow.common.blocks;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BerylWillowVinesBlock extends Block {

	public BerylWillowVinesBlock() {
		super(Properties.of(Material.PLANT).sound(SoundType.GRASS).noCollission().instabreak().lightLevel((state) -> 1).emissiveRendering((state, level, pos) -> true));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState above = level.getBlockState(pos.above());
		return (above.is(this) || above.is(BlockTags.LEAVES));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		if ((direction == Direction.DOWN || direction == Direction.UP) && !state.canSurvive(level, pos)) {
			level.scheduleTick(pos, this, 1);
		}

		return super.updateShape(state, direction, state2, level, pos, pos2);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}
}
