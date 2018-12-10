package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CommandDebug extends CommandExecutorWrapper {

    private static final String USAGE = "/debug f/s <targetRank> <rankToSet>";

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(2, args, USAGE);

        if (args[0].equalsIgnoreCase("f")) {
            Executors.newCachedThreadPool().execute(() -> {

                final Config fileStorage = new Config(Settings.CONFIG_PATH, args[1] + ".yml");
                final Config pexDatabase = new Config("plugins/PermissionsEx", "permissions.yml");

                final List<String> users = PermissionsEx.getPermissionManager().getGroup(args[1]).getUsers()
                    .stream()
                    .map(permissionUser -> {
                        final ConfigurationSection section = pexDatabase.getFileConfiguration().getConfigurationSection("users");

                        for (String pexUser : section.getKeys(false)) {
                            try {
                                final String string = section.getConfigurationSection(pexUser).getConfigurationSection("options").getString("name");

                                if (string.equalsIgnoreCase(permissionUser.getName())) {
                                    return pexUser;
                                }
                            } catch (NullPointerException e) {
                            }
                        }

                        return "error";
                    })
                    .filter(s -> !s.equalsIgnoreCase("error"))
                    .collect(Collectors.toList());

                fileStorage.set(args[0], users);
                fileStorage.saveFile();

                sender.sendMessage(Settings.PREFIX + "§f" + users.size() + " §7users extracted into §f" + args[0] + ".yml");
            });
        } else {
            final Config fileStorage = new Config(Settings.CONFIG_PATH, args[1] + ".yml");

            fileStorage.getStringList(args[1]).forEach(s -> Bukkit.dispatchCommand(sender, "pex user " + s + " group set " + args[2]));
        }
    }

}
