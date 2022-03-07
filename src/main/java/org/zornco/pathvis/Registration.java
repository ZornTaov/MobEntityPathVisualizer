package org.zornco.pathvis;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.zornco.pathvis.item.PathingVisualizerItem;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = MobEntityPathVisualizer.MOD_ID)
public class Registration {
    // ================================================================================================================
    //    Registries
    // ================================================================================================================
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MobEntityPathVisualizer.MOD_ID);

    // ================================================================================================================
    //    ITEMS
    // ================================================================================================================
    public static final RegistryObject<PathingVisualizerItem> PATHING_VISUALIZER_ITEM = ITEMS.register("pathing_visualizer", PathingVisualizerItem::new);
    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
    public static final ItemGroup ITEM_GROUP = new ItemGroup (MobEntityPathVisualizer.MOD_ID) {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.PATHING_VISUALIZER_ITEM.get());
        }
    };
}
