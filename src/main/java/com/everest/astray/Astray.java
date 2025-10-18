package com.everest.astray;

import com.everest.astray.entity.RiftEntity;
import com.everest.astray.init.AstrayEntities;
import de.keksuccino.melody.Melody;
import de.keksuccino.melody.resources.audio.SimpleAudioFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;

public class Astray implements ModInitializer {
    public static final String MODID = "astray";

    @Override
    public void onInitialize() {
        AstrayEntities.init();
        FabricDefaultAttributeRegistry.register(AstrayEntities.RIFT_ENTITY_TYPE, RiftEntity.setAttributes());
    }

    public static Identifier id(String s) {
        return Identifier.of(MODID, s);
    }
}
