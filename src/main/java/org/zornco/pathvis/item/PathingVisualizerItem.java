package org.zornco.pathvis.item;

import net.minecraft.world.item.Item;
import org.zornco.pathvis.Registration;

public class PathingVisualizerItem extends Item {
    public PathingVisualizerItem() {
        super(new Item.Properties()
            .stacksTo(1)
            .tab(Registration.ITEM_GROUP));
    }
}
