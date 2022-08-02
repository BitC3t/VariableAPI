package me.floof.varapi.config;

import me.floof.varapi.VariableAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BooleanConfig {

    private final VariableAPI plugin;
    private FileConfiguration config;
    private File file;

    /**
     * Boolean Configuration (not related to API)
     * @param plugin
     */
    public BooleanConfig(VariableAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.file = new File(this.plugin.getDataFolder(), "booleans.yml");

        if(!this.file.exists()) {
            this.file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void writeData(String key, Boolean bool) {
        this.config.set(key, bool);
        this.saveData();
        this.reloadData();
    }

    public Boolean fetchData(String key) {
        if(!(this.config.get(key) instanceof Boolean)) {
            return null;
        }

        Boolean bool = this.config.getBoolean(key);
        return bool;
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
