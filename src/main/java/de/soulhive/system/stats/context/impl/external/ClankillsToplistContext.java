package de.soulhive.system.stats.context.impl.external;

import de.soulhive.system.SoulHive;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.storage.DatabaseClanStorage;
import de.soulhive.system.stats.context.ToplistContext;
import de.soulhive.system.stats.label.StatsLabel;
import de.soulhive.system.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClankillsToplistContext implements ToplistContext {

    private List<Clan> clans;

    public ClankillsToplistContext(final DatabaseClanStorage databaseClanStorage) {
        this.clans = databaseClanStorage.getClans();
    }

    @Override
    public StatsLabel getLabel() {
        return StatsLabel.CLAN_KILLS;
    }

    @Override
    public Map<String, ? super Number> getData() {
        return this.clans
            .stream()
            .collect(Collectors.toMap(Clan::getName, Clan::getKills));
    }

    @Override
    public String getSkullOwnerByKey(final String key) {
        Optional<String> optionalOwner = this.clans
            .stream()
            .filter(clan -> clan.getTag().equals(key))
            .map(Clan::getOwner)
            .findFirst();

        if (optionalOwner.isPresent()) {
            String owner = optionalOwner.get();
            UserService userService = SoulHive.getServiceManager().getService(UserService.class);

            return userService.getUserByUuid(owner).getName();
        } else {
            return null;
        }
    }

}
