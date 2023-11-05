package bloomhollow.common.blocks;

import java.util.Random;

import bloomhollow.common.util.BloomhollowPortalShape;
import bloomhollow.core.registry.BloomhollowParticles;
import bloomhollow.core.registry.world.BloomhollowDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BloomhollowPortalBlock extends Block {

	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	protected static final int AABB_OFFSET = 2;
	protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

	public BloomhollowPortalBlock() {
		super(BlockBehaviour.Properties.of(Material.PORTAL).strength(-1.0F).noCollission().noDrops().lightLevel((state) -> 3).sound(SoundType.GLASS));
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch((Direction.Axis)state.getValue(AXIS)) {
		case Z:
			return Z_AXIS_AABB;
		case X:
		default:
			return X_AXIS_AABB;
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
		Direction.Axis direction$axis = dir.getAxis();
		Direction.Axis direction$axis1 = state.getValue(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && !state2.is(this) && !(new PortalShape(level, pos, direction$axis1)).isComplete() ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, state2, level, pos, pos2);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if(level.isClientSide) {
			entity.handleInsidePortal(pos);
		}
		if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
	        int i = entity.getPortalWaitTime();
	        entity.portalTime++;
			if(entity.isOnPortalCooldown()) {
				entity.setPortalCooldown();
			} else {
				if(!level.isClientSide) {
					if(!entity.isOnPortalCooldown() && entity.portalTime >= i) {
						ResourceKey<Level> destination = level.dimension() == BloomhollowDimensions.BLOOMHOLLOW ? Level.OVERWORLD : BloomhollowDimensions.BLOOMHOLLOW;
				        ServerLevel serverLevel = ((ServerLevel)level).getServer().getLevel(destination);
				        
				        entity.level.getProfiler().push("bloomhollow_portal");
				        entity.portalTime = i;
				        entity.setPortalCooldown();
				        entity.changeDimension(serverLevel, new BloomhollowDimensions.BloomhollowTeleporter(serverLevel));
				        entity.level.getProfiler().pop();
					}
				}
			}
		}
	}
	
	public BloomhollowPortalShape getPortalShape(LevelAccessor level, BlockPos pos) {
		BloomhollowPortalShape shape = new BloomhollowPortalShape(level, pos, Direction.Axis.X);
		if(shape.isValid()) {
			return shape;
		} else {
			shape = new BloomhollowPortalShape(level, pos, Direction.Axis.Z);
			return shape.isValid() ? shape : null;
		}
	}
	
	public boolean trySpawnPortal(LevelAccessor level, BlockPos pos) {
		BloomhollowPortalShape shape = this.getPortalShape(level, pos);
		if(shape != null) {
			shape.createPortalBlocks();
			return true;
		}
		return false;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		if (random.nextInt(100) == 0) {
			level.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}

		double d0 = (double)pos.getX() + random.nextDouble();
		double d1 = (double)pos.getY() + random.nextDouble();
		double d2 = (double)pos.getZ() + random.nextDouble();
		double d3 = ((double)random.nextFloat() - 0.5D) * 0.5D;
		double d4 = ((double)random.nextFloat() - 0.5D) * 0.5D;
		double d5 = ((double)random.nextFloat() - 0.5D) * 0.5D;
		int j = random.nextInt(2) * 2 - 1;
		if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
			d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
			d3 = (double)(random.nextFloat() * 2.0F * (float)j);
		} else {
			d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
			d5 = (double)(random.nextFloat() * 2.0F * (float)j);
		}

		level.addParticle(BloomhollowParticles.BRIGHTSHROOM_SPORE.get(), d0, d1, d2, d3, d4, d5);

	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(rotation) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			switch((Direction.Axis)state.getValue(AXIS)) {
			case Z:
				return state.setValue(AXIS, Direction.Axis.X);
			case X:
				return state.setValue(AXIS, Direction.Axis.Z);
			default:
				return state;
			}
		default:
			return state;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}
