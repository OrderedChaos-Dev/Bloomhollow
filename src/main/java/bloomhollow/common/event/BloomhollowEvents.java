package bloomhollow.common.event;

import bloomhollow.common.blocks.BloomhollowPortalBlock;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BloomhollowEvents {

	@SubscribeEvent
	public void createPortal(PlayerInteractEvent.RightClickBlock event) {
		if(event.getItemStack().getItem() == Items.STICK) {
			BlockPos pos = event.getPos().above();
			
			((BloomhollowPortalBlock)BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get()).trySpawnPortal(event.getLevel(), pos);
		}
	}
}
