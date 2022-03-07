package org.zornco.pathvis.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import org.zornco.pathvis.Registration;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        // ================================================================================================================
        //    ITEMS
        // ================================================================================================================
        ShapedRecipeBuilder.shaped(Registration.PATHING_VISUALIZER_ITEM.get(), 1)
            .pattern(" P ")
            .pattern("NGN")
            .pattern(" N ")
            .define('P', Tags.Items.ENDER_PEARLS)
            .define('G', Tags.Items.GLASS_LIGHT_BLUE)
            .define('N', Tags.Items.NUGGETS_IRON)
            .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
            .save(consumer);
    }
}
