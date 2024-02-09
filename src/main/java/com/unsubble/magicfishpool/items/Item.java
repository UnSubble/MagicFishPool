package com.unsubble.magicfishpool.items;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Item extends ItemStack {
    private final String name;
    private final double chance;
    private final Rarity rarity;

    public Item(@NotNull ItemStack itemStack, String name, double chance, Rarity rarity)  {
        super(itemStack.getType(), itemStack.getAmount());
        this.name = name;
        this.chance = chance;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public double getChance() {
        return chance;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Double.compare(chance, item.chance) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, chance);
    }
}
