package de.soulhive.system.command.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.InvalidArgsException;
import de.soulhive.system.command.exception.impl.TargetNotFoundException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandJewelserver extends CommandExecutorWrapper {

    private static final String USAGE = "/jewelserver <add|remove|set> <name> <anzahl> [-s]";

    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.jewelserver");
        ValidateCommand.minArgs(3, args, USAGE);

        if (!Arrays.asList("add", "remove", "set").contains(args[0].toLowerCase())) {
            throw new InvalidArgsException(USAGE);
        }

        final User user = this.userService.getUserByName(args[1]);
        final int amount = ValidateCommand.number(args[2]);
        boolean silent = args.length > 3 && args[3].equalsIgnoreCase("-s");

        if (user == null) {
            throw new TargetNotFoundException(args[1]);
        }

        switch (args[0].toLowerCase()) {
            case "add":
                user.addJewels(amount);

                if (silent) {
                    sender.sendMessage(
                        Settings.PREFIX
                            + "Dem Spieler §f"
                            + user.getName()
                            + " §7wurden §f"
                            + amount
                            + " Juwelen §7hinzugefügt."
                    );
                } else {
                    Bukkit.broadcastMessage(
                        Settings.PREFIX
                            + "§f"
                            + user.getName()
                            + " §7wurden §f"
                            + amount
                            + " Juwelen §7hinzugefügt."
                    );
                    Bukkit.broadcastMessage(Settings.PREFIX + "Jetzt auch Juwelen kaufen: §d§l/buy");
                }
                break;
            case "remove":
                user.removeJewels(amount);
                sender.sendMessage(
                    Settings.PREFIX
                        + "§f"
                        + user.getName()
                        + " §7wurden §c"
                        + amount
                        + " §dJuwelen §7abgezogen."
                );
                break;
            case "set":
                user.setJewels(amount);
                sender.sendMessage(
                    Settings.PREFIX
                        + "§f"
                        + user.getName()
                        + " §7wurden die Juwelen auf §f" + amount + " §7gesetzt."
                );
                break;
        }

        this.userService.saveUser(user);
    }

}
