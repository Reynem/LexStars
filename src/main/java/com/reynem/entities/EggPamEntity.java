package com.reynem.entities;

import com.reynem.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class EggPamEntity extends ThrownItemEntity {
    static final Random RANDOM = new Random();
    private final List<Consumer<EggPamEntity>> possibleActions = new ArrayList<>();
    private LightningEntity lightningBolt;
    private ZombieEntity zombieArmored;
    private BlockPos hitPos;

    public EggPamEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        generateActions();
    }

    public EggPamEntity(World world, LivingEntity owner) {
        super(ModEntities.EGG_PAM_ENTITY, owner, world);
        generateActions();
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PAM_EGG;
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

        if (!this.getWorld().isClient()) {
            if (!possibleActions.isEmpty()) {
                int randomIndex = RANDOM.nextInt(possibleActions.size());
                Consumer<EggPamEntity> chosenAction = possibleActions.get(randomIndex);
                chosenAction.accept(this);
            } else {
                this.getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.STONE.getDefaultState());
                this.discard();
            }
        }
    }
    // world в лямбде - это яйцо, просто в документации было названо по другому
    private void generateActions(){
        possibleActions.add(world -> {
            world.getWorld().setBlockState(this.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
            world.getWorld().sendEntityStatus(this, (byte) 3);
            this.discard();
        });
        possibleActions.add(world -> {
            world.getWorld().setBlockState(this.getBlockPos(), Blocks.DIRT.getDefaultState());
            world.getWorld().sendEntityStatus(this, (byte) 3);
            this.discard();
        });
        possibleActions.add(world -> {
            lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world.getWorld());
            lightningBolt.setPos(world.getX(), world.getY(), world.getZ());
            world.getWorld().spawnEntity(lightningBolt);
            this.discard();
        });
        possibleActions.add(world -> {
            hitPos = world.getBlockPos();
            zombieArmored = new ZombieEntity(EntityType.ZOMBIE, world.getWorld());
            zombieArmored.setPos(hitPos.getX(), hitPos.getY() + 2, hitPos.getZ());

            ItemStack helmet = new ItemStack(Items.NETHERITE_HELMET);
            ItemStack chestplate = new ItemStack(Items.NETHERITE_CHESTPLATE);
            ItemStack leggings = new ItemStack(Items.NETHERITE_LEGGINGS);
            ItemStack boots = new ItemStack(Items.NETHERITE_BOOTS);

            zombieArmored.equipStack(EquipmentSlot.HEAD, helmet);
            zombieArmored.equipStack(EquipmentSlot.CHEST, chestplate);
            zombieArmored.equipStack(EquipmentSlot.LEGS, leggings);
            zombieArmored.equipStack(EquipmentSlot.FEET, boots);

            world.getWorld().spawnEntity(zombieArmored);
            this.discard();
        });
        possibleActions.add(world -> {
            hitPos = world.getBlockPos();
            if (!world.getWorld().isClient()) {
                BlockState woolState = Blocks.WHITE_WOOL.getDefaultState();

                world.getWorld().setBlockState(hitPos.add(-1, 0, 0), woolState); // Левая часть верхней перекладины
                world.getWorld().setBlockState(hitPos.add(0, 0, 0), woolState);  // Центральная часть верхней перекладины
                world.getWorld().setBlockState(hitPos.add(1, 0, 0), woolState);  // Правая часть верхней перекладины
                world.getWorld().setBlockState(hitPos.add(0, 1, 0), woolState);  // Вертикальная ножка
                world.getWorld().setBlockState(hitPos.add(0, 2, 0), woolState);  // Продолжение вертикальной ножки

                world.discard();
            }
        });
    }

}
