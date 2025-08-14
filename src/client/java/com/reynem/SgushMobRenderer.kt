package com.reynem

import com.reynem.entities.SgushMob
import net.minecraft.client.render.entity.BipedEntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.util.Identifier


class SgushMobRenderer(ctx: EntityRendererFactory.Context) :
    BipedEntityRenderer<SgushMob, PlayerEntityModel<SgushMob>>(
        ctx,
        PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER), false),
        0.5f
    ) {

    override fun getTexture(entity: SgushMob): Identifier {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = Identifier.of("lexstars", "textures/entity/sgush_skin.png")
    }
}