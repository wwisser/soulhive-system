package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Set;

public class CommandDebug extends CommandExecutorWrapper {

    private static final String USAGE = "/debug <rank>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(1, args, USAGE);

        final Config fileStorage = new Config(Settings.CONFIG_PATH, args[0] + ".yml");
        final Set<PermissionUser> users = PermissionsEx.getPermissionManager().getGroup(args[0]).getUsers();

        fileStorage.set(args[0], users);
        fileStorage.saveFile();

        sender.sendMessage(Settings.PREFIX + "§f" + users.size() + " §7users extracted into §f" + args[0] + "§7.");
    }

}
