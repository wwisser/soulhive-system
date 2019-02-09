package de.soulhive.system.peace.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.exception.impl.SelfInteractionException;
import de.soulhive.system.command.exception.impl.TargetNotFoundException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.peace.PeaceService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class CommandPeace extends CommandExecutorWrapper {

    @NonNull private PeaceService peaceService;
    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);
        final String uuid = player.getUniqueId().toString();

        if (args.length == 0) {
            final List<String> peaceList = this.peaceService.getPeaceList(uuid);

            if (peaceList.isEmpty()) {
                player.sendMessage(Settings.PREFIX + "§cDu hast mit niemandem Frieden geschlossen.");
            } else {
                player.sendMessage(Settings.PREFIX + "Du hast mit §f" + peaceList.size() + " §7Spielern Frieden geschlossen.");
            }

            for (String peaceUuid : peaceList) {
                player.sendMessage(" §8- §f" + this.userService.getUserByUuid(peaceUuid).getName());
            }

            player.sendMessage(" §7Schließe/Beende Frieden mit §f/friede <Name>");
        } else if (args.length == 1) {
            final User user = this.userService.getUserByName(args[0]);

            if (user == null) {
                throw new TargetNotFoundException(args[0]);
            } else if (user.getName().equals(player.getName())) {
                throw new SelfInteractionException();
            }

            final Player target = Bukkit.getPlayer(user.getName());

            if (this.peaceService.hasPeace(uuid, user.getUuid())) {
                this.peaceService.removePeace(uuid, user.getUuid());

                player.sendMessage(Settings.PREFIX + "§cDu hast den Frieden mit §f" + user.getName() + " §caufgelöst.");

                if (target != null && target.isOnline()) {
                    target.sendMessage(Settings.PREFIX + "§f" + player.getName() + " §chat den Frieden mit dir aufgelöst.");
                }
            } else {
                if (target == null || !target.isOnline()) {
                    throw new TargetNotFoundException(user.getName());
                }

                if (this.peaceService.hasInvite(uuid, user.getUuid())) {
                    this.peaceService.removeInvite(uuid, target.getUniqueId().toString());
                    this.peaceService.setPeace(uuid, target.getUniqueId().toString());
                    Bukkit.broadcastMessage(Settings.PREFIX + "§f" + player.getName() + " §7hat mit §f" + target.getName() + " §7Frieden geschlossen.");
                    return;
                }

                if (this.peaceService.hasInvite(user.getUuid(), uuid)) {
                    player.sendMessage(Settings.PREFIX + "§cDu hast diesem Spieler bereits Frieden angeboten.");
                    return;
                }

                this.peaceService.setInvite(user.getUuid(), uuid);
                player.sendMessage(Settings.PREFIX + "Du hast §f" + target.getName() + " §7Frieden angeboten.");
                target.sendMessage(Settings.PREFIX + "§f" + player.getName() + " §7bietet dir Frieden an!");
                target.sendMessage(" §7Verwende §f/friede accept " + player.getName() + "§7, um anzunehmen.");
            }
        } else if (args[0].equalsIgnoreCase("accept")) {
            final Player target = ValidateCommand.target(args[1], sender);

            if (this.peaceService.hasInvite(uuid, target.getUniqueId().toString())) {
                this.peaceService.removeInvite(uuid, target.getUniqueId().toString());
                this.peaceService.setPeace(uuid, target.getUniqueId().toString());
                Bukkit.broadcastMessage(Settings.PREFIX + "§f" + player.getName() + " §7hat mit §f" + target.getName() + " §7Frieden geschlossen.");
            } else {
                player.sendMessage(Settings.PREFIX + "§cDu hast keine Anfrage von §f" + target.getName() + " §cbekommen.");
            }
        }
    }

}
