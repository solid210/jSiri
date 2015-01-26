package com.siri.net;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class SSLContextFactory {

	private final static Logger LOGGER = Logger.getLogger("SSLContextFactory");

	private final static String KEYSTORE = "./keystore.jks";
	private final static String JAVA_KEY_STORE = "JKS";
	private final static String ALGORITHM = "SunX509";
	private final static String PROTOCOL = "TLS";
	private static SSLContext serverContext;

	static {
		try {
			final KeyStore ks = KeyStore.getInstance(JAVA_KEY_STORE);
			ks.load(new FileInputStream(KEYSTORE), getKeyStorePassword());

			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
			kmf.init(ks, getCertificatePassword());

			serverContext = SSLContext.getInstance(PROTOCOL);
			serverContext.init(kmf.getKeyManagers(), null, null);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unexpected exception while loading certificate.", e.getCause());
		}
	}

	public static char[] getCertificatePassword() {
		return "1234".toCharArray();
	}

	public static char[] getKeyStorePassword() {
		return "1234".toCharArray();
	}

	public static SSLContext getServerContext() {
		return serverContext;
	}

}
