package de.turboman.zombies;

import de.turboman.zombies.handlers.BannerHandler;
import de.turboman.zombies.handlers.DummyHandler;
import de.turboman.zombies.handlers.SignHandler;
import de.turboman.zombies.handlers.SkullHandler;
import net.hollowcube.polar.PolarLoader;
import net.mangolise.anticheat.MangoAC;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Zombies {

    private static final Logger log = LoggerFactory.getLogger(Zombies.class);

    public static MangoAC antiCheat;

    public static void main(String[] args) throws IOException {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MinecraftServer.getBlockManager().registerHandler("minecraft:comparator", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:command_block", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:daylight_detector", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:campfire", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:beehive", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:smoker", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:blast_furnace", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:furnace", DummyHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:chest", DummyHandler::new);

        MinecraftServer.getBlockManager().registerHandler("minecraft:skull", SkullHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:player_head", SkullHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:banner", BannerHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:sign", SignHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:hanging_sign", SignHandler::new);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setChunkLoader(new PolarLoader(Path.of("worlds/ZombiesTest.polar").toAbsolutePath()));

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();

            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, -55, 0));
        });

        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            final Player player = event.getPlayer();

            player.setGameMode(GameMode.ADVENTURE);
        });

        MangoAC.Config config = new MangoAC.Config(false, List.of(), List.of(), List.of());
        antiCheat = new MangoAC(config);

        antiCheat.start();

        minecraftServer.start("0.0.0.0", 25565);
    }
}