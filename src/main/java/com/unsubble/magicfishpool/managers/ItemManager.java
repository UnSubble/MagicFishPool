package com.unsubble.magicfishpool.managers;

import com.unsubble.magicfishpool.MagicFishPool;
import com.unsubble.magicfishpool.handlers.ConfigHandler;
import com.unsubble.magicfishpool.items.Item;
import com.unsubble.magicfishpool.items.Rarity;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ItemManager {
    private static final ConfigHandler configHandler = MagicFishPool.getInstance().getItemConfigHandler();
    private static Iterator<String> nextItem;
    private static final Map<Rarity, List<Item>> itemRarityMap = new EnumMap<>(Rarity.class);

    private ItemManager() {
    }

    private static @Nullable Item getNextItem() {
        if (nextItem == null)
            resetIterator();

        Objects.requireNonNull(nextItem);

        if (!nextItem.hasNext())
            return null;

        ConfigurationSection itemSection = configHandler.getSection(nextItem.next());

        if (itemSection == null)
            return null;

        String raritySection = Objects.requireNonNull(itemSection.getString("rarity")).toUpperCase();
        Rarity rarity = toRarity(raritySection);

        String material = Objects.requireNonNull(itemSection.getString("type")).toUpperCase();

        String rawName = Objects.requireNonNull(itemSection.getString("name"));
        String coloredName = MagicFishPool.formatStringColor(rawName);

        double chance = itemSection.getDouble("chance");

        List<String> lore = Objects.requireNonNull(itemSection.getStringList("lore"));

        ItemStack itemStack = toItemStack(material, rarity, lore, coloredName);

        return new Item(itemStack, coloredName, chance, rarity);
    }

    public static void initializeAllItems() {
        resetIterator();
        Item item;
        while ((item = getNextItem()) != null) {
            List<Item> itemListByRarity = itemRarityMap.computeIfAbsent(item.getRarity(), x -> new ArrayList<>());
            itemListByRarity.add(item);
        }
    }

    private static @NotNull ItemStack toItemStack(@NotNull String materialStr, @NotNull Rarity rarity, @NotNull List<String> lore, @NotNull String name) {
        ItemStack itemStack = new ItemStack(toMaterial(materialStr));
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta)
                .addEnchant(Enchantment.LUCK, rarity.getEnchantmentLevel(), true);
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static Material toMaterial(@NotNull String str) {
        return Material.valueOf(str);
    }

    private static Rarity toRarity(@NotNull String str) {
        return Rarity.valueOf(str);
    }

    public static void resetIterator() {
        nextItem = configHandler.getKeys().iterator();
    }

    public static Map<Rarity, List<Item>> getItemRarityMap() {
        return itemRarityMap;
    }
}
