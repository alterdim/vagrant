package alternis.vagrant.registry;

import alternis.vagrant.Vagrant;
import alternis.vagrant.entity.RiftEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static alternis.vagrant.Vagrant.CUSTOM_ITEM_GROUP_KEY;
import static alternis.vagrant.Vagrant.MOD_ID;

public class ModEntities {

    public static void initialize()
    {

    }

    public static final EntityType<RiftEntity> RIFT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "rift"),
            EntityType.Builder.create(RiftEntity::new, SpawnGroup.MISC).setDimensions(0.75f, 0.75f).build("rift")
    );

}
