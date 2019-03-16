package de.soulhive.system.clan.storage.impl;

import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.serializer.file.FileClanDeserializer;
import de.soulhive.system.clan.storage.DatabaseClanStorage;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileClanStorage implements DatabaseClanStorage {

    private Config yamlFile = new Config(Settings.CONFIG_PATH, "clans.yml");
    private FileClanDeserializer fileClanSerializer = new FileClanDeserializer();

    @Override
    public List<Clan> getClans() {
        ConfigurationSection section = this.yamlFile.getFileConfiguration().getConfigurationSection("clans");

        return section.getKeys(false)
            .stream()
            .map(section::getConfigurationSection)
            .map(this.fileClanSerializer::deserializeClan)
            .collect(Collectors.toList());
    }

    @Override
    public Clan getClan(String uuid) {
        Optional<Clan> clanOptional = this.getClans()
            .stream()
            .filter(clan -> clan.getMembers().contains(uuid))
            .findFirst();

        return clanOptional.orElse(null);
    }

    @Override
    public ClanMember getClanMember(String uuid) {
        if (!this.yamlFile.contains("users." + uuid)) {
            return null;
        }

        ConfigurationSection section = this.yamlFile.getFileConfiguration().getConfigurationSection("users." + uuid);
        ClanMember clanMember = this.fileClanSerializer.deserializeClanMember(section, uuid);

        clanMember.setClan(this.getClan(uuid));
        return clanMember;
    }

    @Override
    public void saveClan(Clan clan) {
        String path = "clans." + clan.getTag() + ".";

        this.yamlFile.set(path + "name", clan.getName());
        this.yamlFile.set(path + "tag", clan.getTag());
        this.yamlFile.set(path + "owner", clan.getOwner());
        this.yamlFile.set(path + "kills", clan.getKills());
        this.yamlFile.set(path + "deaths", clan.getDeaths());
        this.yamlFile.set(path + "member", clan.getMembers());
        this.yamlFile.set(path + "jewels", clan.getBankJewels());

        if (!this.yamlFile.contains(path + "created")) {
            this.yamlFile.set(path + "created", clan.getCreated());
        }

        this.yamlFile.saveFile();
    }

    @Override
    public void deleteClan(Clan clan) {
        this.yamlFile.set("clans." + clan.getTag(), null);
        this.yamlFile.saveFile();
    }

    @Override
    public void saveClanMember(ClanMember clanMember) {
        String path = "users." + clanMember.getUuid() + ".";

        if (clanMember.getClan() != null) {
            this.yamlFile.set(path + "tag", clanMember.getClan().getTag());
            this.yamlFile.set(path + "joined", clanMember.getJoined());
            this.yamlFile.set(path + "moderator", clanMember.getModerator());
            this.yamlFile.set(path + "kills", clanMember.getKills());
            this.yamlFile.set(path + "deaths", clanMember.getDeaths());
            this.yamlFile.saveFile();
        } else if (this.yamlFile.contains(path)) {
            this.yamlFile.set(path, null);
            this.yamlFile.saveFile();
        }
    }

}
