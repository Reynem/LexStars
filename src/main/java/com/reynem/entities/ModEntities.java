package com.reynem.entities;


import com.reynem.EggRicoItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.Item;
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

    public static void registerModEntities(){

    }
}
