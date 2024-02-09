package com.unsubble.magicfishpool.managers;

import com.unsubble.magicfishpool.MagicFishPool;
import com.unsubble.magicfishpool.handlers.MainConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public final class LocationManager {
    private static final MainConfigHandler mainConfigHandler = MagicFishPool.getInstance().getMainConfigHandler();
    private static final Map<World, List<Location>> locationsByWorlds = new HashMap<>();
    private static Iterator<String> nextWorld;
    private static Iterator<String> nextLocation;

    private LocationManager() {
    }

    public static @Nullable Location createNextLocation() {
        if (nextWorld == null)
            reset();

        if (!nextWorld.hasNext())
            return null;

        String world = null;

        if (nextLocation == null || !nextLocation.hasNext()) {
            world = nextWorld.next();
            nextLocation = Arrays.stream(mainConfigHandler
                    .getString("settings.locations." + world).split(",")).iterator();
        }

        String[] coordinates = nextLocation.next().split(",");
        return new Location(Bukkit.getWorld(Objects.requireNonNull(world)), Double.parseDouble(coordinates[0]),
                Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[2]));
    }

    public static void initializeAllLocations() {
        reset();
        Location location;
        while ((location = createNextLocation()) != null) {
            List<Location> locationList = locationsByWorlds.computeIfAbsent(location.getWorld(), x -> new ArrayList<>());
            locationList.add(location);
        }
    }

    private static void reset() {
        ConfigurationSection locationsSection = mainConfigHandler.getSection("settings.locations");
        nextWorld = locationsSection.getKeys(false).iterator();
    }

    public static Map<World, List<Location>> getLocationsByWorlds() {
        return locationsByWorlds;
    }
}
