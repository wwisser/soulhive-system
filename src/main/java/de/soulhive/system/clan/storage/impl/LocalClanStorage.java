package de.soulhive.system.clan.storage.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.storage.ClanStorage;
import de.soulhive.system.clan.storage.DatabaseClanStorage;

import javax.annotation.ParametersAreNonnullByDefault;

public class LocalClanStorage implements ClanStorage {

    private Cache<String, ClanMember> memberCache;
    private DatabaseClanStorage databaseClanStorage;

    public LocalClanStorage(DatabaseClanStorage databaseClanStorage) {
        this.databaseClanStorage = databaseClanStorage;
        this.memberCache = CacheBuilder.newBuilder().build(new ClanMemberLoader());
    }

    private class ClanMemberLoader extends CacheLoader<String, ClanMember> {

        private DatabaseClanStorage databaseClanStorage = LocalClanStorage.this.databaseClanStorage;

        @Override
        @ParametersAreNonnullByDefault
        public ClanMember load(String uuid) {

            this.databaseClanStorage.getClan(uuid);

            return null;
        }

    }

}
