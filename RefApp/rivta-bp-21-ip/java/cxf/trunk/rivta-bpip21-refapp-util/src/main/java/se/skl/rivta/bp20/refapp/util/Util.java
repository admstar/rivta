/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
			URL url = Util.class.getResource("/rivta-bpip21-refapp-util.properties");
			properties.load(url.openStream());
			logger.debug("Loaded properties: {}", properties);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}