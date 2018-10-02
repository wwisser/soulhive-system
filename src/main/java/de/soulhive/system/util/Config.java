package de.soulhive.system.util;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Set;

@Getter
public class Config {

    private File file;
    private FileConfiguration fileConfiguration;

    public Config(String path, String filename) {
        this.file = new File(path, filename);
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void setDefault(String key, Object value) {
        if (!this.contains(key)) {
            this.set(key, value);
            this.saveFile();
        }
    }

    @SneakyThrows
    public void saveFile() {
        this.fileConfiguration.save(this.file);
    }

    public boolean contains(String path) {
        return this.fileConfiguration.contains(path);
    }

    public void set(String path, Object value) {
        this.fileConfiguration.set(path, value);
    }

    public Object get(String path) {
        return this.fileConfiguration.get(path);
    }

    public Object get(String path, Object def) {
        return this.fileConfiguration.get(path, def);
    }

    public String getString(String path) {
        return this.fileConfiguration.getString(path);
    }

    public int getInt(String path) {
        return this.fileConfiguration.getInt(path);
    }

    public long getLong(String path) {
        return this.fileConfiguration.getLong(path);
    }

    public Set<String> getKeys(boolean deep) {
        return this.fileConfiguration.getKeys(deep);
    }

    public List<?> getList(String path) {
        return this.fileConfiguration.getList(path);
    }

    public List<String> getStringList(String path) {
        return this.fileConfiguration.getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return this.fileConfiguration.getIntegerList(path);
    }

}