package com.example.stalkermod.entity.client;

import com.example.stalkermod.entity.StalkerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StalkerRenderer extends GeoEntityRenderer<StalkerEntity> {
    public StalkerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new StalkerModel());
    }

    @Override
    public void render(StalkerEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public Color getRenderColor(StalkerEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight) {
        if (animatable.fadeOutTicks > 0) {
            float alpha = (float) animatable.fadeOutTicks / 40.0f; // Fade out en 2 segundos
            return new Color(1.0f, 1.0f, 1.0f, Math.max(0.0f, alpha));
        }
        return super.getRenderColor(animatable, partialTick, poseStack, bufferSource, buffer, packedLight);
    }
}
