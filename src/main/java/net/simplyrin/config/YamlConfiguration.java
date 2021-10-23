package net.simplyrin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * https://github.com/SpigotMC/BungeeCord
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class YamlConfiguration extends ConfigurationProvider {

	private final ThreadLocal<Yaml> yaml = new ThreadLocal<Yaml>() {
		@Override
		protected Yaml initialValue() {
			Representer representer = new Representer() {
				{
					this.representers.put(Configuration.class, new Represent() {
						@Override
						public Node representData(Object data) {
							return represent(((Configuration) data).self);
						}
					});
				}
			};

			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

			return new Yaml(new Constructor(), representer, options);
		}
	};

	@Override
	public void save(Configuration config, File file) throws IOException {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			this.save(config, writer);
		}
	}

	@Override
	public void save(Configuration config, Writer writer) {
		this.yaml.get().dump(config.self, writer);
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
		Map<String, Object> map = this.yaml.get().loadAs(reader, LinkedHashMap.class);
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
	@SuppressWarnings("unchecked")
	public Configuration load(InputStream is, Configuration defaults) {
		Map<String, Object> map = this.yaml.get().loadAs(is, LinkedHashMap.class);
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}

	@Override
	public Configuration load(String string) {
		return this.load(string, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(String string, Configuration defaults) {
		Map<String, Object> map = this.yaml.get().loadAs(string, LinkedHashMap.class);
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}
}
