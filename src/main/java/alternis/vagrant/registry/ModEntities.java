package alternis.vagrant.registry;

import alternis.vagrant.entity.Abomination;
import alternis.vagrant.entity.RiftEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static alternis.vagrant.Vagrant.MOD_ID;

public class ModEntities {

    public static void initialize()
    {
        FabricDefaultAttributeRegistry.register(ModEntities.ABOMINATION, Abomination.createMobAttributes());

    }

    public static final EntityType<RiftEntity> RIFT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "rift"),
            EntityType.Builder.create(RiftEntity::new, SpawnGroup.MISC).setDimensions(0.75f, 0.75f).build("rift")
    );

    public static final EntityType<Abomination> ABOMINATION = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "abomination"),
            EntityType.Builder.create(Abomination::new, SpawnGroup.MONSTER)
                    .setDimensions(1f, 1.5f)
                    .build("abomination")
    );




}
