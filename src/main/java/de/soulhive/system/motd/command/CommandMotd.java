package de.soulhive.system.motd.command;

import de.soulhive.system.motd.MotdService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@AllArgsConstructor
public class CommandMotd implements CommandExecutor {

    private static final String USAGE = Settings.COMMAND_USAGE + "/motd <reload|header|footer> <Text>";

    private final MotdService motdService;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission("soulhive.motd")) {
            ActionBar.send(Settings.COMMAND_NO_PERMISSION, commandSender);
            return true;
        }

        if (args.length < 1) {
            commandSender.sendMessage(USAGE);
            return true;
        }

        String argument = args[0].toLowerCase();

        switch (argument) {
            case "header":
                this.motdService.updateHeader(this.joinText(args));
                break;
            case "footer":
                this.motdService.updateFooter(this.joinText(args));
                break;
            case "reload":
                this.motdService.reloadConfig();
                break;
            default:
                commandSender.sendMessage(Settings.PREFIX + USAGE);
                return true;
        }

        commandSender.sendMessage(Settings.PREFIX + "Du hast die §fMOTD §7erfolgreich aktualisiert");
        commandSender.sendMessage("");
        commandSender.sendMessage("§7" + this.motdService.fetchHeader());
        commandSender.sendMessage("§7" + this.motdService.fetchFooter());

        return true;
    }

    private String joinText(String[] args) {
        return ChatColor.translateAlternateColorCodes(
            '&',
            String.join(
                " ", Arrays.copyOfRange(args, 1, args.length)
            )
        );
    }

}
