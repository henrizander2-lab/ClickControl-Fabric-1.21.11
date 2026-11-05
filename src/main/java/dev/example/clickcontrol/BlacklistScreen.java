package dev.example.clickcontrol;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BlacklistScreen extends Screen {
    public BlacklistScreen() { super(Text.literal("ClickControl Blacklist")); }

    @Override
    protected void init() {
        int cx = width / 2;
        addDrawableChild(ButtonWidget.builder(Text.literal("Add held item to blacklist"),
                b -> {
                    if (client != null && client.player != null) {
                        String id = net.minecraft.registry.Registries.ITEM
                                .getId(client.player.getMainHandStack().getItem()).toString();
                        Config.blacklist.add(id);
                        Config.save();
                    }
                }).dimensions(cx - 110, height / 2 - 25, 220, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Remove held item from blacklist"),
                b -> {
                    if (client != null && client.player != null) {
                        String id = net.minecraft.registry.Registries.ITEM
                                .getId(client.player.getMainHandStack().getItem()).toString();
                        Config.blacklist.remove(id);
                        Config.save();
                    }
                }).dimensions(cx - 110, height / 2 + 5, 220, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"),
                b -> close()).dimensions(cx - 110, height / 2 + 35, 220, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, "Hold item in hand, then add/remove it", width / 2,
                height / 2 - 55, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "Blacklist entries: " + Config.blacklist.size(), width / 2,
                height / 2 + 65, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
}
