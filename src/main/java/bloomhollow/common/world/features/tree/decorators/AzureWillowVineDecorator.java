package bloomhollow.common.world.features.tree.decorators;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;

import bloomhollow.core.registry.BloomhollowBlocks;
import bloomhollow.core.registry.world.BloomhollowTreePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class AzureWillowVineDecorator extends TreeDecorator {
   public static final Codec<AzureWillowVineDecorator> CODEC = Codec.unit(() -> {
      return AzureWillowVineDecorator.INSTANCE;
   });
   public static final AzureWillowVineDecorator INSTANCE = new AzureWillowVineDecorator();

   protected TreeDecoratorType<?> type() {
      return BloomhollowTreePlacers.Decorator.AZURE_WILLOW_VINES.get();
   }

   public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, Random random, List<BlockPos> logs, List<BlockPos> leaves) {
      leaves.forEach((pos) -> {
    	  if(random.nextFloat() < 0.25F) {
    		  placeVine(level, pos.below(), random, consumer);
    	  }
      });
   }
   
   private void placeVine(LevelSimulatedReader level, BlockPos pos, Random random, BiConsumer<BlockPos, BlockState> consumer) {
	   if(Feature.isAir(level, pos))
		   consumer.accept(pos, BloomhollowBlocks.AZURE_WILLOW_VINES.get().defaultBlockState());
	   int height = random.nextInt(5) + 1;
	   
	   for(BlockPos blockpos = pos.below(); Feature.isAir(level, blockpos) && height > 0; height--) {
		   consumer.accept(blockpos, BloomhollowBlocks.AZURE_WILLOW_VINES.get().defaultBlockState());
		   blockpos = blockpos.below();
	   }

   }
}