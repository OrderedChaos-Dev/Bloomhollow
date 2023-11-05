package bloomhollow.core.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(Gui.class)
public class GuiMixin {
	
	@Shadow
	protected Minecraft minecraft;

	@Shadow
	protected int screenWidth;

	@Shadow
	protected int screenHeight;

	@Inject(at = @At("HEAD"), method="renderPortalOverlay(F)V", cancellable = true)
	protected void renderPortalOverlay(float f, CallbackInfo info) {
		AABB aabb = minecraft.player.getBoundingBox();
		BlockPos blockpos = new BlockPos(aabb.minX + 0.001D, aabb.minY + 0.001D, aabb.minZ + 0.001D);
		BlockPos blockpos1 = new BlockPos(aabb.maxX - 0.001D, aabb.maxY - 0.001D, aabb.maxZ - 0.001D);
		if (minecraft.player.level.hasChunksAt(blockpos, blockpos1)) {
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for(int i = blockpos.getX(); i <= blockpos1.getX(); ++i) {
				for(int j = blockpos.getY(); j <= blockpos1.getY(); ++j) {
					for(int k = blockpos.getZ(); k <= blockpos1.getZ(); ++k) {
						blockpos$mutableblockpos.set(i, j, k);
						BlockState blockstate = minecraft.player.level.getBlockState(blockpos$mutableblockpos);

						if(blockstate.is(BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get())) {
							if (f < 1.0F) {
								f *= f;
								f *= f;
								f = f * 0.8F + 0.2F;
							}

							RenderSystem.disableDepthTest();
							RenderSystem.depthMask(false);
							RenderSystem.defaultBlendFunc();
							RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f);
							RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
							RenderSystem.setShader(GameRenderer::getPositionTexShader);
							TextureAtlasSprite textureatlassprite = this.minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get().defaultBlockState());
							float f0 = textureatlassprite.getU0();
							float f1 = textureatlassprite.getV0();
							float f2 = textureatlassprite.getU1();
							float f3 = textureatlassprite.getV1();
							Tesselator tesselator = Tesselator.getInstance();
							BufferBuilder bufferbuilder = tesselator.getBuilder();
							bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
							bufferbuilder.vertex(0.0D, (double)this.screenHeight, -90.0D).uv(f0, f3).endVertex();
							bufferbuilder.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0D).uv(f2, f3).endVertex();
							bufferbuilder.vertex((double)this.screenWidth, 0.0D, -90.0D).uv(f2, f1).endVertex();
							bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f0, f1).endVertex();
							tesselator.end();
							RenderSystem.depthMask(true);
							RenderSystem.enableDepthTest();
							RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
							info.cancel();
						}
					}
				}
			}
		}
		
		if(minecraft.player.isColliding(minecraft.player.blockPosition(), BloomhollowBlocks.BLOOMHOLLOW_PORTAL.get().defaultBlockState())) {
			
		}
	}
}
