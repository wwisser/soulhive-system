package de.soulhive.system.user.repository.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.repository.UserRepository;
import de.soulhive.system.util.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FileUserRepository implements UserRepository {

    private static final String FILE_NAME_USERS = "users.yml";
    private static final String FILE_NAME_UUIDS = "uuids.yml";

    private Config userDatabase = new Config(Settings.CONFIG_PATH, FILE_NAME_USERS);
    private Config uuidDatabase = new Config(Settings.CONFIG_PATH, FILE_NAME_UUIDS);

    @Override
    public User fetchByUuid(String uuid) {
        String name = this.uuidDatabase.getString(uuid);
        long currentTimeMillis = System.currentTimeMillis();

        if (!this.userDatabase.contains(uuid)) {
            return new User(
                name, uuid, currentTimeMillis, currentTimeMillis,
                0, 0, 0, 0, 0
            );
        }

        String[] data = this.userDatabase.getString(uuid).split(";");

        return new User(
            name,
            uuid,
            Long.valueOf(data[0]),
            Long.valueOf(data[1]),
            Integer.valueOf(data[2]),
            Integer.valueOf(data[3]),
            Integer.valueOf(data[4]),
            Integer.valueOf(data[5]),
            Long.valueOf(data[6])
        );
    }

    @Override
    public User fetchByName(String name) {
        String uuid = this.uuidDatabase.getString(name);

        return this.fetchByUuid(uuid);
    }

    @Override
    public Map<String, ? super Number> fetchByColumn(int column) {
        Map<String, ? super Number> result = new HashMap<>();

        for (String key : this.userDatabase.getKeys(false)) {
            Number number = Long.valueOf(this.userDatabase.getString(key).split(";")[column]);

            result.put(this.uuidDatabase.getString(key), number);
        }

        return result;
    }

    @Override
    public String fetchNameByUuid(String uuid) {
        return this.uuidDatabase.getString(uuid);
    }

    @Override
    public void save(User user) {
        String data = user.getFirstSeen() + ";"
            + user.getLastSeen() + ";"
            + user.getKills() + ";"
            + user.getDeaths() + ";"
            + user.getVotes() + ";"
            + user.getPlaytime() + ";"
            + user.getJewels();

        this.userDatabase.set(user.getUuid(), data);
        this.userDatabase.saveFile();
    }

    public void updateUuid(Player player) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        this.uuidDatabase.set(name, uuid);
        this.uuidDatabase.set(uuid, name);
        this.uuidDatabase.saveFile();
    }

}
