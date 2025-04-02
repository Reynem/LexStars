package com.reynem.entities;

import com.reynem.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class EggPamEntity extends ThrownItemEntity {
    public EggPamEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public EggPamEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    public EggPamEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RICO_EGG;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult){
        super.onBlockHit(blockHitResult);

        if (!this.getWorld().isClient()){
            BlockState hiState = this.getWorld().getBlockState(blockHitResult.getBlockPos());

            this.getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

}
