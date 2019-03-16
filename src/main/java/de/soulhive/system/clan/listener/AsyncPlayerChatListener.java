package de.soulhive.system.clan.listener;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanLeague;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.util.RomanNumerals;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@AllArgsConstructor
public class AsyncPlayerChatListener implements Listener {

    private ClanService clanService;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        Clan clan = this.clanService.getLocalClanStorage().getClan(uuid);

        if (clan == null) {
            return;
        }

        ClanLeague.InfoMapper info = ClanLeague.getInfo(clan.getKills());

        if (info == null) {
            return;
        }

        String chatTag;

        ClanLeague league = info.getLeague();
        ChatColor chatColor = league.getChatColor();

        if (league == ClanLeague.NONE) {
            chatTag = chatColor + clan.getTag() + " §8* ";
        } else if (league == ClanLeague.COAL) {
            chatTag = chatColor + clan.getTag() + " " + RomanNumerals.toRoman(info.getLevel()) + " §7* §8";
        } else {
            chatTag = chatColor + clan.getTag() + " " + RomanNumerals.toRoman(info.getLevel()) + " §8* ";
        }

        event.setFormat(chatTag + event.getFormat());
    }

}
