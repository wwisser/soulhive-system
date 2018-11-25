package de.soulhive.system.command.impl.essential;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.TargetNotFoundException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.vanish.VanishService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandMessage extends CommandExecutorWrapper {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Bitte warte noch %time, bis du erneut eine private Nachricht schreiben kannst.",
        2000
    );
    private static final String USAGE = "/msg <target> <message>";

    private Map<UUID, UUID> lastReplies = new HashMap<>();
    private DelayService delayService = SoulHive.getServiceManager().getService(DelayService.class);

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        VanishService vanishService = SoulHive.getServiceManager().getService(VanishService.class);

        ValidateCommand.minArgs(1, args, USAGE);
        Player player = ValidateCommand.onlyPlayer(sender);


        if (label.toLowerCase().startsWith("r")) {
            UUID uuid = this.lastReplies.get(player.getUniqueId());

            if (uuid != null) {
                Player target = Bukkit.getPlayer(uuid);

                if (target == null || !target.isOnline()) {
                    return;
                }

                if (vanishService.getVanishedPlayers().contains(target)
                    && !sender.hasPermission(Settings.PERMISSION_TEAM)) {
                    throw new TargetNotFoundException(target.getName());
                }

                String message = String.join(" ", args);
                if (sender.hasPermission(Settings.PERMISSION_TEAM)) {
                    this.sendMessage(player, target, message);
                } else {
                    this.delayService.handleDelay(player, DELAY_CONFIGURATION, messageSender ->
                        this.sendMessage(player, target, message)
                    );
                }
            } else {
                throw new CommandException("§cDu hast aktuell mit niemandem eine Konversation.");
            }

            return;
        }

        if (args.length > 1) {
            Player target = ValidateCommand.target(args[0], player);
            String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            if (vanishService.getVanishedPlayers().contains(target)
                && !sender.hasPermission(Settings.PERMISSION_TEAM)) {
                throw new TargetNotFoundException(target.getName());
            }

            if (sender.hasPermission(Settings.PERMISSION_TEAM)) {
                this.sendMessage(player, target, message);
            } else {
                this.delayService.handleDelay(player, DELAY_CONFIGURATION, messageSender ->
                    this.sendMessage(player, target, message)
                );
            }
        }
    }

    private void sendMessage(Player sender, Player target, String message) {
        target.sendMessage(Settings.PREFIX + "§aNachricht von §f" + sender.getName() + "§a: §f§o" + message);
        sender.sendMessage(Settings.PREFIX + "§aNachricht an §f" + target.getName() + "§a: §f§o" + message);

        this.lastReplies.put(sender.getUniqueId(), target.getUniqueId());
        this.lastReplies.put(target.getUniqueId(), sender.getUniqueId());
    }

}
