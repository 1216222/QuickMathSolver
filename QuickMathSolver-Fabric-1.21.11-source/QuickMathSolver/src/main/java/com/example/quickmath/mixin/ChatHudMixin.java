package com.example.quickmath.mixin;

import com.example.quickmath.MathQuestion;
import com.example.quickmath.QuickMathSolverClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    private static long quickMathSolver$lastSentAt = 0L;
    private static String quickMathSolver$lastQuestion = "";

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("TAIL"))
    private void quickMathSolver$onChatMessage(Text message, CallbackInfo ci) {
        if (!QuickMathSolverClient.isEnabled() || message == null) return;
        String plain = message.getString();
        String answer = MathQuestion.solve(plain);
        if (answer == null) return;

        long now = System.currentTimeMillis();
        synchronized (ChatHudMixin.class) {
            // Avoid duplicate handling if the same formatted message is added twice in a short interval.
            if (plain.equals(quickMathSolver$lastQuestion) && now - quickMathSolver$lastSentAt < 1500L) return;
            quickMathSolver$lastQuestion = plain;
            quickMathSolver$lastSentAt = now;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.getNetworkHandler() != null) {
            client.getNetworkHandler().sendChatMessage(answer);
        }
    }
}
