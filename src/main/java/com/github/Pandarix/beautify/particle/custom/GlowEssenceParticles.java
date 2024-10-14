package com.github.Pandarix.beautify.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;


public class GlowEssenceParticles extends TextureSheetParticle {

	private static final float size = 0.07f;

	protected GlowEssenceParticles(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet,
			double xd, double yd, double zd) {
		super(level, xCoord, yCoord, zCoord, xd, yd, zd);

		this.friction = 0.8F;
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		this.quadSize = 0;
		this.lifetime = (int) (60 * (0.5 + Math.random() / 2));
		this.setSpriteFromAge(spriteSet);

		this.rCol = 1f;
		this.gCol = 1f;
		this.bCol = 1f;
	}

	private void fadeOut() {
		float fadeValue = (float) Math.sin(Math.PI * ((float) this.age / this.lifetime));

		this.alpha = 1 * fadeValue;
		this.quadSize = size * fadeValue;
	}

	private void move() {
		if(Math.random()<=0.05) {
		this.xd = (Math.random()*2-1)/70;
		}
		if(Math.random()<=0.05) {
		this.yd = (Math.random()*2-1)/70;
		}
		if(Math.random()<=0.05) {
		this.zd = (Math.random()*2-1)/70;
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@Override
	public void tick() {
		super.tick();
		this.fadeOut();
		this.move();
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z,
				double dx, double dy, double dz) {
			return new GlowEssenceParticles(level, x, y, z, this.sprites, dx, dy, dz);
		}
	}

}
