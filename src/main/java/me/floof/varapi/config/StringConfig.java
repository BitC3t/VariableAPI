package me.floof.varapi.config;

import me.floof.varapi.VariableAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class StringConfig {

    private final VariableAPI plugin;
    private FileConfiguration config;
    private File file;

    /**
     * String Configuration (not related to API)
     * @param plugin
     */
    public StringConfig(VariableAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.file = new File(this.plugin.getDataFolder(),"strings.yml");

        if(!this.file.exists()) {
            this.file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void writeData(String key, String string) {
        this.config.set(key, string);
        this.saveData();
        this.reloadData();
    }

    public String fetchData(String key) {
        if(!(this.config.get(key) instanceof String)) {
            return null;
        }

        String string = this.config.getString(key);
        return string;
    }

    public void saveData() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            this.plugin.getLogger().info("Failed to save strings.yml");
        }
    }

    private void reloadData() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}
