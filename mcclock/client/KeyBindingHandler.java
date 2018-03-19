/*
 * KeyBindingHandler.java
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


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("unused")

@Mod.EventBusSubscriber(Side.CLIENT)
public class KeyBindingHandler {
    @SubscribeEvent
    public static void clientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (ModKeyBinding.TOGGLE_CLOCK_VISIBILITY.isPressed()) {
            MCClockRenderer.toggleClock();
        }
        if (ModKeyBinding.TOGGLE_CLOCK_LOCALTIME.isPressed()) {
            MCClockRenderer.toggleRealTime();
        }
    }
}
