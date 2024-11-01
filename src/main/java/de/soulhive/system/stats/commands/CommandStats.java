package de.soulhive.system.stats.commands;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.MillisecondsConverter;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class CommandStats extends CommandExecutorWrapper {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");

    private UserService userService;

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);
        User user = args.length > 0 ? this.userService.getUserByName(args[0]) : this.userService.getUser(player);

        if (user == null) {
            player.sendMessage(Settings.PREFIX + "§cDer Spieler '" + args[0] + "' konnte nicht gefunden werden.");
            return;
        }

        player.sendMessage(Settings.PREFIX + "Stats von §f" + user.getName());
        player.sendMessage(" §7Kills: §f" + user.getKills());
        player.sendMessage(" §7Tode: §f" + user.getDeaths());
        player.sendMessage(" §7KD/r: §f" + user.getKdr());
        player.sendMessage(" §7Juwelen: §f" + user.getJewels());
        player.sendMessage(" §7Spielzeit: §f" + (user.getPlaytime() / 60) + "h");
        player.sendMessage(" §7Votes: §f" + user.getVotes());
        player.sendMessage(" §7Registriert seit: §f" + DATE_FORMAT.format(new Date(user.getFirstSeen())));

        if (player.isOp()) {

            final Player targetPlayer = Bukkit.getPlayer(user.getName());
            String lastSeen;

            if (targetPlayer != null && targetPlayer.isOnline()) {
                lastSeen = "Jetzt";
            } else {
                long diff = System.currentTimeMillis() - user.getLastSeen();
                lastSeen = MillisecondsConverter.convertToString(diff);
            }

            player.sendMessage(" §7Zul. gesehen: §f" + lastSeen);
        }
    }

}
