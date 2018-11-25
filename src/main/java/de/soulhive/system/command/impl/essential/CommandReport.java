package de.soulhive.system.command.impl.essential;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.text.TextComponentUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandReport extends CommandExecutorWrapper {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Bitte warte noch %time, um erneut einen Spieler zu melden.",
        60000
    );
    private static final List<String> REPORT_REASONS = Arrays.asList(
        "HACKING", "SKIN", "BUGUSING", "LAGVERURSACHUNG"
    );
    private static final String USAGE = "/report <target> <" + String.join("|", REPORT_REASONS) + ">";

    private DelayService delayService = SoulHive.getServiceManager().getService(DelayService.class);

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        Player player = ValidateCommand.onlyPlayer(sender);
        ValidateCommand.minArgs(2, args, USAGE);
        Player target = ValidateCommand.target(args[0]);

        if (target.hasPermission(Settings.PERMISSION_TEAM) || target.hasPermission(Settings.PERMISSION_ADMIN)) {
            throw new CommandException("§cDu darfst keine Teammitglieder melden.");
        }

        String reason = args[1].toUpperCase();

        if (REPORT_REASONS
            .stream()
            .noneMatch(validReason -> validReason.equals(reason))) {
            player.sendMessage(Settings.COMMAND_USAGE + USAGE);
            return;
        }

        this.delayService.handleDelay(player, DELAY_CONFIGURATION, reporter -> {
            String message = Settings.PREFIX
                + "§4"
                + target.getName()
                + " §c("
                + reason
                + ") §4von "
                + reporter.getName();
            String hoverText = "§bKlicke, um Vanish zu aktivieren und dich zum Spieler zu teleportieren";
            String command = "/spec " + target.getName();
            TextComponent textComponent = TextComponentUtils.createClickableComponent(message, hoverText, command);

            Bukkit.getOnlinePlayers()
                .stream()
                .filter(onlinePlayer -> onlinePlayer.hasPermission("soulhive.report.see"))
                .forEach(teamMember -> teamMember.spigot().sendMessage(textComponent));

            player.sendMessage(
                Settings.PREFIX + "Du hast §f" + target.getName() + " §7für §f" + reason + " §7gemeldet."
            );
            player.sendMessage(
                Settings.PREFIX + "Vielen Dank, wir werden uns schnellstmöglich darum kümmern."
            );
        });
    }

}
