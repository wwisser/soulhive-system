package de.soulhive.system.motd.commands;

import de.soulhive.system.SoulHive;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.util.nms.ActionBar;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@AllArgsConstructor
public class CommandMotd implements CommandExecutor {

    private static final String USAGE = SoulHive.COMMAND_USAGE + "/motd <header|footer> <Text>";

    private final MotdService motdService;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission("soulhive.motd")) {
            ActionBar.send(SoulHive.NO_PERMISSION, commandSender);
            return false;
        }

        if (args.length < 2) {
            commandSender.sendMessage(USAGE);
            return true;
        }

        String location = args[0].toLowerCase();
        String text = ChatColor.translateAlternateColorCodes(
            '&',
            String.join(
                " ", Arrays.copyOfRange(args, 1, args.length)
            )
        );

        switch (location) {
            case "header":
                this.motdService.updateHeader(text);
                break;
            case "footer":
                this.motdService.updateFooter(text);
                break;
            default:
                commandSender.sendMessage(USAGE);
                return true;
        }

        commandSender.sendMessage(SoulHive.PREFIX + "Du hast die §fMOTD §7erfolgreich geändert");
        commandSender.sendMessage("");
        commandSender.sendMessage("§7" + this.motdService.fetchHeader());
        commandSender.sendMessage("§7" + this.motdService.fetchFooter());

        return true;
    }

}
