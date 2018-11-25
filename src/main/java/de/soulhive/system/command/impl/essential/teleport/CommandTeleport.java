package de.soulhive.system.command.impl.essential.teleport;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.InvalidArgsException;
import de.soulhive.system.command.exception.impl.TargetNotFoundException;
import de.soulhive.system.command.impl.essential.teleport.TeleportRequest;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.scheduled.ScheduledTeleport;
import de.soulhive.system.setting.Settings;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CommandTeleport extends CommandExecutorWrapper {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Du musst noch %time warten, um eine neue Anfrage senden zu können.",
        75000
    );
    private static final BiConsumer<Player, Boolean> TELEPORT_ACTION = (player, success) -> {
        if (success) {
            player.sendMessage(Settings.PREFIX + "Du wurdest erfolgreich §fteleportiert§7.");
        } else {
            player.sendMessage(Settings.PREFIX + "§cDu hast dich bewegt - Teleportation abgebrochen.");
        }
    };

    private Map<UUID, TeleportRequest> teleportRequests = new HashMap<>();
    private DelayService delayService = SoulHive.getServiceManager().getService(DelayService.class);

    @Override
    @SneakyThrows
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);

        if (label.equalsIgnoreCase("teleport") || label.equalsIgnoreCase("tp")) {
            ValidateCommand.permission(player, "soulhive.tp");
            ValidateCommand.minArgs(1, args, "/tp <target>");
            Player target = ValidateCommand.target(args[0], player);

            player.teleport(target);
            player.sendMessage(Settings.PREFIX + "Du wurdest zu §f" + target.getName() + " §7teleportiert!");
        }

        if (label.equalsIgnoreCase("tpa")) {
            ValidateCommand.permission(player, "soulhive.tpa");
            ValidateCommand.minArgs(1, args, "/tp <target>");
            Player target = ValidateCommand.target(args[0], player);

            this.delayService.handleDelay(player, DELAY_CONFIGURATION, requester -> {
                this.teleportRequests.put(
                    target.getUniqueId(),
                    new TeleportRequest(player, target, TeleportRequest.RequestType.TPA)
                );

                target.sendMessage(
                    Settings.PREFIX + "§f" + requester.getName() + " §7möchte sich zu dir teleportieren."
                );
                target.sendMessage(Settings.PREFIX + "Nutze §f/tpaccept§7, um die Anfrage zu akzeptieren.");
                player.sendMessage(Settings.PREFIX + "§7Du hast §f" + target.getName() + " §7eine TPA geschickt.");
            });
        }

        if (label.equalsIgnoreCase("tpahere")
            || label.equalsIgnoreCase("tphere")
            || label.equalsIgnoreCase("s")) {
            ValidateCommand.minArgs(1, args, "/tpahere <target>");
            Player target = ValidateCommand.target(args[0], player);

            if (sender.hasPermission("soulhive.tp")) {
                target.teleport(player);
                player.sendMessage(Settings.PREFIX + "Du hast §f" + target.getName() + " §7zu dir teleportiert.");
                target.sendMessage(Settings.PREFIX + "Du wurdest zu §f" + player.getName() + " §7teleportiert.");
                return;
            }

            ValidateCommand.permission(sender, "soulhive.tpahere");

            this.delayService.handleDelay(player, DELAY_CONFIGURATION, requester -> {
                this.teleportRequests.put(
                    target.getUniqueId(),
                    new TeleportRequest(player, target, TeleportRequest.RequestType.TPAHERE)
                );

                target.sendMessage(
                    Settings.PREFIX + "§f" + requester.getName() + " §7möchte, dass du dich zu ihm teleportierst."
                );
                target.sendMessage(Settings.PREFIX + "Nutze §f/tpaccept§7, um die Anfrage zu akzeptieren.");
                player.sendMessage(
                    Settings.PREFIX + "§7Du hast §f" + target.getName() + " §7eine TPA-Here geschickt."
                );
            });
        }

        if (label.equalsIgnoreCase("tpaccept")) {
            if (!this.teleportRequests.containsKey(player.getUniqueId())) {
                throw new CommandException("Du hast aktuell keine Teleportanfragen.");
            }

            TeleportRequest teleportRequest = this.teleportRequests.get(player.getUniqueId());
            Player requester = teleportRequest.getSender();

            if (requester == null || !requester.isOnline()) {
                throw new TargetNotFoundException(requester == null ? "Unbekannt" : requester.getName());
            }

            switch (teleportRequest.getRequestType()) {
                case TPA:
                    new ScheduledTeleport(
                        player.getLocation(),
                        3,
                        TELEPORT_ACTION
                    ).process(requester);
                    requester.sendMessage(
                        Settings.PREFIX + "Du wirst in §f3 Sekunden §7teleportiert. Bewege dich nicht."
                    );
                    break;
                case TPAHERE:
                    new ScheduledTeleport(
                        requester.getLocation(),
                        3,
                        TELEPORT_ACTION
                    ).process(player);
                    player.sendMessage(
                        Settings.PREFIX + "Du wirst in §f3 Sekunden §7teleportiert. Bewege dich nicht."
                    );
                    break;
            }
        }
    }

}
