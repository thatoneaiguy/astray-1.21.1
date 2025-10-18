package com.everest.astray.init;

import com.everest.astray.Astray;
import com.everest.astray.entity.RiftEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AstrayEntities {
    public static final EntityType<RiftEntity> RIFT_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            Astray.id("rift"),
            EntityType.Builder.<RiftEntity>create(RiftEntity::new, SpawnGroup.CREATURE)
                    .dimensions(2F, 2F)
                    .makeFireImmune()
                    .build());

    public static void init() {}
}
