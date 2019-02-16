package de.soulhive.system.command.impl.admin;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetstats extends CommandExecutorWrapper {

    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(2, args, "/setstats <player> <deaths>");

        final Player target = ValidateCommand.targetOrSelf(args[0]);
        final int amount = ValidateCommand.amount(args[1]);
        final User user = this.userService.getUser(target);

        user.setDeaths(amount);
        this.userService.saveUser(user);
    }

}
