package com.siri;

import java.util.logging.Logger;

import com.siri.net.Server;
import com.siri.plugin.PluginManager;
import com.siri.util.Constants;

public class Application {

	private final static Logger LOGGER = Logger.getLogger("Application");

	public static void main(String[] args) {
		LOGGER.info("jSiri v" + Constants.VERSION);
		PluginManager.load();
		LOGGER.info("Initializing server.");
		Server.init();
		LOGGER.info("Server online, port = " + Constants.HOST_PORT + ".");
	}

}
