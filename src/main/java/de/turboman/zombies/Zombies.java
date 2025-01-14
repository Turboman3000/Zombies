package de.turboman.zombies;

import de.turboman.zombies.handlers.*;
import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.gamedata.tags.Tag;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class Zombies {

    public static void main(String[] args) throws IOException {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MinecraftServer.getBlockManager().registerHandler("minecraft:daylight_detector", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:campfire", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:beehive", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:smoker", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:blast_furnace", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:furnace", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:chest", DummyHandler::new);

        MinecraftServer.getBlockManager().registerHandler("minecraft:player_head", SkullHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:banner", BannerHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:sign", SignHandler::new);

        for (var value : Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:all_signs")).getValues()) {
            MinecraftServer.getBlockManager().registerHandler(value, SignHandler::new);
        }

        for (var value : Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:wooden_doors")).getValues()) {
            MinecraftServer.getBlockManager().registerHandler(value, DoorHandler::new);
        }

        for (var value : Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:wooden_trapdoors")).getValues()) {
            MinecraftServer.getBlockManager().registerHandler(value, TrapdoorHandler::new);
        }

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setChunkLoader(new PolarLoader(Path.of("worlds/world.polar").toAbsolutePath()));

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 142, 0));
        });

        minecraftServer.start("0.0.0.0", 25565);
    }
}