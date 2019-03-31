package de.soulhive.system.motd.command;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@AllArgsConstructor
public class CommandMotd extends CommandExecutorWrapper {

    private static final String USAGE = "/motd <reload|header|footer> <Text>";

    private final MotdService motdService;

    @Override
    public void process(CommandSender commandSender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(commandSender, "soulhive.motd");

        if (args.length < 1) {
            commandSender.sendMessage(Settings.PREFIX + USAGE);
            commandSender.sendMessage("");
            this.showMotd(commandSender);
            return;
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
                return;
        }

        commandSender.sendMessage(Settings.PREFIX + "Du hast die §fMOTD §7erfolgreich aktualisiert");
        commandSender.sendMessage("");
        this.showMotd(commandSender);
    }

    private void showMotd(CommandSender sender) {
        sender.sendMessage("§7" + this.motdService.fetchHeader());
        sender.sendMessage("§7" + this.motdService.fetchFooter());
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
