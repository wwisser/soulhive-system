package de.soulhive.system.service.micro;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.InvalidArgsException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpyCommandService extends Service implements Listener {

    private List<UUID> messageSpy = new ArrayList<>();
    private List<UUID> commandSpy = new ArrayList<>();

    @Override
    public void initialize() {
        super.registerCommand("spy", new CommandSpy());
        super.registerListener(this);
    }

    public void emitMessage(final String message, final Player emitter, final SpyLevel spyLevel) {
        List<UUID> entries = spyLevel == SpyLevel.COMMAND ? this.commandSpy : this.messageSpy;

        for (UUID uuid : entries) {
            final Player player = Bukkit.getPlayer(uuid);

            if (player != null && player != emitter) {
                player.sendMessage(
                    Settings.PREFIX
                        + "§d" + spyLevel.toString()
                        + " §7" + emitter.getName()
                        + " §f:: §7" + message
                );
            }
        }
    }

    public enum SpyLevel {
        COMMAND,
        MESSAGE
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        this.emitMessage(event.getMessage(), player, SpyLevel.COMMAND);
    }

    private class CommandSpy extends CommandExecutorWrapper {

        private static final String USAGE = "/spy <msg|cmd>";

        @Override
        public void process(CommandSender sender, String label, String[] args) throws CommandException {
            final Player player = ValidateCommand.onlyPlayer(sender);
            ValidateCommand.owner(sender);
            ValidateCommand.minArgs(1, args, USAGE);

            switch (args[0].toLowerCase()) {
                case "msg":
                    if (SpyCommandService.this.messageSpy.contains(player.getUniqueId())) {
                        SpyCommandService.this.messageSpy.remove(player.getUniqueId());
                        player.sendMessage(Settings.PREFIX + "MSG-Spy wurde §cdeaktiviert§7.");
                    } else {
                        SpyCommandService.this.messageSpy.add(player.getUniqueId());
                        player.sendMessage(Settings.PREFIX + "MSG-Spy wurde §aaktiviert§7.");
                    }
                    break;
                case "cmd":
                    if (SpyCommandService.this.commandSpy.contains(player.getUniqueId())) {
                        SpyCommandService.this.commandSpy.remove(player.getUniqueId());
                        player.sendMessage(Settings.PREFIX + "CMD-Spy wurde §cdeaktiviert§7.");
                    } else {
                        SpyCommandService.this.commandSpy.add(player.getUniqueId());
                        player.sendMessage(Settings.PREFIX + "CMD-Spy wurde §aaktiviert§7.");
                    }
                    break;
                default:
                    throw new InvalidArgsException(USAGE);
            }
        }
    }

}
