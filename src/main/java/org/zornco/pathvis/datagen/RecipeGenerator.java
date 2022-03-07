package org.zornco.pathvis.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.zornco.pathvis.Registration;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator gen) {
        super(gen);
    }


    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
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
