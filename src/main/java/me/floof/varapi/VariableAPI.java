package me.floof.varapi;

import me.floof.varapi.command.CommandManager;
import me.floof.varapi.config.*;
import me.floof.varapi.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class VariableAPI extends JavaPlugin {

    private BooleanConfig booleanConfig = new BooleanConfig(this);
    private DoubleConfig doubleConfig = new DoubleConfig(this);
    private LocationConfig locationConfig = new LocationConfig(this);
    private IntegerConfig integerConfig = new IntegerConfig(this);
    private StringConfig stringConfig = new StringConfig(this);
    private ConfigManager configManager = new ConfigManager(this);
    private VariableAPI instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.createDirectory();
        this.instance = this;

        // Initialize all Configs
        try {
            this.booleanConfig.init();
            this.doubleConfig.init();
            this.locationConfig.init();
            this.integerConfig.init();
            this.stringConfig.init();
        } catch (IOException e) {
            this.getLogger().info("Failed to initialize files.");
        }

        // Enable Commands
        this.getCommand("varapi").setExecutor(new CommandManager(this));
        this.getCommand("varapi").setTabCompleter(new CommandManager(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Gets the VariableAPI Instance
     * @return VariableAPI
     */
    public VariableAPI getInstance() {
        return this.instance;
    }

    /**
     * Returns the Variable affliated with the
     * key - ensure to do null checks
     * @param key
     * @return Object
     */
    public Object getVariable(String key) {
       return this.configManager.getVariableSetTo(key);
    }
    /**
     * Returns the Config Manager
     * @return
     */
    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public BooleanConfig getBooleanConfig() {
        return this.booleanConfig;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public IntegerConfig getIntegerConfig() {
        return this.integerConfig;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public LocationConfig getLocationConfig() {
        return this.locationConfig;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public StringConfig getStringConfig() {
        return this.stringConfig;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public DoubleConfig getDoubleConfig() {
        return this.doubleConfig;
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    private void createDirectory() {
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
    }

    /**
     * Not related to API (ignore)
     * @return
     */
    public void reloadData() throws IOException {
        this.booleanConfig.init();
        this.doubleConfig.init();
        this.locationConfig.init();
        this.integerConfig.init();
        this.stringConfig.init();
    }
}
