package com.github.Pandarix.beautify.event;

import com.github.Pandarix.beautify.Beautify;
import com.github.Pandarix.beautify.particle.ParticleInit;
import com.github.Pandarix.beautify.particle.custom.GlowEssenceParticles;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;


@EventBusSubscriber(modid = Beautify.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    // minecraft is autoclosing
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.GLOWESSENCE_PARTICLES.get(),
                GlowEssenceParticles.Provider::new);
    }
}
