/*
 * MCClock.java
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

package com.mcclock.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(
        modid = MCClock.MODID,
        name = MCClock.NAME,
        version = MCClock.MODVERSION,
        acceptedMinecraftVersions = MCClock.MCVERSION
)

@SuppressWarnings("unused")
public class MCClock {
    private static final Logger logger = LogManager.getLogger(MCClock.MODID);

    public static final String MODID = "mcclock";
    public static final String MODVERSION = "1.0.0";
    public static final String MCVERSION = "1.12.2";
    public static final String NAME = "MC Clock Mod";

    @SidedProxy(clientSide = "com.mcclock.client.MCClockClientProxy", serverSide = "com.mcclock.common.MCClockServerProxy")
    public static IProxy proxy;

    @Mod.Instance(MODID)
    public static MCClock instance;

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.Init();
    }

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    public String getVersion() {
        return MCClock.MODVERSION;
    }

    public void info(String s) {
        logger.info(s);
    }

    public void error(String s) {
        logger.error(s);
    }
}
