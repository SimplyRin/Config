package net.simplyrin.config;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {

	public static void saveConfig(Configuration config, File file) {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig(Configuration config, String file) {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getConfig(File file) {
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Configuration getConfig(String file) {
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
