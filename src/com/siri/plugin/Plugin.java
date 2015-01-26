package com.siri.plugin;

import org.jboss.netty.channel.ChannelHandlerContext;

public abstract class Plugin {

	private ChannelHandlerContext ctx;
	private String command;

	public abstract boolean start();

	public abstract void execute();

	public ChannelHandlerContext getChannelHandler() {
		return ctx;
	}
	
	public void sendMessage(String message) {
		getChannelHandler().getChannel().write(message);
	}

	public String getCommand() {
		return command;
	}

	protected void set(ChannelHandlerContext ctx, String command) {
		this.ctx = ctx;
		this.command = command;
	}

}
