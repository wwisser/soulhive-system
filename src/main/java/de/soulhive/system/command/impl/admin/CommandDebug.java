package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CommandDebug extends CommandExecutorWrapper {

    private static final String USAGE = "/debug <rank>";

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(1, args, USAGE);

        PermissionsEx.getPermissionManager().getGroup(args[0]).getUsers().forEach(
            permissionUser -> sender.sendMessage(permissionUser.getName())
        );
    }

}
