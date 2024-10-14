package com.github.Pandarix.beautify.particle;

import com.github.Pandarix.beautify.Beautify;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ParticleInit {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
			.create(BuiltInRegistries.PARTICLE_TYPE, Beautify.MODID);

	public static final Supplier<SimpleParticleType> GLOWESSENCE_PARTICLES = PARTICLE_TYPES
			.register("glowessence_particles", () -> new SimpleParticleType(true));
}