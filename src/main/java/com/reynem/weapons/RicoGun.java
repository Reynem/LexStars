package com.reynem.weapons;

import com.reynem.ModItems;
import com.reynem.entities.EggRicoEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        ItemStack stack = user.getStackInHand(hand);
        boolean hasAmmo = user.getAbilities().creativeMode || user.getInventory().count(ModItems.RICO_EGG) > 0;

        if (!hasAmmo) return TypedActionResult.fail(stack);

        if (!world.isClient) {
            if (!user.getAbilities().creativeMode) {
                ItemStack ammo = user.getInventory().getStack(
                        user.getInventory().getSlotWithStack(new ItemStack(ModItems.RICO_EGG))
                );
                if (!ammo.isEmpty()) {
                    ammo.decrement(1);
                    if (ammo.isEmpty()) {
                        user.getInventory().removeStack(
                                user.getInventory().getSlotWithStack(new ItemStack(ModItems.RICO_EGG))
                        );
                    }
                } else {
                    return TypedActionResult.fail(stack);
                }
            }

            EggRicoEntity arrow = new EggRicoEntity(
                    world, user
            );
            arrow.setOwner(user);
            arrow.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 4.0F, 0.0F);

            world.spawnEntity(arrow);
            world.playSound(
                    null,
                    user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + 0.5f
            );
            user.getItemCooldownManager().set(this, 10);
        }

        return TypedActionResult.success(stack);
    }

}
