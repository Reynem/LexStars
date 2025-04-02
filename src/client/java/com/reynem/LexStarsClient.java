package com.reynem;

import net.fabricmc.api.ClientModInitializer;
import com.reynem.entities.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LexStarsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.EGG_RICO_ENTITY, FlyingItemEntityRenderer::new);
	}
}