package org.zornco.pathvis.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.zornco.pathvis.MobEntityPathVisualizer;
import org.zornco.pathvis.Registration;

import java.util.Objects;

public class ItemStateGenerator extends ItemModelProvider {

    public ItemStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MobEntityPathVisualizer.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(Objects.requireNonNull(Registration.PATHING_VISUALIZER_ITEM.getId()).getPath(),
            new ResourceLocation("item/handheld"),
            "layer0", new ResourceLocation(MobEntityPathVisualizer.MOD_ID, "item/pathing_visualizer"));
    }
}
