package net.simplyrin.config.test;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;

public class Main {

	public static void main(String[] args) {
		File file = new File("config");
		Configuration config;

		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			config = Config.getConfig(file);

			config.set("Main", "String test");

			Config.saveConfig(config, file);
		}

		config = Config.getConfig(file);

		System.out.println("Main: " + config.getString("Main"));
	}

}
