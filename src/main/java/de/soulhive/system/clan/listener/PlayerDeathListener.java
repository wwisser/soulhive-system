package de.soulhive.system.clan.listener;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.storage.ClanStorage;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private ClanService clanService;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        ClanStorage localClanStorage = this.clanService.getLocalClanStorage();
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        ClanMember clanMember = localClanStorage.getClanMember(victim.getUniqueId().toString());

        if (clanMember != null && clanMember.getClan() != null) {
            clanMember.addDeath();
            clanMember.getClan().addDeath();
        }

        if (killer == null && this.clanService.getLastHits().containsKey(victim)) {
            killer = this.clanService.getLastHits().get(victim);
            this.clanService.getLastHits().remove(victim);
        }

        if (killer != null && killer != victim) {
            ClanMember killerClanMember = localClanStorage.getClanMember(killer.getUniqueId().toString());

            if (killerClanMember != null && killerClanMember.getClan() != null) {
                Clan clan = killerClanMember.getClan();
                final String uuid = killer.getUniqueId().toString();
                final String name = killer.getName();
                final String victimName = victim.getName();

                killerClanMember.addKill();
                clan.addKill();

                clan.getMembers()
                    .stream()
                    .filter(member -> !member.equals(uuid))
                    .map(localClanStorage::getClanMember)
                    .filter(ClanMember::isOnline)
                    .map(ClanMember::toPlayer)
                    .forEach(player -> player.sendMessage(Settings.PREFIX + "Clanmitglied §6" + name + " §7hat §a" + victimName + " §7getötet."));
            }
        }

        this.clanService.getLastHits().remove(victim);
    }

}
