package se.skl.rivta.bp20.refapp.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);
	static private       Properties properties = null;
	
	public static String getProperty(String key) {
		if (properties == null) {
			loadProperties();
		}
		return properties.getProperty(key);
	}

	private static void loadProperties() {
		try {
			properties = new Properties();
			URL url = ClassLoader.getSystemResource("rivta-bp20-refapp-util.props");
			properties.load(url.openStream());
			logger.debug("Loaded properties: {}", properties);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}