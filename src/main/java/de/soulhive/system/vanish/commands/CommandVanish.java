package de.soulhive.system.vanish.commands;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import de.soulhive.system.vanish.VanishService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public class CommandVanish implements CommandExecutor {

    private VanishService vanishService;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission(Settings.PERMISSION_TEAM)) {
            ActionBar.send(Settings.COMMAND_NO_PERMISSION, commandSender);
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Settings.COMMAND_ONLY_PLAYERS);
            return true;
        }

        Player player = (Player) commandSender;
        List<Player> vanishedPlayers = this.vanishService.getVanishedPlayers();

        if (vanishedPlayers.contains(player)) {
            vanishedPlayers.remove(player);
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(player));
            player.sendMessage(Settings.PREFIX + "Du hast den Vanish-Modus §cdeaktiviert§f!");
        } else {
            vanishedPlayers.add(player);
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(player));
            player.sendMessage(Settings.PREFIX + "Du hast den Vanish-Modus §aaktiviert§f!");
        }

        return true;
    }

}
