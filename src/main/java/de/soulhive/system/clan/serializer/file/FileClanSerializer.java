package de.soulhive.system.clan.serializer.file;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.serializer.ClanSerializer;
import org.bukkit.configuration.ConfigurationSection;

public class FileClanSerializer implements ClanSerializer<ConfigurationSection> {

    @Override
    public Clan deserializeClan(ConfigurationSection configurationSection) {
        return null;
    }

    @Override
    public ClanMember deserializeClanMember(ConfigurationSection data, String uuid) {
        return null;
    }

}
