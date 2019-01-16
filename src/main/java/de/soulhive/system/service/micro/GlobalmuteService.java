package de.soulhive.system.service.micro;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@FeatureService
public class GlobalmuteService extends Service implements Listener {

    private boolean active = false;

    @Override
    public void initialize() {
        super.registerCommand("globalmute", new CommandGlobalmute());
        super.registerListener(this);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (this.active && !player.hasPermission("soulhive.globalmute.bypass")) {
            event.setCancelled(true);
            player.sendMessage(Settings.PREFIX + "§cDer globale Chat ist gerade deaktiviert.");
        }
    }

    private class CommandGlobalmute extends CommandExecutorWrapper {

        @Override
        public void process(CommandSender sender, String label, String[] args) throws CommandException {
            ValidateCommand.permission(sender, "soulhive.globalmute");

            GlobalmuteService.this.active ^= true;

            Bukkit.broadcastMessage(
                Settings.PREFIX
                    + "Der globale Chat wurde "
                    + (GlobalmuteService.this.active ? "§cdeaktiviert" : "§aaktiviert")
                    + "§7!"
            );
        }

    }

}
