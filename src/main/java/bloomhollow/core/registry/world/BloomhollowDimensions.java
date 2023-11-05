package bloomhollow.core.registry.world;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowDimensions {
	
	public static final ResourceKey<Level> BLOOMHOLLOW = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Bloomhollow.MOD_ID, "bloomhollow"));
	
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<PoiType> PORTAL_POI = POI_TYPES.register("bloomhollow_portal", () -> new PoiType(Set.of(BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get().defaultBlockState()), 0, 1));

	public static class BloomhollowTeleporter implements ITeleporter {

		protected final ServerLevel level;

		public BloomhollowTeleporter(ServerLevel level) {
			this.level = level;
		}
		
		@Override
		public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
			Optional<BlockUtil.FoundRectangle> result = findOrCreatePortal(entity.blockPosition());
			if(result.isPresent()) {
				return PortalShape.createPortalInfo(destWorld, result.get(), Axis.Z, Vec3.ZERO, entity.getDimensions(entity.getPose()), Vec3.ZERO, entity.getYRot(), entity.getXRot());
			}

			return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
		}
		
		public Optional<BlockUtil.FoundRectangle> findOrCreatePortal(BlockPos pos) {
			Optional<BlockUtil.FoundRectangle> result = findPortalAround(pos, this.level.getWorldBorder());
			if(result.isPresent()) {
				return result;
			}
			
			result = createPortal(pos, level.getRandom().nextBoolean() ? Direction.Axis.X : Direction.Axis.Z);
			return result;
		}

		public Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos pos, WorldBorder border) {
			PoiManager poimanager = this.level.getPoiManager();
			int searchRadius = 128;
			poimanager.ensureLoadedAndValid(this.level, pos, searchRadius);
			Optional<PoiRecord> optional = poimanager.getInSquare((poi) -> {
				return poi == PORTAL_POI.get();
			}, pos, searchRadius, PoiManager.Occupancy.ANY).filter((poi) -> {
				return border.isWithinBounds(poi.getPos());
			}).sorted(Comparator.<PoiRecord>comparingDouble((poi) -> {
				return poi.getPos().distSqr(pos);
			}).thenComparingInt((poi) -> {
				return poi.getPos().getY();
			})).filter((poi) -> {
				return this.level.getBlockState(poi.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS);
			}).findFirst();
			return optional.map((poi) -> {
				BlockPos blockpos = poi.getPos();
				this.level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
				BlockState blockstate = this.level.getBlockState(blockpos);
				return BlockUtil.getLargestRectangleAround(blockpos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (temppos) -> {
					return this.level.getBlockState(temppos) == blockstate;
				});
			});
		}


		public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos pos, Direction.Axis axis) {
			System.out.println(axis);
			Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
			double d0 = -1.0D;
			BlockPos blockpos = null;
			double d1 = -1.0D;
			BlockPos blockpos1 = null;
			WorldBorder worldborder = this.level.getWorldBorder();
			int i = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

			for(BlockPos.MutableBlockPos mutPos : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
				int j = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, mutPos.getX(), mutPos.getZ()));
				int k = 1;
				if (worldborder.isWithinBounds(mutPos) && worldborder.isWithinBounds(mutPos.move(direction, 1))) {
					mutPos.move(direction.getOpposite(), 1);

					for(int l = j; l >= this.level.getMinBuildHeight(); --l) {
						mutPos.setY(l);
						if (this.level.isEmptyBlock(mutPos)) {
							int i1;
							for(i1 = l; l > this.level.getMinBuildHeight() && this.level.isEmptyBlock(mutPos.move(Direction.DOWN)); --l) {
							}

							if (l + 4 <= i) {
								int j1 = i1 - l;
								if (j1 <= 0 || j1 >= 3) {
									mutPos.setY(l);
									if (this.canHostFrame(mutPos, blockpos$mutableblockpos, direction, 0)) {
										double d2 = pos.distSqr(mutPos);
										if (this.canHostFrame(mutPos, blockpos$mutableblockpos, direction, -1) && this.canHostFrame(mutPos, blockpos$mutableblockpos, direction, 1) && (d0 == -1.0D || d0 > d2)) {
											d0 = d2;
											blockpos = mutPos.immutable();
										}

										if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
											d1 = d2;
											blockpos1 = mutPos.immutable();
										}
									}
								}
							}
						}
					}
				}
			}

			if (d0 == -1.0D && d1 != -1.0D) {
				blockpos = blockpos1;
				d0 = d1;
			}

			if (d0 == -1.0D) {
				int k1 = Math.max(this.level.getMinBuildHeight() - -1, 70);
				int i2 = i - 9;
				if (i2 < k1) {
					return Optional.empty();
				}

				blockpos = (new BlockPos(pos.getX(), Mth.clamp(pos.getY(), k1, i2), pos.getZ())).immutable();
				Direction direction1 = direction.getClockWise();
				if (!worldborder.isWithinBounds(blockpos)) {
					return Optional.empty();
				}

				for(int i3 = -1; i3 < 2; ++i3) {
					for(int j3 = 0; j3 < 2; ++j3) {
						for(int k3 = -1; k3 < 3; ++k3) {
							BlockState blockstate1 = k3 < 0 ? Blocks.CALCITE.defaultBlockState() : Blocks.AIR.defaultBlockState();
							blockpos$mutableblockpos.setWithOffset(blockpos, j3 * direction.getStepX() + i3 * direction1.getStepX(), k3, j3 * direction.getStepZ() + i3 * direction1.getStepZ());
							this.level.setBlockAndUpdate(blockpos$mutableblockpos, blockstate1);
						}
					}
				}
			}

			for(int l1 = -1; l1 < 3; ++l1) {
				for(int j2 = -1; j2 < 4; ++j2) {
					if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
						blockpos$mutableblockpos.setWithOffset(blockpos, l1 * direction.getStepX(), j2, l1 * direction.getStepZ());
						this.level.setBlock(blockpos$mutableblockpos, Blocks.CALCITE.defaultBlockState(), 3);
					}
				}
			}

			BlockState blockstate = BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, axis);

			for(int k2 = 0; k2 < 2; ++k2) {
				for(int l2 = 0; l2 < 3; ++l2) {
					blockpos$mutableblockpos.setWithOffset(blockpos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
					this.level.setBlock(blockpos$mutableblockpos, blockstate, 18);
				}
			}

			return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
		}

		private boolean canHostFrame(BlockPos pos, BlockPos.MutableBlockPos mutPos, Direction dir, int k) {
			Direction direction = dir.getClockWise();

			for(int i = -1; i < 3; ++i) {
				for(int j = -1; j < 4; ++j) {
					mutPos.setWithOffset(pos, dir.getStepX() * i + direction.getStepX() * k, j, dir.getStepZ() * i + direction.getStepZ() * k);
					if (j < 0 && !this.level.getBlockState(mutPos).getMaterial().isSolid()) {
						return false;
					}

					if (j >= 0 && !this.level.isEmptyBlock(mutPos)) {
						return false;
					}
				}
			}

			return true;
		}	
	}
}
