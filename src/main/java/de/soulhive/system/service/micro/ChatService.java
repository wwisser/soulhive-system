package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatService extends Service implements Listener {

    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Bitte warte noch %time.",
        1500
    );

    private DelayService delayService;

    @Override
    public void initialize() {
        this.delayService = SoulHive.getServiceManager().getService(DelayService.class);
        super.registerListener(this);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("soulhive.chat.bypass")) {
            return;
        }

        this.delayService.handleDelayInverted(player, DELAY_CONFIGURATION, sender -> {
            event.setCancelled(true);
            sender.sendMessage(Settings.PREFIX + "§cDu schreibst zu schnell.");
        });
    }

}
