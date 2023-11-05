package bloomhollow.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BrightshroomSporeParticle extends SuspendedParticle {

	public BrightshroomSporeParticle(ClientLevel level, SpriteSet sprite, double x, double y, double z, double dx, double dy, double dz) {
		super(level, sprite, x, y, z, dx, dy, dz);
		this.gravity = 0.01F;
		this.setLifetime(Mth.randomBetweenInclusive(level.random, 500, 1000));
		this.setColor(0.9F, 0.9F, 0.1F);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Provider(SpriteSet sprite) {
			this.sprite = sprite;
		}

		public Particle createParticle(SimpleParticleType particle, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			BrightshroomSporeParticle suspendedparticle = new BrightshroomSporeParticle(level, this.sprite, x, y, z, 0.0D, (double) -0.8F, 0.0D);
			return suspendedparticle;
		}
	}
}
