package de.soulhive.system.clan.storage;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;

import java.util.List;

public interface ClanStorage {

    List<Clan> getClans();

    Clan getClan(String uuid);

    ClanMember getClanMember(String uuid);

}
