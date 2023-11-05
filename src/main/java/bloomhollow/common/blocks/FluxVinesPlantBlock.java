package bloomhollow.common.blocks;

import java.util.Random;
import java.util.function.ToIntFunction;

import bloomhollow.common.blocks.FluxVinesPlantBlock.BerryColor;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FluxVinesPlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock {
	
	public static final EnumProperty<BerryColor> COLOR = EnumProperty.create("color", BerryColor.class);
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public FluxVinesPlantBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().emissiveRendering((state, level, pos) -> true).lightLevel(emission()).instabreak().sound(SoundType.CAVE_VINES), Direction.DOWN, SHAPE, false);
		this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, BerryColor.NONE));
	}
	
	public static ToIntFunction<BlockState> emission() {
		return (state) -> {
			return state.getValue(COLOR) != BerryColor.NONE ? 5 : 2;
		};
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
	}
	
	@Override
	protected BlockState updateHeadAfterConvertedFromBody(BlockState state, BlockState state2) {
		return state2.setValue(COLOR, state.getValue(COLOR));
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
	protected GrowingPlantHeadBlock getHeadBlock() {
		return (GrowingPlantHeadBlock) BloomhollowBlocks.FLUX_VINES.get();
	}

	public static enum BerryColor implements StringRepresentable {
		NONE("none"),
		ORANGE("orange"),
		YELLOW("yellow"),
		BLUE("blue");
		
		private String name;
		
		private BerryColor(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
		
		public static BerryColor getRandom(Random random) {
			int length = BerryColor.values().length - 1;
			return BerryColor.values()[1 + random.nextInt(length)];
		}
	}
}
