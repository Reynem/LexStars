package com.reynem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PowerBox extends Block {
    public PowerBox(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!world.isClient()) {
            StatusEffectInstance strengthEffect = new StatusEffectInstance(
                    StatusEffects.STRENGTH,
                    1200,
                    1,
                    false,
                    true,
                    true
            );

            player.addStatusEffect(strengthEffect);

            player.sendMessage(Text.literal("Вы чувствуете прилив сил!").formatted(Formatting.GREEN), false);
        }
    }
}