package de.soulhive.system.listener.impl;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.FontUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.playSound(player.getLocation(), Sound.LEVEL_UP, Float.MAX_VALUE, Float.MIN_VALUE);
        event.setJoinMessage(null);

        if (!player.hasPlayedBefore()) {
            player.teleport(Settings.LOCATION_SPAWN);
            Bukkit.getOnlinePlayers()
                .stream()
                .filter(onlinePlayer -> onlinePlayer != player)
                .forEach(onlinePlayer -> onlinePlayer.sendMessage(
                    Settings.PREFIX
                        + "§7Willkommen auf SoulHive," +
                        " §f" + player.getName() + "§7! "
                        + "§8[§f#§l" + Bukkit.getOfflinePlayers().length + "§8]"
                ));
        }

        FontUtils.sendCenteredMessage(player, "§8§m---------------------------------------------");
        player.sendMessage("");
        FontUtils.sendCenteredMessage(player, "§7Herzlich Willkommen auf §9§lSoulHive§7!");
        FontUtils.sendCenteredMessage(player, "§7SkyPvP im Einklang mit Handel & Wirtschaft.");
        player.sendMessage("");
        FontUtils.sendCenteredMessage(player, "§7Wichtige Befehle:");
        FontUtils.sendCenteredMessage(player, "§f/menu§8, §f/is§8, §f/kit§8, §f/vote");
        player.sendMessage("");
        FontUtils.sendCenteredMessage(player, "§8§m---------------------------------------------");
    }

}
