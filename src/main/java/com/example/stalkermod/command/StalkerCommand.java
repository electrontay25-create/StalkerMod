package com.example.stalkermod.command;

import com.example.stalkermod.event.StalkerEvents;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class StalkerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("stalker")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("stalk").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    StalkerEvents.trySpawnStalkerStalk(player, player.serverLevel());
                    context.getSource().sendSuccess(() -> Component.literal("§c[Stalker] Evento de acecho forzado."), true);
                    return 1;
                }))
                .then(Commands.literal("door").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    StalkerEvents.tryDoorEvent(player, player.serverLevel());
                    context.getSource().sendSuccess(() -> Component.literal("§c[Stalker] Evento de puerta forzado."), true);
                    return 1;
                }))
        );
    }
}
