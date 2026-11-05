package dev.example.clickcontrol.mixin;

import dev.example.clickcontrol.ClickControlClient;
import dev.example.clickcontrol.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow public ClientPlayerInteractionManager interactionManager;
    @Shadow public net.minecraft.client.network.ClientPlayerEntity player;
    @Shadow public int itemUseCooldown;

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void clickcontrol$repeatUse(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient)(Object)this;
        if (!ClickControlClient.enabled || client.player == null || client.interactionManager == null
                || client.currentScreen != null || !client.options.useKey.isPressed()) return;

        ItemStack stack = client.player.getMainHandStack();
        if (stack.isEmpty() || Config.isBlocked(stack.getItem())) return;

        long tick = client.player.age;
        if (tick - ClickControlClient.lastUseTick < ClickControlClient.useDelayTicks) {
            ci.cancel();
            return;
        }
        ClickControlClient.lastUseTick = tick;
        // Let vanilla perform one use action; this controls cadence while the key is held.
    }
}
