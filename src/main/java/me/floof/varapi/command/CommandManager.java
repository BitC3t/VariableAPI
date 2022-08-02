package me.floof.varapi.command;

import me.floof.varapi.VariableAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final VariableAPI plugin;

    public CommandManager(VariableAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("varapi")) {
            try {
                this.runCommand(sender, args);
            } catch (IOException e) {
                this.sendError(sender);
            }
            return true;
        }

        return true;
    }

    /*
    - get-info
    - set-int
    - set-string
    - set-double
    - set-location
    - set-boolean
    - get-type
    - reload
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions.clear();
            List<String> tempList = new ArrayList<>();
            tempList.add("get-info");
            tempList.add("set-int");
            tempList.add("set-string");
            tempList.add("set-double");
            tempList.add("set-location");
            tempList.add("set-boolean");
            tempList.add("reload");

            StringUtil.copyPartialMatches(args[0], tempList, completions);
            Collections.sort(completions);

            return completions;
        } else if(args.length == 2) {
            StringUtil.copyPartialMatches(args[1], Arrays.asList("<key>"), completions);
            return completions;
        } else if(args.length == 3) {
            StringUtil.copyPartialMatches(args[2], Arrays.asList("<variable>"), completions);
            return completions;
        } else {
            return completions;
        }
    }

    private void runCommand(CommandSender sender, String[] args) throws IOException {
        switch(args[0]) {
            case "get-info":
                if(args.length > 2) {
                    this.sendError(sender);
                    break;
                }

                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                Object object = this.plugin.getConfigManager().getVariableSetTo(args[1]);
                if(object == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "\n&7[&6&lVarAPI&7] &fNo information found about &bthis variable &fand &bpath.\n"));
                    break;
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "\n&7[&6&lVarAPI&7] &fInformation about this key:\n" +
                                "&7[&6&lVarAPI&7] &bKey: &f"+ args[1] + "\n" +
                                "&7[&6&lVarAPI&7] &bVariable: &f" + object.toString() + "\n"));
                break;

            case "set-int":
                if(args.length > 3) {
                    this.sendError(sender);
                    break;
                }

                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                if(args[2] == null || args[2] == "") {
                    this.setError(sender);
                    break;
                }

                if(this.parseInteger(sender, args[2]) == null) {
                    break;
                }

                this.plugin.getIntegerConfig().writeData(args[1], Integer.parseInt(args[2]));
                this.successMessage(sender, args[1], args[2]);
                break;

            case "set-string":
                if(args.length > 3) {
                    this.sendError(sender);
                    break;
                }

                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                if(args[2] == null || args[2] == "") {
                    this.setError(sender);
                    break;
                }

                this.plugin.getStringConfig().writeData(args[1], args[2]);
                this.successMessage(sender, args[1], args[2]);
                break;

            case "set-boolean":
                if(args.length > 3) {
                    this.sendError(sender);
                    break;
                }

                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                if(args[2] == null || args[2] == "") {
                    this.setError(sender);
                    break;
                }

                if(this.parseBoolean(sender, args[2]) == null) {
                    break;
                }

                this.plugin.getBooleanConfig().writeData(args[1], Boolean.parseBoolean(args[2]));
                this.successMessage(sender, args[1], args[2]);
                break;

            case "set-double":
                if(args.length > 3) {
                    this.sendError(sender);
                    break;
                }

                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                if(args[2] == null || args[2] == "") {
                    this.setError(sender);
                    break;
                }

                if(this.parseDouble(sender, args[2]) == null) {
                    break;
                }

                this.plugin.getDoubleConfig().writeData(args[1], Double.parseDouble(args[2]));
                this.successMessage(sender, args[1], args[2]);
                break;

            case "set-location":
                if(args[1] == null || args[1] == "") {
                    this.sendError(sender);
                    break;
                }

                if(args.length > 2) {
                    this.sendError(sender);
                    break;
                }

                if(!(sender instanceof Player)) {
                    this.sendError(sender);
                    break;
                }

                Player player = (Player) sender;
                Location location = player.getLocation();
                location.setYaw(player.getLocation().getYaw());
                location.setPitch(player.getLocation().getPitch());

                this.plugin.getLocationConfig().writeData(args[1], location);
                this.successMessage(sender, args[1], "Location -> World: " + location.getWorld().getName() + " // X: "
                        + location.getBlockX() + " // Y: " + location.getBlockY() + " // Z: "
                        + location.getBlockZ());
                break;

            case "reload":
                this.plugin.reloadData();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "\n&7[&6&lVarAPI&7] &aReloaded successfully.\n"));

            default:
                this.setError(sender);
                break;
        }
    }

    private void successMessage(CommandSender sender, String key, Object value) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "\n&7[&6&lVarAPI&7] &aSuccessfully saved your data! &f// Key: &b" + key + " &f// Value: &b" + value.toString() + "\n"));
    }

    private void sendError(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "\n&7[&6&lVarAPI&7] &cYou have not provided appropriate arguments for this command.\n"));
    }

    private void setError(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "\n&7[&6&lVarAPI&7] &b/varapi &fset-[int/double/boolean/location/string] [key] [variable]\n"));
    }

    private Boolean parseBoolean(CommandSender sender, String s) {
        try {
            Boolean bool = Boolean.parseBoolean(s);
            return bool;
        } catch (Exception exception){
            this.setError(sender);
        }

        return null;
    }

    private Integer parseInteger(CommandSender sender, String s) {
        try {
            Integer integer = Integer.parseInt(s);
            return integer;
        } catch (NumberFormatException e) {
            this.setError(sender);
        }
        return null;
    }

    private Double parseDouble(CommandSender sender, String s) {
        try {
            Double d = Double.parseDouble(s);
            return d;
        } catch(Exception e) {
            this.setError(sender);
        }

        return null;
    }
}
