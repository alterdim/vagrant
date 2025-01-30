package alternis.vagrant.registry;

import alternis.vagrant.Vagrant;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static alternis.vagrant.Vagrant.CUSTOM_ITEM_GROUP_KEY;

public class ModItems {


    public static void initialize()
    {

    }

    public static final Item ZIRCON = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings()),
            "zircon"
    );

    public static final Item SCULK_BLOOD = register(
            new Item(new FabricItemSettings()),
            "sculk_blood"
    );

    public static final Item BOOBA = register(
            new MusicDiscItem(1, ModSounds.BOOBA, new FabricItemSettings().maxCount(1), 238),
            "booba_disk"
    );


    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier(Vagrant.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(item);
        });
        return registeredItem;
    }
}