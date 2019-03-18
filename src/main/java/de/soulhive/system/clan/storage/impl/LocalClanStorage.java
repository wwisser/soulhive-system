package de.soulhive.system.clan.storage.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMapper;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.storage.ClanStorage;
import de.soulhive.system.clan.storage.DatabaseClanStorage;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

public class LocalClanStorage implements ClanStorage {

    @Getter private LoadingCache<String, ClanMapper> cache;
    private DatabaseClanStorage databaseClanStorage;

    public LocalClanStorage(DatabaseClanStorage databaseClanStorage) {
        this.databaseClanStorage = databaseClanStorage;
        this.cache = CacheBuilder.newBuilder().build(new ClanLoader());
    }

    public void saveAndUnload(ClanMember clanMember) {
        this.databaseClanStorage.saveClanMember(clanMember);
        this.cache.asMap().remove(clanMember.getUuid());
    }

    public void saveClanMember(ClanMember clanMember) {
        this.databaseClanStorage.saveClanMember(clanMember);
    }

    public void saveClan(Clan clan) {
        this.databaseClanStorage.saveClan(clan);
    }

    @Override
    public List<Clan> getClans() {
        return this.databaseClanStorage.getClans();
    }

    @Override
    @SneakyThrows
    public Clan getClan(String uuid) {
        if (!this.cache.asMap().containsKey(uuid)) {
            Optional<Clan> optionalClan = this.cache.asMap().values()
                .stream()
                .map(ClanMapper::getClan)
                .filter(clan -> clan.getMembers().contains(uuid))
                .findFirst();

            return optionalClan.orElse(null);
        }

        return this.cache.get(uuid).getClan();
    }

    public void addClanSet(Clan clan, ClanMember clanMember) {
        this.cache.put(clanMember.getUuid(), new ClanMapper(clan, clanMember));
    }

    @Override
    @SneakyThrows
    public ClanMember getClanMember(String uuid) {
        return this.cache.get(uuid).getClanMember();
    }

    private class ClanLoader extends CacheLoader<String, ClanMapper> {

        private DatabaseClanStorage databaseClanStorage = LocalClanStorage.this.databaseClanStorage;

        @Override
        @ParametersAreNonnullByDefault
        public ClanMapper load(String uuid) {
            ClanMember clanMember = this.databaseClanStorage.getClanMember(uuid);

            Clan clan = null;

            if (clanMember != null) {
                clan = clanMember.getClan();
            }

            return new ClanMapper(clan, clanMember);
        }

    }

}
