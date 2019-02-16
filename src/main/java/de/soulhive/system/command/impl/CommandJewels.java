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
import org.bukkit.entity.Player;

public class CommandJewels extends CommandExecutorWrapper {

    private static final String USAGE = "/juwelen pay <target> <amount>";

    private UserService userService;

    @Override
    protected void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        if (args.length < 1) {
            final User user = this.userService.getUser(player);

            player.sendMessage(Settings.PREFIX + "§dJuwelen §7System");
            player.sendMessage(" §7Deine Juwelen: §f" + user.getJewels());
            player.sendMessage(" §7Transferieren: §f/juwelen pay <target> <amount>");
            player.sendMessage(" §7Shop: §f/shop");
            player.sendMessage(" §7Juwelen kaufen: §f/buy");
            return;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("pay")) {
            throw new InvalidArgsException(USAGE);
        }

        final User user = this.userService.getUser(player);
        User targetUser;

        try {
            targetUser = this.userService.getUser(ValidateCommand.target(args[1], sender));
        } catch (TargetNotFoundException e) {
            final User userByName = this.userService.getUserByName(args[1]);

            if (userByName != null) {
                targetUser = this.userService.getUserByName(args[1]);
            } else {
                throw e;
            }
        }

        final int amount = ValidateCommand.amount(args[2]);
        final boolean success = this.userService.getUserHelper().handleTransaction(user, targetUser, amount);

        if (success) {
            player.sendMessage(
                Settings.PREFIX
                    + "Du hast §f"
                    + targetUser.getName()
                    + " §d" + amount + " Juwelen §7überwiesen."
            );

            final Player targetPlayer = Bukkit.getPlayer(targetUser.getName());

            if (targetPlayer != null && targetPlayer.isOnline()) {
                targetPlayer.sendMessage(
                    Settings.PREFIX
                        + "Du hast §d"
                        + amount
                        + " Juwelen §7von §f"
                        + player.getName()
                        + " §7erhalten!"
                );
            }
        } else {
            player.sendMessage(
                Settings.PREFIX
                    + "Dir fehlen "
                    + (amount - user.getJewels())
                    + " Juwelen, um diese Transaktion durchzuführen."
            );
            player.sendMessage(Settings.PREFIX + "Jetzt §d§lJuwelen §7kaufen §8> §f/buy");
        }
    }

}
