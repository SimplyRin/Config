package net.simplyrin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * https://github.com/SpigotMC/BungeeCord
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonConfiguration extends ConfigurationProvider {

	private final Gson json = new GsonBuilder().serializeNulls().setPrettyPrinting()
			.registerTypeAdapter(Configuration.class, new JsonSerializer<Configuration>() {
				@Override
				public JsonElement serialize(Configuration src, Type typeOfSrc, JsonSerializationContext context) {
					return context.serialize(((Configuration) src).self);
				}
			}).create();

	@Override
	public void save(Configuration config, File file) throws IOException {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			this.save(config, writer);
		}
	}

	@Override
	public void save(Configuration config, Writer writer) {
		this.json.toJson(config.self, writer);
	}

	@Override
	public Configuration load(File file) throws IOException {
		return this.load(file, null);
	}

	@Override
	public Configuration load(File file, Configuration defaults) throws IOException {
		try (FileInputStream is = new FileInputStream(file)) {
			return this.load(is, defaults);
		}
	}

	@Override
	public Configuration load(Reader reader) {
		return this.load(reader, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(Reader reader, Configuration defaults) {
		Map<String, Object> map = this.json.fromJson(reader, LinkedHashMap.class);
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}

	@Override
	public Configuration load(InputStream is) {
		return this.load(is, null);
	}

	@Override
	public Configuration load(InputStream is, Configuration defaults) {
		return this.load(new InputStreamReader(is, StandardCharsets.UTF_8), defaults);
	}

	@Override
	public Configuration load(String string) {
		return this.load(string, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(String string, Configuration defaults) {
		Map<String, Object> map = this.json.fromJson(string, LinkedHashMap.class);
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}
}
