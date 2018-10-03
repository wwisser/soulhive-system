package de.soulhive.system.stats.commands;

import de.soulhive.system.SoulHive;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class CommandStats implements CommandExecutor {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");

    private UserService userService;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Dieser Befehl ist nur für Spieler");
            return true;
        }

        Player player = (Player) commandSender;
        User user = args.length > 0 ?this.userService.getUserByName(args[0]) : this.userService.getUser(player);

        if (user == null) {
            player.sendMessage(Settings.PREFIX + "§cDer Spieler '" + args[0] + "' konnte nicht gefunden werden.");
            return true;
        }

        player.sendMessage(Settings.PREFIX + "Stats von §f" + user.getName());
        player.sendMessage(" §7Kills: §f" + user.getKills());
        player.sendMessage(" §7Tode: §f" + user.getDeaths());
        player.sendMessage(" §7Juwelen: §f" + user.getJewels());
        player.sendMessage(" §7Spielzeit: §f" + (user.getPlaytime() / 60) + "h");
        player.sendMessage(" §7Votes: §f" + user.getVotes());
        player.sendMessage(" §7Registriert seit: §f" + DATE_FORMAT.format(new Date(user.getFirstSeen())));

        return true;
    }

}
