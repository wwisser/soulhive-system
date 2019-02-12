package de.soulhive.system.command.impl.admin;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.service.micro.BossTitleService;
import de.soulhive.system.setting.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandBossbar extends CommandExecutorWrapper {

    private BossTitleService bossTitleService;

    @Override
    public void initialize() {
        this.bossTitleService = SoulHive.getServiceManager().getService(BossTitleService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        ValidateCommand.minArgs(2, args, "/bossbar <player> <message> [seconds]");

        final Player target = ValidateCommand.targetOrSelf(args[0]);
        String message = ChatColor.translateAlternateColorCodes('&', args[1]).replace("_", " ");

        this.bossTitleService.addPlayer(target, message);

        if (args.length > 2) {
            final int amount = ValidateCommand.amount(args[2]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    CommandBossbar.this.bossTitleService.removePlayer(target);
                }
            }.runTaskLater(SoulHive.getPlugin(), 20L * amount);

            sender.sendMessage(Settings.PREFIX + "§f" + target.getName() + " §7für §f" + amount + "s§7: §r" + message);
        } else {
            sender.sendMessage(Settings.PREFIX + "§f" + target.getName() + " §7gesetzt: §f" + message);
        }
    }

}
