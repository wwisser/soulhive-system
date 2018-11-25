package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandSudo extends CommandExecutorWrapper {

    private static final String USAGE = "/sudo <player> <message>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(2, args, USAGE);

        Player target = ValidateCommand.target(args[0]);
        String message = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");

        target.chat(message);
        sender.sendMessage(Settings.PREFIX + "'§f" + message + "§7' für §f" + target.getName() + " §7ausgeführt.");
    }

}
