package com.siri.plugin.impl;

import com.siri.plugin.Plugin;

public class TestPlugin extends Plugin {

	@Override
	public boolean start() {
		return getCommand().equals("Test");
	}

	@Override
	public void execute() {
		sendMessage("Plugin working!");
	}

}
