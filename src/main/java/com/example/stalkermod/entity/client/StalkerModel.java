package com.example.stalkermod.entity.client;

import com.example.stalkermod.entity.StalkerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StalkerModel extends GeoModel<StalkerEntity> {
    @Override
    public ResourceLocation getModelResource(StalkerEntity object) {
        return new ResourceLocation("stalkermod", "geo/stalker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StalkerEntity object) {
        return new ResourceLocation("stalkermod", "textures/entity/stalker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StalkerEntity animatable) {
        return new ResourceLocation("stalkermod", "animations/stalker.animation.json");
    }
}
