package org.zornco.pathvis;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zornco.pathvis.item.PathingVisualizerItem;
import org.zornco.pathvis.particles.NodeParticleData;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pathvis")
public class MobEntityPathVisualizer
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "pathvis";

    public MobEntityPathVisualizer() {
        Registration.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(modid = MobEntityPathVisualizer.MOD_ID)
    public static class ServerEvents {

        @SubscribeEvent
        public static void onLivingUpdateEvent(LivingEvent.LivingTickEvent event) {
            LivingEntity le = event.getEntity();
            if (le instanceof Mob mob) { // only MobEntity has a Navigator, so this includes most entities
                PathNavigation navi = mob.getNavigation();

                if (le.level instanceof ServerLevel level && level.getGameTime() % 10 == 0) { // only generate every 0.5 seconds, to try and cut back on packet spam
                    List<ServerPlayer> players = level.getPlayers((player) ->
                        player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof PathingVisualizerItem ||
                            player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof PathingVisualizerItem);
                    for (ServerPlayer player: players) {
                        Path path = navi.getPath();
                        if (path != null) {
                            for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
                                //get current point
                                BlockPos pos = path.getNode(i).asBlockPos();
                                //get next point (or current point)
                                BlockPos nextPos = (i+1) != path.getNodeCount() ? path.getNode(i+1).asBlockPos() : navi.getTargetPos() != null ? navi.getTargetPos() : pos;
                                //get difference for vector
                                BlockPos endPos = nextPos.subtract(pos);
                                //render pathpoints
                                NodeParticleData data = new NodeParticleData(.2f, .5f, 1f, 1.5f);
                                level.sendParticles(player, data,false,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1);
                                //send a particle between points for direction
                                NodeParticleData dataw = new NodeParticleData(1f, 1f, 1f, 1f);
                                level.sendParticles(player, dataw,false,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                    nextPos.getX() + 0.5, nextPos.getY() + 0.5, nextPos.getZ() + 0.5, 1);
                            }
                            // render end point
                            BlockPos pos = navi.getTargetPos();
                            if (pos != null) {
                                NodeParticleData data = new NodeParticleData(1f, .2f, .2f, 2f);
                                level.sendParticles(player, data,false,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
