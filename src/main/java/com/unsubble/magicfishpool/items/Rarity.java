package com.unsubble.magicfishpool.items;

import com.unsubble.magicfishpool.MagicFishPool;
import com.unsubble.magicfishpool.handlers.ConfigHandler;
import org.jetbrains.annotations.NotNull;

public enum Rarity {
    COMMON(1),
    NORMAL(2),
    RARE(3),
    MYSTIC(4),
    LEGENDARY(5),
    UNIQUE(6);

    private final int enchantmentLevel;

    Rarity(int enchantmentLevel) {
        this.enchantmentLevel = enchantmentLevel;
    }

    public int getEnchantmentLevel() {
        return enchantmentLevel;
    }

    public @NotNull String getPrefix() {
        if (ConfigHandler.getMainConfig().getBoolean("settings.enablePrefix"))
            return "";
        String rawPrefix = ConfigHandler.getLanguage().getString("Rarity_" + toString() + "_prefix");
        return MagicFishPool.formatStringColor(rawPrefix);
    }

    @Override
    public @NotNull String toString() {
        return super.toString().toLowerCase();
    }
}
