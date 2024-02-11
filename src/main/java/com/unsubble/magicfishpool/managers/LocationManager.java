package com.unsubble.magicfishpool.managers;

import com.unsubble.magicfishpool.MagicFishPool;
import com.unsubble.magicfishpool.handlers.MainConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class LocationManager {
    private static final MainConfigHandler mainConfigHandler = MagicFishPool.getInstance().getMainConfigHandler();
    private static final Set<Location> locationSet = new HashSet<>();
    private static Iterator<String> nextWorld;
    private static Iterator<String> nextLocation;
    private static final Location dummyLocation = new Location(null, 0, 0, 0);

    private LocationManager() {
    }

    private static @Nullable Location getNextLocation() {
        if (nextWorld == null)
            resetIterator();

        String world = null;

        if (nextLocation == null || !nextLocation.hasNext()) {
            if (!nextWorld.hasNext())
                return null;
            world = nextWorld.next();
            nextLocation = Arrays.stream(mainConfigHandler.getString("settings.locations." + world)
                    .split(",")).iterator();
        }
        String[] coordinates = nextLocation.next().split(",\\w+=");
        return new Location(Bukkit.getWorld(Objects.requireNonNull(world)), Double.parseDouble(coordinates[1]),
                Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
    }

    public static void initializeAllLocations() {
        resetIterator();
        Location location;
        while ((location = getNextLocation()) != null) {
            if (!dummyLocation.equals(location))
                locationSet.add(location);
        }
    }

    private static void resetIterator() {
        ConfigurationSection locationsSection = getLocationsSection();
        if (locationsSection == null)
            createDummyLocation();
        locationsSection = mainConfigHandler.getSection("settings.locations");
        nextWorld = locationsSection.getKeys(false).iterator();
    }

    private static void createDummyLocation() {
        locationSet.add(dummyLocation);
        saveAllLocations();
    }

    public static void saveAllLocations() {
        ConfigurationSection settingsSection = getSettingsSection();
        settingsSection.set("locations", null);
    }

    public static void addNewLocation(@NotNull Location location) {
        ConfigurationSection locationsSection = getLocationsSection();
        if (locationsSection.get(Objects.requireNonNull(location.getWorld()).getName()) == null)
            locationsSection.set(location.getWorld().getName(), location);
        else
            locationsSection.addDefault(location.getWorld().getName(), location);
    }

    private static ConfigurationSection getSettingsSection() {
        return mainConfigHandler.getSection("settings");
    }

    private static ConfigurationSection getLocationsSection() {
        return getSettingsSection().getConfigurationSection("languages");
    }

    public static Set<Location> getLocationSet() {
        return locationSet;
    }
}
