package me.floof.varapi.config;

import me.floof.varapi.VariableAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DoubleConfig {
    private final VariableAPI plugin;
    private FileConfiguration config;
    private File file;

    /**
     * Double Configuration (not related to API)
     * @param plugin
     */
    public DoubleConfig(VariableAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.file = new File(this.plugin.getDataFolder(),"doubles.yml");

        if(!this.file.exists()) {
            this.file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void writeData(String key, Double integer) {
        this.config.set(key, integer);
        this.saveData();
        this.reloadData();
    }

    public Double fetchData(String key) {
        if(!(this.config.get(key) instanceof Double)) {
            return null;
        }

        Double integer = this.config.getDouble(key);
        return integer;
    }

    public void saveData() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            this.plugin.getLogger().info("Failed to save doubles.yml");
        }
    }

    private void reloadData() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}
