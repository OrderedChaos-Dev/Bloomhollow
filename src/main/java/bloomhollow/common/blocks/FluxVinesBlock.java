package bloomhollow.common.blocks;

import java.util.Random;

import bloomhollow.common.blocks.FluxVinesPlantBlock.BerryColor;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FluxVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock {

	public static final EnumProperty<BerryColor> COLOR = FluxVinesPlantBlock.COLOR;
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	
	public FluxVinesBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().emissiveRendering((state, level, pos) -> true).lightLevel(FluxVinesPlantBlock.emission()).instabreak().sound(SoundType.CAVE_VINES),
				Direction.DOWN, SHAPE, false, 0.1D);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(COLOR, BerryColor.NONE));

	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(Random random) {
		return 1;
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	protected Block getBodyBlock() {
		return BloomhollowBlocks.FLUX_VINES_PLANT.get();
	}

	@Override
	protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState state2) {
		return state2.setValue(COLOR, state.getValue(COLOR));
	}

	@Override
	protected BlockState getGrowIntoState(BlockState state, Random random) {
		float chance = 0.08F;
		return super.getGrowIntoState(state, random).setValue(COLOR, random.nextFloat() <= chance ? BerryColor.getRandom(random) : BerryColor.NONE);
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClientSide) {
		return state.getValue(COLOR) == BerryColor.NONE;
	}

	@Override
	public boolean isBonemealSuccess(Level level, Random p_152976_, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.setValue(COLOR, BerryColor.getRandom(random)), 2);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(COLOR);
	}
}
