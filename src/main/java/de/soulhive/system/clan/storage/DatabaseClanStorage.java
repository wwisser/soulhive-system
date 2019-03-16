package de.soulhive.system.clan.storage;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;

public interface DatabaseClanStorage extends ClanStorage {

    void saveClan(Clan clan);

    void deleteClan(Clan clan);

    void saveClanMember(ClanMember clanMember);

}
