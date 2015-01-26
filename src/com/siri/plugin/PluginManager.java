package com.siri.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;

public class PluginManager {

	private final static Logger LOGGER = Logger.getLogger("PluginManager");

	private static final LinkedList<Plugin> PLUGINS = new LinkedList<Plugin>();

	public static void load() {
		final File directory = new File("./plugins/");
		if (!directory.exists()) {
			return;
		}
		final String[] files = directory.list();
		URLClassLoader classLoader = null;
		try {
			classLoader = URLClassLoader.newInstance(new URL[] { directory.toURI().toURL() });
			for (String file : files) {
				PLUGINS.add((Plugin) classLoader.loadClass(file.replace(".class", "")).newInstance());
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unexpected exception from plugin manager.", e.getCause());
		}
		LOGGER.info("Loaded " + (PLUGINS.size() == 1 ? "1 plugin." : PLUGINS.size() + " plugins."));
	}

	public static void handle(ChannelHandlerContext ctx, String message) {
		for (Plugin plugin : PLUGINS) {
			plugin.set(ctx, message);
			if (plugin.start()) {
				LOGGER.info("Executing plugin : " + plugin.getClass().getName());
				plugin.execute();
				return;
			}
		}
		ctx.getChannel().write("Plugin doesn't exist.");
	}

}
