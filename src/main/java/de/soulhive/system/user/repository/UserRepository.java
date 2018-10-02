package de.soulhive.system.user.repository;

import de.soulhive.system.user.User;

import java.util.Map;

public interface UserRepository {

    User fetchByUuid(String uuid);

    User fetchByName(String name);

    Map<String, ? super Number> fetchByColumn(int column);

    String fetchNameByUuid(String uuid);

    void save(User user);

}
