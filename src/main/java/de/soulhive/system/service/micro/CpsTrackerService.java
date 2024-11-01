package de.soulhive.system.service.micro;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.soulhive.system.SoulHive;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.npc.impl.ZombieHologramNpc;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.impl.HologramAppearanceTask;
import de.soulhive.system.util.nms.Title;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

@FeatureService
public class CpsTrackerService extends Service {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        null,
        10000
    );

    private Map<Player, Integer> clicks = new ConcurrentHashMap<>();
    private List<Hologram> holograms = new CopyOnWriteArrayList<>();
    private DelayService delayService;

    @Override
    public void initialize() {
        final ServiceManager serviceManager = SoulHive.getServiceManager();
        this.delayService = serviceManager.getService(DelayService.class);
        final NpcService npcService = serviceManager.getService(NpcService.class);

        npcService.addNpc(new ZombieHologramNpc(
            Settings.LOCATION_CPS,
            BlockFace.SOUTH_EAST,
            player -> player.sendMessage("§6§lCPS> §7Schlage mich, um deine Klicks pro Sekunde zu messen."),
            new CpsClickAction(),
            "§6§lCPS"
        ));
    }

    @Override
    public void disable() {
        this.holograms.forEach(Hologram::delete);
    }

    private class CpsClickAction implements BiConsumer<Player, CraftEntity> {

        private Map<Player, Integer> clicks = CpsTrackerService.this.clicks;

        @Override
        public void accept(final Player player, final CraftEntity craftZombie) {
            if (this.clicks.containsKey(player)) {
                final int clicks = CpsClickAction.this.clicks.get(player);
                final String cps = CpsTrackerService.this.calculateCps(clicks);

                new Title(
                    "§a" + clicks + " clicks",
                    "§b" + cps + " clicks/s",
                    0, 3, 0
                ).send(player);

                this.clicks.put(player, this.clicks.get(player) + 1);
                craftZombie.playEffect(EntityEffect.HURT);
                player.playSound(craftZombie.getLocation(), Sound.HURT_FLESH, 0.5F, 0.7F);
            } else {
                CpsTrackerService.this.delayService.handleDelay(player, DELAY_CONFIGURATION, clicker -> {
                    final String cps = CpsTrackerService.this.calculateCps(1);

                    craftZombie.playEffect(EntityEffect.HURT);
                    player.getWorld().playSound(craftZombie.getLocation(), Sound.HURT_FLESH, 0.1F, 0.1F);
                    this.clicks.put(player, 1);

                    new Title(
                        "§a1 click",
                        "§b" + cps + " clicks/s"
                        , 0, 3, 0
                    ).send(player);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            final String cps = CpsTrackerService.this.calculateCps(CpsClickAction.this.clicks.get(player));

                            Hologram hologram = HologramsAPI.createHologram(SoulHive.getPlugin(), Settings.LOCATION_CPS);
                            hologram.appendTextLine("§b" + cps + " §7clicks/s von §b" + player.getName());

                            CpsTrackerService.this.holograms.add(hologram);

                            new HologramAppearanceTask(hologram.getLocation().clone().add(0, 2, 0), hologram)
                                .runTaskTimer(SoulHive.getPlugin(), 0, 3);

                            player.sendMessage(Settings.PREFIX + "Du hast §f" + cps + " §7Klicks pro Sekunde erreicht.");
                            CpsClickAction.this.clicks.remove(player);
                        }
                    }.runTaskLater(SoulHive.getPlugin(), 20L * 5);
                });
            }
        }

    }

    private String calculateCps(final int clicks) {
        return String.valueOf(Math.round(((double) clicks / 5) * 100.0D) / 100.0D);
    }

}
