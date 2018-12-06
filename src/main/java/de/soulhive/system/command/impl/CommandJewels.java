package de.soulhive.system.command.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJewels extends CommandExecutorWrapper {

    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        if (args.length < 1) {
            final User user = this.userService.getUser(player);

            player.sendMessage(Settings.PREFIX + "§dJuwelen §7System");
            player.sendMessage(" §7Deine Juwelen: §f" + user.getJewels());
            player.sendMessage(" §7Juwelen transferieren: §f/j pay <target> <amount>");
            return;
        }
    }

}
