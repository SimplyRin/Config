package net.simplyrin.config.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;

public class Main {

	public static void main(String[] args) {
		Configuration config = null;
		try {
			config = Config.getConfig(new URL("https://gist.githubusercontent.com/SimplyRin/f3b19b8cb2f04c6793b7369abd133432/raw/761657575f6e45df345a5e353ca4bc4cfebaddd0/Config.yml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String s1 = config.getString("String-1");
		System.out.println("s1 -> " + s1);

		List<String> l1 = config.getStringList("List-1");
		for (String value : l1) {
			System.out.println(value);
		}
	}

}
