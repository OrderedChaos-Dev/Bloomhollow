package bloomhollow.common.blocks;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DrizzlewoodLeavesBlock extends LeavesBlock {

	public DrizzlewoodLeavesBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		boolean isRaining = level.isRainingAt(pos.above());
		int chance = isRaining ? 2 : 3;
		
		if (random.nextInt(chance) == 0) {
			BlockPos blockpos = pos.below();
			BlockState blockstate = level.getBlockState(blockpos);
			if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
				for(int i = 0; i < random.nextInt(5) + 1; i++) {
					double d0 = (double)pos.getX() + random.nextDouble();
					double d1 = (double)pos.getY() - 0.05D;
					double d2 = (double)pos.getZ() + random.nextDouble();
					level.addParticle(ParticleTypes.DRIPPING_WATER, true, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
