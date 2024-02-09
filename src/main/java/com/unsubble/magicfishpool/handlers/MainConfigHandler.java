package com.unsubble.magicfishpool.handlers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class MainConfigHandler implements ConfigHandler {
    private final FileConfiguration mainConfig;

    public MainConfigHandler() {
        this.mainConfig = ConfigHandler.getMainConfig();
    }

    @Override
    public String getString(String path) {
        return mainConfig.getString(path);
    }

    @Override
    public ConfigurationSection getSection(String path) {
        return mainConfig.getConfigurationSection(path);
    }

    @Override
    public Set<String> getKeys() {
        return mainConfig.getKeys(false);
    }
}