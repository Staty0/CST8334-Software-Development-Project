package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;

	static {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/model/Properties")) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[] getCardSize() {
		String sizeInFile = properties.getProperty("Cardsize");
		CardSizes cardSize = CardSizes.valueOf(sizeInFile);
		return cardSize.getValue();
	}
	
	public static String getBackgroundColour() {
		return properties.getProperty("BackgroundColour");
	}
	
	public static boolean getVegasRules() {
		return Boolean.parseBoolean(properties.getProperty("getVegasRules"));
	}
}