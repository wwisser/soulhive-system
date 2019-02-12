package de.soulhive.system.task.impl;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class HologramAppearanceTask extends BukkitRunnable {

    @NonNull @Getter private Location location;
    @NonNull @Getter private Hologram hologram;
    private double count = 0;

    @Override
    public void run() {
        this.count += 0.01;
        if (this.count < 0.75) {
            this.hologram.teleport(this.location.add(0, this.count, 0));
            this.location.getWorld().playEffect(this.location.add(0, this.count, 0), Effect.WITCH_MAGIC, 10);
        } else {
            this.hologram.delete();
            super.cancel();
        }
    }

}
