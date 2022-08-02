package me.floof.varapi.managers;

import me.floof.varapi.VariableAPI;
import me.floof.varapi.config.BooleanConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ConfigManager {

    private final VariableAPI plugin;

    public ConfigManager(VariableAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the Variable Set To
     * (Can be either Bool, Int, String,
     * Location or Double)
     * @param {@link String} key
     * @return Object
     */
    public Object getVariableSetTo(String key) {
        if(this.getType(key) == null) {
            return null;
        }

        if(this.getType(key).equals(Integer.class)) {
            return this.plugin.getIntegerConfig().fetchData(key);
        }

        if(this.getType(key).equals(Double.class)) {
            return this.plugin.getDoubleConfig().fetchData(key);
        }

        if(this.getType(key).equals(String.class)) {
            return this.plugin.getStringConfig().fetchData(key);
        }

        if(this.getType(key).equals(Location.class)) {
            Location location = this.plugin.getLocationConfig().fetchData(key);
            return "Location -> World: " + location.getWorld().getName() + " // X: "
                    + location.getBlockX() + " // Y: " + location.getBlockY() + " // Z: "
                    + location.getBlockZ();
        }

        if(this.getType(key).equals(Boolean.class)) {
            return this.plugin.getBooleanConfig().fetchData(key);
        }

        return null;
    }

    /**
     * This function is used to search through all the
     * Configurations to find what type of class the path
     * has been set to.
     * @param {@link String} key
     * @return Class<?> as a Class
     */
    public Class<?> getType(String key) {
        if(this.plugin.getIntegerConfig().fetchData(key) != null) {
            return this.plugin.getIntegerConfig().fetchData(key).getClass();
        }

        if(this.plugin.getStringConfig().fetchData(key) != null) {
            return this.plugin.getStringConfig().fetchData(key).getClass();
        }

        if(this.plugin.getBooleanConfig().fetchData(key) != null) {
            return this.plugin.getBooleanConfig().fetchData(key).getClass();
        }

        if(this.plugin.getLocationConfig().fetchData(key) != null) {
            return this.plugin.getLocationConfig().fetchData(key).getClass();
        }

        if(this.plugin.getDoubleConfig().fetchData(key) != null) {
            return this.plugin.getDoubleConfig().fetchData(key).getClass();
        }

        return null;
    }

    /**
     * Command-Specific Function for obtaining the
     * Class as a Chat Message
     * @param {@link String} key
     * @return String - as a Chat Message
     */
    public @NotNull String getStringTypeFormat(String key) {
        Class<?> keyClass = this.getType(key);

        if (keyClass.isInstance(Integer.class)) {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fType of data stored in path &b" + key + " is: &9Integer&f.&cclass");
        } else if (keyClass.isInstance(String.class)) {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fType of data stored in path &b" + key + " is: &9String&f.&cclass");
        } else if (keyClass.isInstance(Double.class)) {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fType of data stored in path &b" + key + " is: &9Double&f.&cclass");
        } else if (keyClass.isInstance(Boolean.class)) {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fType of data stored in path &b" + key + " is: &9Boolean&f.&cclass");
        } else if (keyClass.isInstance(Location.class)) {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fType of data stored in path &b" + key + " is: &9Location&f.&cclass");
        } else {
            return ChatColor.translateAlternateColorCodes('&',
                    "&7[&6&lVarAPI&7] &fThis key has &cno variable &fattached to it.");
        }
    }
}
