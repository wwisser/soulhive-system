package de.soulhive.system.clan.storage;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;

public interface ClanStorage {

    Clan getClan(String uuid);

    ClanMember getClanMember(String uuid);

}
