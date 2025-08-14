package com.reynem.entities

import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.item.Items
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.World

class SgushMob(entityType: EntityType<out SgushMob>, world: World) : PathAwareEntity(entityType, world) {

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, MeleeAttackGoal(this, 1.2, true))
        goalSelector.add(2, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(3, LookAtEntityGoal(this, LivingEntity::class.java, 8.0f))
        goalSelector.add(4, LookAroundGoal(this))

        targetSelector.add(0, RevengeGoal(this))
        targetSelector.add(1, ActiveTargetGoal(this, LivingEntity::class.java, true))
    }

    override fun initEquipment(random: net.minecraft.util.math.random.Random, difficulty: LocalDifficulty) {
        super.initEquipment(random, difficulty)
        equipStack(EquipmentSlot.MAINHAND, Items.NETHERITE_SWORD.defaultStack)
    }

    companion object {
        fun createMobAttributes(): DefaultAttributeContainer.Builder {
            return createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
        }
    }
}
