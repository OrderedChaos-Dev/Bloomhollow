package bloomhollow.common.util;

import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BloomhollowPortalShape {
   private static final int MIN_WIDTH = 2;
   public static final int MAX_WIDTH = 21;
   private static final int MIN_HEIGHT = 3;
   public static final int MAX_HEIGHT = 21;
   private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> {
      return state.is(Blocks.CALCITE);
   };
   private final LevelAccessor level;
   private final Direction.Axis axis;
   private final Direction rightDir;
   private int numPortalBlocks;
   @Nullable
   private BlockPos bottomLeft;
   private int height;
   private final int width;

   public static Optional<BloomhollowPortalShape> findEmptyPortalShape(LevelAccessor p_77709_, BlockPos p_77710_, Direction.Axis p_77711_) {
      return findPortalShape(p_77709_, p_77710_, (p_77727_) -> {
         return p_77727_.isValid() && p_77727_.numPortalBlocks == 0;
      }, p_77711_);
   }

   public static Optional<BloomhollowPortalShape> findPortalShape(LevelAccessor p_77713_, BlockPos p_77714_, Predicate<BloomhollowPortalShape> p_77715_, Direction.Axis p_77716_) {
      Optional<BloomhollowPortalShape> optional = Optional.of(new BloomhollowPortalShape(p_77713_, p_77714_, p_77716_)).filter(p_77715_);
      if (optional.isPresent()) {
         return optional;
      } else {
         Direction.Axis direction$axis = p_77716_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new BloomhollowPortalShape(p_77713_, p_77714_, direction$axis)).filter(p_77715_);
      }
   }

   public BloomhollowPortalShape(LevelAccessor p_77695_, BlockPos p_77696_, Direction.Axis p_77697_) {
      this.level = p_77695_;
      this.axis = p_77697_;
      this.rightDir = p_77697_ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.bottomLeft = this.calculateBottomLeft(p_77696_);
      if (this.bottomLeft == null) {
         this.bottomLeft = p_77696_;
         this.width = 1;
         this.height = 1;
      } else {
         this.width = this.calculateWidth();
         if (this.width > 0) {
            this.height = this.calculateHeight();
         }
      }

   }

   @Nullable
   private BlockPos calculateBottomLeft(BlockPos p_77734_) {
      for(int i = Math.max(this.level.getMinBuildHeight(), p_77734_.getY() - 21); p_77734_.getY() > i && isEmpty(this.level.getBlockState(p_77734_.below())); p_77734_ = p_77734_.below()) {
      }

      Direction direction = this.rightDir.getOpposite();
      int j = this.getDistanceUntilEdgeAboveFrame(p_77734_, direction) - 1;
      return j < 0 ? null : p_77734_.relative(direction, j);
   }

   private int calculateWidth() {
      int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
      return i >= 2 && i <= 21 ? i : 0;
   }

   private int getDistanceUntilEdgeAboveFrame(BlockPos p_77736_, Direction p_77737_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i <= 21; ++i) {
         blockpos$mutableblockpos.set(p_77736_).move(p_77737_, i);
         BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
         if (!isEmpty(blockstate)) {
            if (FRAME.test(blockstate, this.level, blockpos$mutableblockpos)) {
               return i;
            }
            break;
         }

         BlockState blockstate1 = this.level.getBlockState(blockpos$mutableblockpos.move(Direction.DOWN));
         if (!FRAME.test(blockstate1, this.level, blockpos$mutableblockpos)) {
            break;
         }
      }

      return 0;
   }

   private int calculateHeight() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = this.getDistanceUntilTop(blockpos$mutableblockpos);
      return i >= 3 && i <= 21 && this.hasTopFrame(blockpos$mutableblockpos, i) ? i : 0;
   }

   private boolean hasTopFrame(BlockPos.MutableBlockPos p_77731_, int p_77732_) {
      for(int i = 0; i < this.width; ++i) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77731_.set(this.bottomLeft).move(Direction.UP, p_77732_).move(this.rightDir, i);
         if (!FRAME.test(this.level.getBlockState(blockpos$mutableblockpos), this.level, blockpos$mutableblockpos)) {
            return false;
         }
      }

      return true;
   }

   private int getDistanceUntilTop(BlockPos.MutableBlockPos p_77729_) {
      for(int i = 0; i < 21; ++i) {
         p_77729_.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
         if (!FRAME.test(this.level.getBlockState(p_77729_), this.level, p_77729_)) {
            return i;
         }

         p_77729_.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
         if (!FRAME.test(this.level.getBlockState(p_77729_), this.level, p_77729_)) {
            return i;
         }

         for(int j = 0; j < this.width; ++j) {
            p_77729_.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
            BlockState blockstate = this.level.getBlockState(p_77729_);
            if (!isEmpty(blockstate)) {
               return i;
            }

            if (blockstate.is(BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get())) {
               ++this.numPortalBlocks;
            }
         }
      }

      return 21;
   }

   private static boolean isEmpty(BlockState p_77718_) {
      return p_77718_.isAir() || p_77718_.is(BlockTags.FIRE) || p_77718_.is(BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get());
   }

   public boolean isValid() {
      return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
   }

   public void createPortalBlocks() {
      BlockState blockstate = BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
      BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((p_77725_) -> {
         this.level.setBlock(p_77725_, blockstate, 18);
      });
   }

   public boolean isComplete() {
      return this.isValid() && this.numPortalBlocks == this.width * this.height;
   }

   public static Vec3 getRelativePosition(BlockUtil.FoundRectangle p_77739_, Direction.Axis p_77740_, Vec3 p_77741_, EntityDimensions p_77742_) {
      double d0 = (double)p_77739_.axis1Size - (double)p_77742_.width;
      double d1 = (double)p_77739_.axis2Size - (double)p_77742_.height;
      BlockPos blockpos = p_77739_.minCorner;
      double d2;
      if (d0 > 0.0D) {
         float f = (float)blockpos.get(p_77740_) + p_77742_.width / 2.0F;
         d2 = Mth.clamp(Mth.inverseLerp(p_77741_.get(p_77740_) - (double)f, 0.0D, d0), 0.0D, 1.0D);
      } else {
         d2 = 0.5D;
      }

      double d4;
      if (d1 > 0.0D) {
         Direction.Axis direction$axis = Direction.Axis.Y;
         d4 = Mth.clamp(Mth.inverseLerp(p_77741_.get(direction$axis) - (double)blockpos.get(direction$axis), 0.0D, d1), 0.0D, 1.0D);
      } else {
         d4 = 0.0D;
      }

      Direction.Axis direction$axis1 = p_77740_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double d3 = p_77741_.get(direction$axis1) - ((double)blockpos.get(direction$axis1) + 0.5D);
      return new Vec3(d2, d4, d3);
   }
}
