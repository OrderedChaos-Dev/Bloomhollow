package bloomhollow.core.registry;

import bloomhollow.core.Bloomhollow;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<SimpleParticleType> BRIGHTSHROOM_SPORE = PARTICLES.register("brightshroom_spore", () -> new SimpleParticleType(true));
}
