package bloomhollow.common.blocks;

import java.util.Random;

import bloomhollow.core.registry.BloomhollowParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BrightshroomBlock extends MushroomBlock {
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public BrightshroomBlock() {
		super(Block.Properties.of(Material.PLANT, MaterialColor.COLOR_YELLOW)
				.noCollission()
				.randomTicks()
				.instabreak()
				.emissiveRendering((state, world, pos) -> true)
				.sound(SoundType.GRASS).lightLevel((state) -> {
		      return 14;
		   }), () -> null);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(context.getLevel().getRandom());
		return this.defaultBlockState().setValue(FACING, facing);
	}

	@Override
	public boolean growMushroom(ServerLevel world, BlockPos pos, BlockState state, Random rand) {
		return false;
	}
	
	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
			return true;
		} else {
			return blockstate.canSustainPlant(worldIn, blockpos, Direction.UP, this);
		}
	}
	
	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		if (random.nextInt(3) == 0) {
			for(int i = 0; i < 3; i++) {
				double x = (double)pos.getX() + (1.5 * (random.nextDouble() - random.nextDouble()));
				double y = (double)pos.getY() + 0.3 + (1.1 * (random.nextDouble() - random.nextDouble()));
				double z = (double)pos.getZ() + (1.5 * (random.nextDouble() - random.nextDouble()));
				level.addParticle(BloomhollowParticles.BRIGHTSHROOM_SPORE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}