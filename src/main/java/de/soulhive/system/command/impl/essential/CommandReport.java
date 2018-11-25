package de.soulhive.system.command.impl.essential;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.setting.Settings;
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

        String reason = args[1];

        if (REPORT_REASONS
            .stream()
            .noneMatch(validReason -> validReason.equalsIgnoreCase(reason))) {
            player.sendMessage(Settings.COMMAND_USAGE + USAGE);
            return;
        }

        this.delayService.handleDelay(player, DELAY_CONFIGURATION, reporter -> {
        });
    }

}
