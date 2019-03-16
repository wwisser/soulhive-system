package de.soulhive.system.clan.models;

import de.soulhive.system.SoulHive;
import de.soulhive.system.vanish.VanishService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ClanMember {

    private String uuid;
    private int kills;
    private int deaths;
    @Nullable private Long joined;
    @Nullable private Clan clan;

    public boolean hasClan() {
        return this.clan != null;
    }

    public boolean isOnline() {
        Player player = Bukkit.getPlayer(UUID.fromString(this.uuid));
        VanishService vanishService = SoulHive.getServiceManager().getService(VanishService.class);

        return player != null
            && player.isOnline()
            && !vanishService.getVanishedPlayers().contains(player);
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(UUID.fromString(this.uuid));
    }

}
