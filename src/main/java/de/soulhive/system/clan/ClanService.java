package de.soulhive.system.clan;

import de.soulhive.system.clan.commands.CommandClan;
import de.soulhive.system.clan.listener.AsyncPlayerChatListener;
import de.soulhive.system.clan.listener.EntityDamageByEntityListener;
import de.soulhive.system.clan.listener.PlayerDeathListener;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.storage.ClanStorage;
import de.soulhive.system.clan.storage.DatabaseClanStorage;
import de.soulhive.system.clan.storage.impl.FileClanStorage;
import de.soulhive.system.clan.storage.impl.LocalClanStorage;
import de.soulhive.system.service.Service;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ClanService extends Service {

    private DatabaseClanStorage databaseClanStorage = new FileClanStorage();
    private LocalClanStorage localClanStorage = new LocalClanStorage(this.databaseClanStorage);
    private Map<Player, Player> lastHits = new HashMap<>();

    @Override
    public void initialize() {
        super.registerListeners(
            new AsyncPlayerChatListener(this),
            new PlayerDeathListener(this),
            new EntityDamageByEntityListener(this),
            new de.soulhive.system.clan.listener.protection.EntityDamageByEntityListener(this)
        );
        super.registerCommand("clan", new CommandClan(this));
    }

    @Override
    public void disable() {
        this.localClanStorage.getCache().asMap().values().forEach(clanMapper -> {
            this.databaseClanStorage.saveClanMember(clanMapper.getClanMember());
            this.databaseClanStorage.saveClan(clanMapper.getClan());
        });
    }

    public boolean areMembers(final Player first, Player second) {
        Clan clan = this.localClanStorage.getClan(first.getUniqueId().toString());

        return clan != null && clan.getMembers().contains(second.getUniqueId().toString());
    }

}
