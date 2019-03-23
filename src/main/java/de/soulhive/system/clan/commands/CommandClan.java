package de.soulhive.system.clan.commands;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.commands.subcommands.CommandCreate;
import de.soulhive.system.clan.commands.subcommands.CommandDisband;
import de.soulhive.system.clan.commands.subcommands.CommandLeagues;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandClan extends CommandExecutorWrapper {

    private List<ClanCommand> subCommands;

    public CommandClan(ClanService clanService) {
        this.subCommands = Arrays.asList(
            new CommandLeagues(),
            new CommandCreate(clanService),
            new CommandDisband(clanService)
        );
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);

        if (args.length == 0) {
            this.sendHelp(player);
        } else {
            for (ClanCommand subCommand : this.subCommands) {
                if (subCommand.getArgument().equalsIgnoreCase(args[0]) && !subCommand.process(player, args)) {
                    this.sendHelp(player);
                }
            }
        }
    }

    private void sendHelp(final Player player) {
        player.sendMessage(Settings.PREFIX + "Clansystem - Hilfe");
        player.sendMessage(" §8> §7Clan erstellen §8- §f/clan create <tag> <name>");
        player.sendMessage(" §8> §7Clan auflösen §8- §f/clan disband");
        player.sendMessage(" §8> §7Clan verlassen §8 - §f/clan leave");
        player.sendMessage(" §8> §7Spieler einladen §8- §f/clan invite <name>");
        player.sendMessage(" §8> §7Einladung annehmen §8 - §f/clan accept <tag>");
        player.sendMessage(" §8> §7Spieler kicken §8- §f/clan kick <name>");
        player.sendMessage(" §8> §7Spieler befördern §8- §f/clan promote <name>");
        player.sendMessage(" §8> §7Spieler degradieren §8- §f/clan demote <name>");
        player.sendMessage(" §8> §7Clan upgraden §8- §f/clan upgrade");
        player.sendMessage(" §8> §7Clanstats anzeigen §8- §f/clan stats [name|clantag]");
        player.sendMessage(" §8> §7Ligen anzeigen §8- §f/clan leagues");
        player.sendMessage(" §8> §7Clanjuwelen (Anzeigen/Einzahlen) §8- §f/clan bank [add] [amount]");
    }

}
