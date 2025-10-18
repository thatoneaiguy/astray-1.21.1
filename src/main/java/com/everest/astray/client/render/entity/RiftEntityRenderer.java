package com.everest.astray.client.render.entity;

import com.everest.astray.client.AstrayClient;
import com.everest.astray.client.shader.ShaderActivators;
import com.everest.astray.entity.RiftEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RiftEntityRenderer extends MobEntityRenderer<RiftEntity, RiftEntityModel> {
    public static final Identifier RIFT_TEXTURE = Identifier.of("astray", "textures/entity/rift_entity.png");

    public RiftEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RiftEntityModel(context.getPart(AstrayClient.MODEL_TEST_LAYER)), 0.0f);
    }

    @Override
    public boolean shouldRender(RiftEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(RiftEntity entity) {
        return Identifier.of("");
    }

    @Override
    public void render(RiftEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        livingEntity.addTimer(MinecraftClient.getInstance().getRenderTickCounter().getLastDuration());
        ShaderActivators.blackHoleShaderActivator(livingEntity.getPos(), livingEntity.getTimer());

        ItemStack stack = new ItemStack(Blocks.PACKED_ICE);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, null, null, 0);

        matrixStack.push();

        matrixStack.translate(0.0, 4.0, 0.0);

        float xSpeed = 2f;
        float ySpeed = 2f;
        float zSpeed = 2f;

        float timer = livingEntity.getTimer();

        Quaternionf rotation = new Quaternionf()
                .rotateX((float)Math.toRadians(timer * xSpeed))
                .rotateY((float)Math.toRadians(timer * ySpeed))
                .rotateZ((float)Math.toRadians(timer * zSpeed));

        matrixStack.multiply(rotation);

        matrixStack.scale(6f, 6f, 6f);

        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, true, matrixStack, vertexConsumerProvider, light, 0, model);

        matrixStack.pop();
    }

}
