package de.soulhive.system.clan.serializer.impl;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.serializer.ClanDeserializer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class FileClanDeserializer implements ClanDeserializer<ConfigurationSection> {

    @Override
    public Clan deserializeClan(ConfigurationSection section) {
        String tag = section.getString("tag");
        String name = section.getString("name");
        String owner = section.getString("owner");
        List<String> members = section.getStringList("member");
        int kills = section.getInt("kills");
        int deaths = section.getInt("deaths");
        int jewels = section.getInt("jewels");
        long created = section.getLong("created");

        return new Clan(name, tag, owner, members, kills, deaths, jewels, created);
    }

    /**
     * The clan (which gets passed as null here) gets injected by {@link ClanMember#setClan(Clan)} after this method call.
     */
    @Override
    public ClanMember deserializeClanMember(ConfigurationSection data, String uuid) {
        int kills = data.getInt("kills");
        int deaths = data.getInt("deaths");
        long joined = data.getLong("joined");
        boolean moderator = data.getBoolean("moderator");

        return new ClanMember(uuid, kills, deaths, null, joined, moderator);
    }

}
