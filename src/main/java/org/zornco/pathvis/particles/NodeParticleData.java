package org.zornco.pathvis.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.zornco.pathvis.ClientRegistration;

import javax.annotation.Nonnull;
import java.util.Locale;

public record NodeParticleData(Float r, Float g, Float b, Float s) implements ParticleOptions {

    public static final Codec<NodeParticleData> CODEC = RecordCodecBuilder.create(val -> val.group(
        Codec.FLOAT.fieldOf("r").forGetter((data) -> data.r),
        Codec.FLOAT.fieldOf("g").forGetter((data) -> data.g),
        Codec.FLOAT.fieldOf("b").forGetter((data) -> data.b),
        Codec.FLOAT.fieldOf("s").forGetter((data) -> data.s)
    ).apply(val, NodeParticleData::new));

    public static final Deserializer<NodeParticleData> DESERIALIZER = new Deserializer<>() {
        @Override
        @Nonnull
        public NodeParticleData fromCommand(@Nonnull ParticleType<NodeParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float) reader.readDouble();
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            return new NodeParticleData(f, f1, f2, f3);
        }

        @Override
        @Nonnull
        public NodeParticleData fromNetwork(@Nonnull ParticleType<NodeParticleData> type, @Nonnull FriendlyByteBuf buf) {
            return new NodeParticleData(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
    };

    @Nonnull
    @Override
    public ParticleType<NodeParticleData> getType() {
        return ClientRegistration.NODE_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(@Nonnull FriendlyByteBuf buf) {
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(s);
    }

    @Override
    public Float r() {
        return r;
    }

    @Override
    public Float g() {
        return g;
    }

    @Override
    public Float b() {
        return b;
    }

    @Override
    public Float s() {
        return s;
    }

    @Nonnull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.5f %.5f %.5f %.5f", ClientRegistration.NODE_PARTICLE.getId(), r, g, b, s);
    }

}