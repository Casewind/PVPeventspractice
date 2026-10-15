package dev.pvpevents.command;

import dev.pvpevents.PvPEventsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class PvPEventsCommand implements TabExecutor {

    private static final String ADMIN_PERMISSION = "pvpevents.admin";

    private final PvPEventsPlugin plugin;

    public PvPEventsCommand(PvPEventsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String sub = args.length == 0 ? "info" : args[0].toLowerCase(Locale.ROOT);

        switch (sub) {
            case "info" -> sendInfo(sender);
            case "reload" -> reload(sender);
            default -> sender.sendMessage(Component.text(
                    "Unknown subcommand. Try /" + label + " info", NamedTextColor.RED));
        }
        return true;
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage(Component.text(
                "PvPEvents v" + plugin.getPluginMeta().getVersion(), NamedTextColor.GOLD));
    }

    private void reload(CommandSender sender) {
        if (!sender.hasPermission(ADMIN_PERMISSION)) {
            sender.sendMessage(Component.text(
                    "You do not have permission to do that.", NamedTextColor.RED));
            return;
        }
        plugin.reloadConfig();
        sender.sendMessage(Component.text("PvPEvents configuration reloaded.", NamedTextColor.GREEN));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return List.of();
        }
        List<String> options = new ArrayList<>();
        options.add("info");
        if (sender.hasPermission(ADMIN_PERMISSION)) {
            options.add("reload");
        }
        String prefix = args[0].toLowerCase(Locale.ROOT);
        options.removeIf(option -> !option.startsWith(prefix));
        return options;
    }
}
