package me.floof.varapi.config;

import me.floof.varapi.VariableAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationConfig {

    private final VariableAPI plugin;
    private FileConfiguration config;
    private File file;

    /**
     * Location Configuration (not related to API)
     * @param plugin
     */
    public LocationConfig(VariableAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.file = new File(this.plugin.getDataFolder(),"locations.yml");

        if(!this.file.exists()) {
            this.file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void writeData(String key, Location location) {
        this.config.set(key, location);
        this.saveData();
        this.reloadData();
    }

    public Location fetchData(String key) {
        if(!(this.config.get(key) instanceof Location)) {
            return null;
        }

        Location location = this.config.getLocation(key);
        return location;
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
