package com.reynem.weapons;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RicoGun extends Item {
    public RicoGun(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        //потом будут кастомные патроны
        boolean hasAmmo = !user.getAbilities().creativeMode && user.getInventory().count(Items.ARROW) > 0;

        if (hasAmmo || user.getAbilities().creativeMode){
            if (!world.isClient){
                if (!user.getAbilities().creativeMode){
                    ItemStack ammoStack = user.getInventory().getStack(user.getInventory().getSlotWithStack(new ItemStack(Items.ARROW)));
                    if (!ammoStack.isEmpty()){
                        ammoStack.decrement(1);
                        if(ammoStack.isEmpty()){
                            user.getInventory().removeStack(user.getInventory().getSlotWithStack(new ItemStack(Items.ARROW)));
                        }
                    } else{
                        return TypedActionResult.fail(itemStack);
                    }
                }

                ArrowEntity arrowEntity = new ArrowEntity(EntityType.ARROW, world);
                arrowEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3.0F, 1.0F);
                arrowEntity.setDamage(10);
                world.spawnEntity(arrowEntity);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + 0.5f);
                user.getItemCooldownManager().set(this, 10);
            }

            return TypedActionResult.success(itemStack);
        } else{
            return TypedActionResult.fail(itemStack);
        }

    }
}
