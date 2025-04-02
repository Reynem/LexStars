package com.reynem.entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;



public class ModEntities {
    public static final EntityType<EggRicoEntity> EGG_RICO_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("lexstars", "egg_rico"),
            EntityType.Builder.<EggRicoEntity>create(EggRicoEntity::new, SpawnGroup.MISC)
                    .dimensions(0.75F, 0.75F)
                    .build("egg_rico"));

    public static final EntityType<EggPamEntity> EGG_PAM_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("lexstars", "egg_pam"),
            EntityType.Builder.<EggPamEntity>create(EggPamEntity::new, SpawnGroup.MISC)
                    .dimensions(0.75F, 0.75F)
                    .build("egg_pam"));

    public static void registerModEntities(){

    }
}
