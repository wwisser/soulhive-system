package de.soulhive.system.clan.serializer;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;

public interface ClanSerializer<T> {

    T serializeClan(Clan clan);

    T serializeClanMember(ClanMember clanMember);

}