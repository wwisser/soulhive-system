package de.soulhive.system.clan.listener;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.models.ClanMember;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private ClanService clanService;

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        ClanMember clanMember = this.clanService.getLocalClanStorage().getClanMember(
            event.getPlayer().getUniqueId().toString()
        );

        if (clanMember != null)
            this.clanService.getLocalClanStorage().saveAndUnload(clanMember);
    }

}
