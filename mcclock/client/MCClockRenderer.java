/*
 * MCClockRenderer.java
 *
 *  Copyright (c) 2018 Michael Sheppard
 *
 * =====GPL=============================================================
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 * =====================================================================
 */

package com.mcclock.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class MCClockRenderer extends Gui {
    private static final int CLOCK_COLOR = 0xffff0000; // red
    private static final int MAX_TICKS = 24000;
    private static final double TICKS_2_SECONDS = 3.6;

    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private static FontRenderer fontRenderer = MINECRAFT.fontRenderer;

    private static boolean isVisible = true;
    private static boolean realTime = false;


    @SubscribeEvent
    public void drawClock(RenderGameOverlayEvent.Post event) {
        if (event.isCancelable() || MINECRAFT.gameSettings.showDebugInfo || !isVisible) {
            return;
        }
        if (MINECRAFT.inGameHasFocus && event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            ScaledResolution sr = new ScaledResolution(MINECRAFT);

            int x = sr.getScaledWidth() - fontRenderer.getStringWidth("00:00:00") - 8; //sr.getScaledWidth() / 2) + 92;
            int x2 = sr.getScaledWidth() - fontRenderer.getStringWidth("0000.0/-0000.0") - 8;
            int y = sr.getScaledHeight() - 11;
            int textY = y - (fontRenderer.FONT_HEIGHT / 2);

            boolean unicodeFlag = fontRenderer.getUnicodeFlag();
            fontRenderer.setUnicodeFlag(true);

            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            // draw the text
            String timeStr;
            if (realTime) {
                timeStr = formatRealTime();
            } else {
                timeStr = formatMinecraftTime(MINECRAFT.world.getWorldTime());
            }
            fontRenderer.drawStringWithShadow(timeStr, x, textY, CLOCK_COLOR);

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();

            fontRenderer.setUnicodeFlag(unicodeFlag);
        }
    }

    public String formatMinecraftTime(double ticks) {
        double realSeconds = (ticks % MAX_TICKS) * TICKS_2_SECONDS; // ticks to seconds wrapping ticks if necessary
        int McHours = MathHelper.floor(((realSeconds / 3600.0) + 6) % 24); // Minecraft ticks are offset from calendar clock by 6 hours
        int McMinutes = MathHelper.floor((realSeconds / 60.0) % 60);
        int McSeconds = MathHelper.floor(realSeconds % 60);
        return String.format("§l%02d:%02d:%02d", McHours, McMinutes, McSeconds);
    }

    public String formatRealTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return String.format("§l%s", sdf.format(date));
    }

    public static void toggleClock() {
        isVisible = !isVisible;
    }

    public static void toggleRealTime() {
        realTime = ! realTime;
    }

}
