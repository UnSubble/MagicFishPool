package com.unsubble.magicfishpool.listeners;

import com.unsubble.magicfishpool.MagicFishPool;
import com.unsubble.magicfishpool.managers.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

public final class CatchingAFishListener implements Listener {

    @EventHandler
    public void onCatch(@NotNull PlayerFishEvent event) {
        if (event.getCaught() == null)
            return;
        if (LocationManager.getLocationSet().contains(event.getCaught().getLocation()))
            Bukkit.getScheduler().runTaskAsynchronously(MagicFishPool.getInstance(), new PlayerFishEventHandler(event.getCaught(), event.getPlayer()));
    }
}

class PlayerFishEventHandler implements Runnable {
    private final Entity item;
    private final Player player;

    public PlayerFishEventHandler(Entity item, Player player) {
        this.item = item;
        this.player = player;
    }

    @Override
    public void run() {
        if (item.getType().equals(EntityType.DROPPED_ITEM)) {
            item.remove();
            giveItem();
        }
    }

    private void giveItem() {
        MagicFishPool.getInstance().getRandom().nextDouble();
    }
}