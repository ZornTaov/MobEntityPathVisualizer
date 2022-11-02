package org.zornco.pathvis;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.zornco.pathvis.particles.NodeParticle;
import org.zornco.pathvis.particles.NodeParticleType;

import java.util.OptionalDouble;

@Mod.EventBusSubscriber(modid = MobEntityPathVisualizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {
    // ================================================================================================================
    //    Registries
    // ================================================================================================================
    public static final DeferredRegister<ParticleType<?>> PARTICLE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MobEntityPathVisualizer.MOD_ID);

    // ================================================================================================================
    //    Particles
    // ================================================================================================================
    public static final RegistryObject<NodeParticleType> NODE_PARTICLE = PARTICLE.register("node", NodeParticleType::new);


    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        ParticleEngine manager = Minecraft.getInstance().particleEngine;
        manager.register(NODE_PARTICLE.get(), NodeParticle.FACTORY::new);

    }
    public static class LineTypes extends RenderType {
        public static final RenderType THICC_LINE = RenderType.create("thicc_line",
            DefaultVertexFormat.POSITION_COLOR_NORMAL,
            VertexFormat.Mode.LINES,
            256, false, false,
            RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_LINES_SHADER)
                .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(4.0)))
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(NO_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .setCullState(NO_CULL)
                .createCompositeState(false));
        public static final RenderType THICCCCC_LINES = RenderType.create("thiccccc_lines",
            DefaultVertexFormat.POSITION_COLOR_NORMAL,
            VertexFormat.Mode.LINE_STRIP,
            256, false, false,
            RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_LINES_SHADER)
                .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(8.0)))
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(NO_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(NO_DEPTH_TEST)
                .createCompositeState(false));

        public LineTypes(String s, VertexFormat vf, VertexFormat.Mode m, int i, boolean b1, boolean b2, Runnable r1, Runnable r2) {
            super(s, vf, m, i, b1, b2, r1, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }
    }
}
