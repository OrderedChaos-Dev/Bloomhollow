package bloomhollow.core.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BloomhollowCreativeModeTab extends CreativeModeTab {
	
	public static final BloomhollowCreativeModeTab INSTANCE = new BloomhollowCreativeModeTab();

	public BloomhollowCreativeModeTab() {
		super("bloomhollow");
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get().asItem());
	}

}
