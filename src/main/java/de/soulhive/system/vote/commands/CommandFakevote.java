package de.soulhive.system.vote.commands;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandFakevote extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(1, args, "/fakevote <player>");

        sender.sendMessage(Settings.PREFIX + "Sende Fakevote...");

        final Vote vote = new Vote();
        vote.setUsername(args[0]);
        vote.setAddress("127.0.0.1");

        Bukkit.getPluginManager().callEvent(new VotifierEvent(vote));
    }

}
