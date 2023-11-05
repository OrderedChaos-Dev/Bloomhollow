package bloomhollow.client;

import bloomhollow.client.particle.BrightshroomSporeParticle;
import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.BloomhollowParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Bloomhollow.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class BloomhollowClient {

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
    	Minecraft.getInstance().particleEngine.register(BloomhollowParticles.BRIGHTSHROOM_SPORE.get(), BrightshroomSporeParticle.Provider::new);
    }
}
