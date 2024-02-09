package com.unsubble.magicfishpool.handlers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class ItemConfigHandler implements ConfigHandler {
    private final FileConfiguration itemConfig;

    public ItemConfigHandler() {
        this.itemConfig = ConfigHandler.getItemConfig();
    }

    @Override
    public String getString(String path) {
        return itemConfig.getString(path);
    }

    @Override
    public ConfigurationSection getSection(String path) {
        return itemConfig.getConfigurationSection(path);
    }

    @Override
    public Set<String> getKeys() {
        return itemConfig.getKeys(false);
    }
}
