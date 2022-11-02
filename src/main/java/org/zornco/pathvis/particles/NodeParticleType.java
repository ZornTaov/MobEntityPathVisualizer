package org.zornco.pathvis.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

import javax.annotation.Nonnull;

public class NodeParticleType extends ParticleType<NodeParticleData> {
    public NodeParticleType() {
        super(false, NodeParticleData.DESERIALIZER);
    }

    @Nonnull
    @Override
    public Codec<NodeParticleData> codec() {
        return NodeParticleData.CODEC;
    }
}
