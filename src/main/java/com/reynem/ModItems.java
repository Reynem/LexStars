package com.reynem;

import com.reynem.weapons.RicoGun;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item PAM_EGG = register(
            "egg_pam",
            EggPamItem::new,
            new Item.Settings()
    );

    public static final Item RICO_EGG = register(
            "egg_rico",
            EggRicoItem::new,
            new Item.Settings()
    );

    public static final Item RICO_GUN = register(
            "rico_gun",
            RicoGun::new,
            new Item.Settings()
    );


    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LexStars.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings);

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.PAM_EGG);
                    itemGroup.add(ModItems.RICO_EGG);
                });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.RICO_GUN));
    }

}