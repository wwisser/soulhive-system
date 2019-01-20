package de.soulhive.system.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class PermissionUtils {

    public void addPermission(final String name, final String permission) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " add " + permission);
    }

    public void setRank(final String name, final String rank) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set " + rank);
    }

}
