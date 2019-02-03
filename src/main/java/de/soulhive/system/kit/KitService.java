package de.soulhive.system.kit;

import de.soulhive.system.kit.command.CommandKit;
import de.soulhive.system.kit.listener.PlayerDeathListener;
import de.soulhive.system.kit.listener.PlayerRespawnListener;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.MillisecondsConverter;
import org.bukkit.entity.Player;

@FeatureService
public class KitService extends Service {

    private Config database = new Config(Settings.CONFIG_PATH, "kits.yml");

    @Override
    public void initialize() {
        super.registerCommand("kit", new CommandKit(this));
        super.registerListeners(
            new PlayerRespawnListener(),
            new PlayerDeathListener()
        );
    }

    @Override
    public void disable() {
        this.database.saveFile();
    }

    public boolean hasRecieved(final Player player) {
        if (this.database.contains(player.getUniqueId().toString())) {
            return System.currentTimeMillis() < this.database.getLong(player.getUniqueId().toString());
        }
        return false;
    }

    public void setRecieved(final Player player) {
        this.database.set(player.getUniqueId().toString(), System.currentTimeMillis() + (60000 * 60 * 12));
    }

    public String getTimeRemaining(final Player player) {
        final long remaining = this.database.getLong(player.getUniqueId().toString()) - System.currentTimeMillis();

        return MillisecondsConverter.convertToString(remaining);
    }

}
