package de.soulhive.system.clan.serializer;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;

public interface ClanSerializer<T> {

    Clan deserializeClan(T data);

    ClanMember deserializeClanMember(T data, String uuid);

}
