package de.soulhive.system.service.micro;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

@FeatureService
public class DoublexpService extends Service implements Listener {

    private boolean doubleXpEnabled = false;

    @Override
    public void initialize() {
        super.registerCommand("doublexp", new CommandDoublexp());
        super.registerListener(this);
    }

    @EventHandler
    public void onPlayerExpChange(final PlayerExpChangeEvent event) {
        final int amount = event.getAmount();

        if (this.doubleXpEnabled) {
            event.setAmount(amount * 2);
        }
    }

    private class CommandDoublexp extends CommandExecutorWrapper {

        @Override
        public void process(CommandSender sender, String label, String[] args) throws CommandException {
            ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);

            boolean doubleXpEnabled = DoublexpService.this.doubleXpEnabled;

            Bukkit.broadcastMessage(Settings.PREFIX + "§6§lDoubleXP §7wurde " + (doubleXpEnabled ? "§cdeaktiviert" : "§aaktiviert"));

            DoublexpService.this.doubleXpEnabled ^= true;
        }

    }

}
