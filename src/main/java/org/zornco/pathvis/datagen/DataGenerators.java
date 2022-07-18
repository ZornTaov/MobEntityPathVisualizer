package org.zornco.pathvis.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zornco.pathvis.MobEntityPathVisualizer;

@Mod.EventBusSubscriber(modid = MobEntityPathVisualizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new RecipeGenerator(generator));

        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ItemStateGenerator(generator, helper));
    }
}
