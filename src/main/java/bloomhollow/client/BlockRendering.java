package bloomhollow.client;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockRendering {

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderers() {
		RenderType cutout = RenderType.cutout();
		RenderType cutout_mipped = RenderType.cutoutMipped();
		
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.DRIZZLEWOOD_LEAVES.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.DOWNPOUR_STEM.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.DOWNPOUR_FLOWER.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.FLUX_VINES.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.FLUX_VINES_PLANT.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.AZURE_WILLOW_VINES.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.BERYL_WILLOW_VINES.get(), cutout_mipped);
		ItemBlockRenderTypes.setRenderLayer(BloomhollowBlocks.ABYSSAL_LOTUS.get(), cutout_mipped);
	}
}
