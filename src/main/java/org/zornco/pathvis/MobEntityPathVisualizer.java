package org.zornco.pathvis;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zornco.pathvis.item.PathingVisualizerItem;

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
        public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
            LivingEntity le = event.getEntityLiving();
            if (le instanceof MobEntity) { // only MobEntity has a Navigator, so this includes most entities
                PathNavigator navi = ((MobEntity) le).getNavigation();
                if (le.level instanceof ServerWorld && le.level.getGameTime() % 10 == 0) { // only generate every 0.5 seconds, to try and cut back on packet spam
                    List<ServerPlayerEntity> players = ((ServerWorld) le.level).getPlayers((player) ->
                        player.getItemBySlot(EquipmentSlotType.MAINHAND).getItem() instanceof PathingVisualizerItem ||
                            player.getItemBySlot(EquipmentSlotType.OFFHAND).getItem() instanceof PathingVisualizerItem);
                    for (ServerPlayerEntity player: players) {
                        Path path = navi.getPath();
                        if (path != null) {
                            for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
                                //get current point
                                BlockPos pos = path.getNode(i).asBlockPos();
                                //get next point (or current point)
                                BlockPos nextPos = (i+1) != path.getNodeCount() ? path.getNode(i+1).asBlockPos() : pos;
                                //get difference for vector
                                BlockPos endPos = nextPos.subtract(pos);
                                //render pathpoints
                                ((ServerWorld) le.level).sendParticles(player, ParticleTypes.HAPPY_VILLAGER,false,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                    0, 0, 0, 0);
                                //send a particle between points for direction
                                ((ServerWorld) le.level).sendParticles(player, ParticleTypes.END_ROD,false,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                    endPos.getX(), endPos.getY(), endPos.getZ(), 0.1);
                            }
                            // render end point
                            BlockPos pos = navi.getTargetPos();
                            ((ServerWorld) le.level).sendParticles(player, ParticleTypes.HEART,false,
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0,
                                0, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }
}
