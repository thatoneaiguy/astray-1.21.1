package com.everest.astray.client.shader;

import com.everest.astray.Astray;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ShaderActivators {
    public static Color getClientColor() {
        try {
            //return Color.decode(AffixConfigs.shaderAccentColor);
            return new Color(255, 255, 255);
        }catch (Exception e){
            return new Color(255, 255, 255);
        }
    }

    public static void blackHoleShaderActivator(Vec3d pos, float timer) {
        try {
            PostProcessingManager postProcessingManager = VeilRenderSystem.renderer().getPostProcessingManager();
            PostPipeline postPipeline = postProcessingManager.getPipeline(Identifier.of(Astray.MODID, "black_hole"));
            assert postPipeline != null;
            Color color = getClientColor();
            postPipeline.getUniformSafe("pos").setVector((float) pos.x, (float) pos.y, (float) pos.z);
            postPipeline.getUniformSafe("timer").setFloat(timer);
            postProcessingManager.runPipeline(postPipeline);
            postPipeline = postProcessingManager.getPipeline(Identifier.of(Astray.MODID, "fog"));
            assert postPipeline != null;
            postPipeline.getUniformSafe("pos").setVector((float) pos.x, (float) pos.y, (float) pos.z);
            postProcessingManager.runPipeline(postPipeline);
        } catch (Exception ignored) {}
    }
}
