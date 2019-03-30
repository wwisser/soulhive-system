package de.soulhive.system.vote.commands;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;

public class CommandVote extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.onlyPlayer(sender);

        sender.sendMessage(Settings.PREFIX + "Jetzt voten und Belohnung erhalten:");
        sender.sendMessage(" §fhttps://minecraft-server.eu/vote/index/20752/" + sender.getName());
        sender.sendMessage(" §cWichtig: §fDu musst dazu online sein!");
    }

}
