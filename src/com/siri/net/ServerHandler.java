package com.siri.net;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.ssl.SslHandler;

import com.siri.plugin.PluginManager;

public class ServerHandler extends SimpleChannelUpstreamHandler {

	private final static Logger LOGGER = Logger.getLogger("ServerHandler");

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.println("New connection.");
		SslHandler sslHandler = ctx.getPipeline().get(SslHandler.class);
		sslHandler.handshake();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		String message = (String) e.getMessage();
		LOGGER.info("Message recieved - " + message);
		PluginManager.handle(ctx, message);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		LOGGER.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
	}
}
