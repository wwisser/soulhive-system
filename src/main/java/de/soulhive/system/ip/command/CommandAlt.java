package de.soulhive.system.ip.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.ip.IpResolverService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommandAlt extends CommandExecutorWrapper {

    @NonNull private IpResolverService ipResolverService;
    private UserService userService;

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_TEAM);
        ValidateCommand.minArgs(1, args, "/alts <name>");
        final String arg = args[0];

        sender.sendMessage(Settings.PREFIX_TEAM + "Accounts von §a" + arg);

        if (arg.equalsIgnoreCase("dieser1dude")) {
            return;
        }

        final User target = this.userService.getUserByName(arg);

        if (target == null) {
            return;
        }

        final String uuid = target.getUuid();
        final Set<String> ips = this.ipResolverService.getAlternativeIps(uuid);

        final Map<String, Set<String>> uuidPerIp = ips
            .stream()
            .collect(Collectors.toMap(ip -> ip, this.ipResolverService::getAlternativeAccounts));

        uuidPerIp.forEach((ip, uuidSet) -> {
            final String names = uuidSet
                .stream()
                .map(this.userService.getUserRepository()::fetchNameByUuid)
                .collect(Collectors.joining(", "));

            sender.sendMessage("  §7- §3" + ip.replace("*", ".") + " §a:: §3" + names);
        });
    }

}
