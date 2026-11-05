package dev.example.clickcontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("clickcontrol.json");
    public static Set<String> blacklist = new HashSet<>();

    public static boolean isBlocked(Item item) {
        Identifier id = Registries.ITEM.getId(item);
        return blacklist.contains(id.toString());
    }

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                Data d = GSON.fromJson(Files.readString(FILE), Data.class);
                if (d != null) {
                    ClickControlClient.enabled = d.enabled;
                    ClickControlClient.useDelayTicks = Math.max(1, Math.min(20, d.delay));
                    blacklist = d.blacklist == null ? new HashSet<>() : new HashSet<>(d.blacklist);
                }
            } else {
                blacklist.add("minecraft:elytra");
                blacklist.add("minecraft:diamond_chestplate");
                save();
            }
        } catch (Exception ignored) {}
    }

    public static void save() {
        try {
            Data d = new Data();
            d.enabled = ClickControlClient.enabled;
            d.delay = ClickControlClient.useDelayTicks;
            d.blacklist = blacklist;
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(d));
        } catch (Exception ignored) {}
    }

    private static class Data {
        boolean enabled = true;
        int delay = 3;
        Set<String> blacklist = new HashSet<>();
    }
}
