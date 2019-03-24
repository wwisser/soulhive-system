package de.soulhive.system.clan.models;

import de.soulhive.system.SoulHive;
import de.soulhive.system.vanish.VanishService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ClanMember {

    private String uuid;
    private int kills;
    private int deaths;
    @Nullable @Setter private Clan clan;
    @Nullable private Long joined;
    @Nullable private Boolean moderator;

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public boolean isOwner() {
        return Objects.requireNonNull(this.clan, "Clan is null").getOwner().equals(this.uuid);
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
