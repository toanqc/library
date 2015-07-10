package mpp.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String fileName) {
		InputStream input = null;
		Properties props = new Properties();

		try {
			input = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
			// load a properties file
			props.load(input);
			// get the property value and print it out
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return props;
	}
}
