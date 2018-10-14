package de.soulhive.system.command.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CommandDebug implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String rank = args[0];

        PermissionsEx.getPermissionManager().getGroup(rank).getUsers().forEach(permissionUser -> {
            commandSender.sendMessage(permissionUser.getName());
        });

        return true;
    }

}
