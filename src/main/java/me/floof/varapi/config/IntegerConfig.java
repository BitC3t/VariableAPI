package me.floof.varapi.config;

import me.floof.varapi.VariableAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class IntegerConfig {

    private final VariableAPI plugin;
    private FileConfiguration config;
    private File file;

    /**
     * Integer Configuration (not related to API)
     * @param plugin
     */
    public IntegerConfig(VariableAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.file = new File(this.plugin.getDataFolder(),"integers.yml");

        if(!this.file.exists()) {
            this.file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void writeData(String key, Integer integer) {
        this.config.set(key, integer);
        this.saveData();
        this.reloadData();
    }

    public Integer fetchData(String key) {
        if(!(this.config.get(key) instanceof Integer)) {
            return null;
        }

        Integer integer = this.config.getInt(key);
        return integer;
    }

    public void saveData() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            this.plugin.getLogger().info("Failed to save integers.yml");
        }
    }

    private void reloadData() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}
