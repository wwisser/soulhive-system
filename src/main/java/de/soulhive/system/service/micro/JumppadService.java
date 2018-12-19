package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@FeatureService
public class JumppadService extends Service implements Listener {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        null,
        500
    );

    private DelayService delayService;

    @Override
    public void initialize() {
        this.delayService = SoulHive.getServiceManager().getService(DelayService.class);
        super.registerListener(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (!player.getWorld().equals(Settings.WORLD_MAIN)
            || player.getLocation().add(0, -1, 0).getBlock().getType() != Material.SLIME_BLOCK) {
            return;
        }

        this.delayService.handleDelay(player, DELAY_CONFIGURATION, jumper -> {
            player.setVelocity(player.getLocation().getDirection().setY(0.3).multiply(2.5));
            player.playSound(player.getLocation(), Sound.SLIME_ATTACK, 1, 1);
            ActionBar.send("§a§lWoooosh!", player);
        });
    }

}
