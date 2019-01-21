package de.soulhive.system.particle;

import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.entity.Player;

import java.util.Optional;

@FeatureService
public class ParticleService extends Service {

    private Config database = new Config(Settings.CONFIG_PATH, "particles.yml");

    @Override
    public void disable() {
        this.database.saveFile();
    }

    public Optional<Particle> getSelectedParticle(final Player player) {
        final String uuid = player.getUniqueId().toString();

        if (this.database.contains(uuid)) {
            return Optional.of(Particle.values()[this.database.getInt(uuid)]);
        }

        return Optional.empty();
    }

    public void setSelectedParticle(final Player player, final Particle particle) {
        this.database.set(player.getUniqueId().toString(), particle.ordinal());
    }

}
