package com.unsubble.magicfishpool.handlers;

import com.unsubble.magicfishpool.MagicFishPool;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public interface ConfigHandler {

    static @NotNull FileConfiguration getMainConfig() {
        return MagicFishPool.getInstance().getConfig();
    }

    static ResourceBundle getLanguage() {
        String language = getMainConfig().getString("settings.language");
        Objects.requireNonNull(language, "Settings.language section cannot be null");
        return ResourceBundle.getBundle("lang", Locale.forLanguageTag(language));
    }

    @Contract(" -> new")
    static @NotNull FileConfiguration getItemConfig() {
        return YamlConfiguration.loadConfiguration(new File("items.yml"));
    }

    String getString(String path);

    ConfigurationSection getSection(String path);

    Set<String> getKeys();
}
