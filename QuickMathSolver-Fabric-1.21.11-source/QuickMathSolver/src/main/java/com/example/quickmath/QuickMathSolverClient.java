package com.example.quickmath;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class QuickMathSolverClient implements ClientModInitializer {
    private static volatile boolean enabled = true;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal("mathsolver")
                .executes(context -> {
                    enabled = !enabled;
                    context.getSource().sendFeedback(Text.literal("Quick Math Solver: " + (enabled ? "ON" : "OFF")));
                    return 1;
                })
                .then(literal("on").executes(context -> {
                    enabled = true;
                    context.getSource().sendFeedback(Text.literal("Quick Math Solver: ON"));
                    return 1;
                }))
                .then(literal("off").executes(context -> {
                    enabled = false;
                    context.getSource().sendFeedback(Text.literal("Quick Math Solver: OFF"));
                    return 1;
                }))));
    }

    public static boolean isEnabled() { return enabled; }
}
