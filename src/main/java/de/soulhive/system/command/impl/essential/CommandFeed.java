package de.soulhive.system.command.impl.essential;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.setting.Settings;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class CommandFeed extends CommandExecutorWrapper {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Bitte warte noch %time, um dich erneut zu sättigen.",
        60000
    );
    private static final Consumer<Player> ACTION = player -> {
        player.setFoodLevel(20);
        player.playSound(player.getLocation(), Sound.BURP, 1, 1);
        player.sendMessage(Settings.PREFIX + "Du wurdest §fgesättigt§7!");
    };

    private DelayService delayService = SoulHive.getServiceManager().getService(DelayService.class);

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.feed");
        Player player = ValidateCommand.onlyPlayer(sender);

        if (player.hasPermission(Settings.PERMISSION_ADMIN)) {
            ACTION.accept(player);
            return;
        }

        this.delayService.handleDelay(player, DELAY_CONFIGURATION, ACTION);
    }

}
