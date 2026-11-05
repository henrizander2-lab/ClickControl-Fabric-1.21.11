package dev.example.clickcontrol;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClickControlClient implements ClientModInitializer {
    public static KeyBinding OPEN_MENU;
    public static int useDelayTicks = 3;
    public static boolean enabled = true;
    public static long lastUseTick = 0;

    @Override
    public void onInitializeClient() {
        Config.load();
        OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.clickcontrol.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.clickcontrol"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new ClickControlScreen());
            }
        });
    }
}
