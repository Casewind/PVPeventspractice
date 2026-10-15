package dev.pvpevents;

import dev.pvpevents.command.PvPEventsCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvPEventsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PluginCommand command = getCommand("pvpevents");
        if (command == null) {
            getLogger().severe("Command 'pvpevents' is missing from plugin.yml. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PvPEventsCommand handler = new PvPEventsCommand(this);
        command.setExecutor(handler);
        command.setTabCompleter(handler);

        getLogger().info("PvPEvents " + getPluginMeta().getVersion() + " enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PvPEvents disabled.");
    }
}
