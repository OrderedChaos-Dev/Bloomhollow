package bloomhollow.common.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlowingLeavesBlock extends LeavesBlock {

	public GlowingLeavesBlock(int lightLevel) {
		super(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel((state) -> lightLevel).emissiveRendering((state, level, pos) -> true));
	}
}
