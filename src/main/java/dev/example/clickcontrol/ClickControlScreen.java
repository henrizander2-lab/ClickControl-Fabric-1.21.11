package dev.example.clickcontrol;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ClickControlScreen extends Screen {
    private SpeedSlider slider;

    public ClickControlScreen() {
        super(Text.literal("ClickControl"));
    }

    @Override
    protected void init() {
        int cx = width / 2;
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Fast Use: " + (ClickControlClient.enabled ? "ON" : "OFF")),
                b -> {
                    ClickControlClient.enabled = !ClickControlClient.enabled;
                    Config.save();
                    b.setMessage(Text.literal("Fast Use: " + (ClickControlClient.enabled ? "ON" : "OFF")));
                }).dimensions(cx - 100, height / 2 - 35, 200, 20).build());

        slider = addDrawableChild(new SpeedSlider(cx - 100, height / 2 - 5, 200, 20,
                ClickControlClient.useDelayTicks));
        addDrawableChild(ButtonWidget.builder(Text.literal("Blacklist file"),
                b -> client.setScreen(new BlacklistScreen())).dimensions(cx - 100, height / 2 + 25, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"),
                b -> close()).dimensions(cx - 100, height / 2 + 55, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 70, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private static class SpeedSlider extends SliderWidget {
        SpeedSlider(int x, int y, int width, int height, int ticks) {
            super(x, y, width, height, Text.literal("Delay: " + ticks + " ticks"), (ticks - 1) / 19.0);
            updateMessage();
        }
        @Override protected void updateMessage() {
            int ticks = 1 + (int)Math.round(value * 19);
            setMessage(Text.literal("Delay: " + ticks + " ticks"));
        }
        @Override protected void applyValue() {
            ClickControlClient.useDelayTicks = 1 + (int)Math.round(value * 19);
            Config.save();
        }
    }
}
