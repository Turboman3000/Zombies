/*
 * Copyright (c) 2024. RedCraft Studios
 *
 * Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0 DEED)
 * https://creativecommons.org/licenses/by-nc-nd/4.0/
 */

package de.turboman.zombies.handlers;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TrapdoorHandler implements BlockHandler {

    private static void updateDoor(Block block, Point pos, Instance instance) {
        final HashMap<String, String> props = new HashMap<>();

        for (var key : block.properties().keySet()) {
            props.put(key, block.properties().get(key));
        }

        if (props.get("open").equals("false")) {
            props.put("open", "true");
        } else if (props.get("open").equals("true")) {
            props.put("open", "false");
        }

        instance.setBlock(pos, block.withProperties(props));
    }

    @Override
    public boolean onInteract(@NotNull BlockHandler.Interaction interaction) {
        final Point pos = interaction.getBlockPosition();
        updateDoor(interaction.getBlock(), pos, interaction.getInstance());

        return BlockHandler.super.onInteract(interaction);
    }

    @Override
    public @NotNull NamespaceID getNamespaceId() {
        return NamespaceID.from("zombies", "trapdoor");
    }
}
