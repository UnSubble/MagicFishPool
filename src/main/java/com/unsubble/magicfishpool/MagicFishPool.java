package com.unsubble.magicfishpool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

public final class MagicFishPool extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            createFiles();
        } catch (IOException ignored) {
            Bukkit.getLogger().log(Level.WARNING, "Plugins directory does not found.");
        }
    }

    private void createFiles() throws IOException {
        Path directories = Paths.get(this.getDataFolder().getAbsolutePath());
        if (Files.notExists(directories)) {
            Files.createDirectories(directories);
        }

        Path mainConfigPath = directories.resolve("config.yml");
        if (Files.notExists(mainConfigPath)) {
            this.saveDefaultConfig();
        }

        Path itemsConfigPath = directories.resolve("items.yml");
        if (Files.notExists(itemsConfigPath)) {
            try (InputStream in = this.getResource("items.yml")) {
                Objects.requireNonNull(in);
                YamlConfiguration.loadConfiguration(new InputStreamReader(in)).save(itemsConfigPath.toFile());
            }
        }

        Path langTr = directories.resolve("lang_tr.properties");
        if (Files.notExists(langTr)) {
            try (InputStream in = this.getResource("lang_tr.properties")) {
                Objects.requireNonNull(in);
                Files.copy(in, langTr);
            }
        }

        Path langEn = directories.resolve("lang_en.properties");
        if (Files.notExists(langEn)) {
            try (InputStream in = this.getResource("lang_en.properties")) {
                Objects.requireNonNull(in);
                Files.copy(in, langEn);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Contract("_ -> new")
    public static @NotNull String formatStringColor(@NotNull String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
