package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class ChatService extends Service implements Listener {

    private static final String PREFIX_FORMAT = "§8[%s§8]";
    private static final int MESSAGE_LENGTH_IGNORE_REPEAT = 3;
    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        "Bitte warte noch %time.",
        1500
    );

    private Map<Player, Deque<String>> sentMessages = new HashMap<>();

    private DelayService delayService;
    private Chat vaultChat;

    @Override
    public void initialize() {
        super.registerListener(this);
        this.delayService = SoulHive.getServiceManager().getService(DelayService.class);
        this.vaultChat = Bukkit.getServer().getServicesManager().getRegistration(Chat.class).getProvider();
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        final String formattedMessage = this.fetchPrefix(player)
            + " §7"
            + player.getName()
            + "§8: §r"
            + this.formatMessage(player, message);

        event.setFormat(formattedMessage);

        if (player.hasPermission("soulhive.chat.bypass")) {
            return;
        }

        this.delayService.handleDelayInverted(player, DELAY_CONFIGURATION, sender -> {
            event.setCancelled(true);
            sender.sendMessage(Settings.PREFIX + "§cDu schreibst zu schnell.");
        });

        if (event.isCancelled() || message.length() <= MESSAGE_LENGTH_IGNORE_REPEAT) {
            return;
        }

        final Deque<String> messages = this.sentMessages.getOrDefault(player, new ArrayDeque<>());

        if (messages.stream().anyMatch(message::equalsIgnoreCase)) {
            if (!event.isCancelled()) {
                player.sendMessage(formattedMessage);
            }
            event.setCancelled(true);
            return;
        }

        messages.addLast(message);

        if (messages.size() > MESSAGE_LENGTH_IGNORE_REPEAT) {
            messages.removeFirst();
        }

        this.sentMessages.put(player, messages);
    }

    private String fetchPrefix(final Player player) {
        final String playerPrefix = ChatColor.translateAlternateColorCodes(
            '&', this.vaultChat.getPlayerPrefix(player)
        );

        return String.format(PREFIX_FORMAT, playerPrefix);
    }

    private String formatMessage(final Player sender, final String message) {
        String formattedMessage = message;

        if (sender.hasPermission("soulhive.chat.color")) {
            formattedMessage = ChatColor.translateAlternateColorCodes('&', formattedMessage);
        }

        return formattedMessage.replace("%", "%%");
    }

}
