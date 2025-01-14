/*
 * Copyright (c) 2024. RedCraft Studios
 *
 * Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0 DEED)
 * https://creativecommons.org/licenses/by-nc-nd/4.0/
 */

package de.turboman.zombies.handlers;

import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.tag.Tag;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class SignHandler implements BlockHandler {
    @Override
    public @NotNull NamespaceID getNamespaceId() {
        return NamespaceID.from("zombies", "sign");
    }

    @Override
    public @NotNull Collection<Tag<?>> getBlockEntityTags() {
        return List.of(
                Tag.NBT("front_text"),
                Tag.NBT("back_text")
        );
    }
}