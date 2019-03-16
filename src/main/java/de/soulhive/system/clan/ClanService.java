package de.soulhive.system.clan;

import de.soulhive.system.clan.storage.ClanStorage;
import de.soulhive.system.clan.storage.DatabaseClanStorage;
import de.soulhive.system.clan.storage.impl.FileClanStorage;
import de.soulhive.system.clan.storage.impl.LocalClanStorage;
import de.soulhive.system.service.Service;
import lombok.Getter;

@Getter
public class ClanService extends Service {

    private DatabaseClanStorage databaseClanStorage = new FileClanStorage();
    private ClanStorage localClanStorage = new LocalClanStorage(this.databaseClanStorage);

}
