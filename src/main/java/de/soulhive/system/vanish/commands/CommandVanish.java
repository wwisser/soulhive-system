package de.soulhive.system.vanish.commands;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import de.soulhive.system.vanish.VanishService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public class CommandVanish extends CommandExecutorWrapper {

    private VanishService vanishService;

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.vanish");

        Player player = ValidateCommand.onlyPlayer(sender);
        List<Player> vanishedPlayers = this.vanishService.getVanishedPlayers();

        if (vanishedPlayers.contains(player)) {
            vanishedPlayers.remove(player);
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(player));
            player.sendMessage(Settings.PREFIX + "Du hast den Vanish-Modus §cdeaktiviert§f!");
        } else {
            vanishedPlayers.add(player);
            Bukkit.getOnlinePlayers()
                .stream()
                .filter(onlinePlayer -> !onlinePlayer.hasPermission(Settings.PERMISSION_TEAM))
                .filter(onlinePlayer -> onlinePlayer != player)
                .forEach(onlinePlayer -> onlinePlayer.hidePlayer(player));
            player.sendMessage(Settings.PREFIX + "Du hast den Vanish-Modus §aaktiviert§f!");
        }
    }

}
