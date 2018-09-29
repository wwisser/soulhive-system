package de.skydust.system.user.repository;

import de.skydust.system.user.User;

public interface UserRepository {

    User fetchByUuid(String uuid);

    User fetchByName(String name);

    void save(User user);

}
