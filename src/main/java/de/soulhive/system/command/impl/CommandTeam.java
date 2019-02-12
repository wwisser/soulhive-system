package de.soulhive.system.command.impl;

import com.google.common.collect.ImmutableMap;
import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.util.FontUtils;
import de.soulhive.system.vanish.VanishService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class CommandTeam extends CommandExecutorWrapper {

    private static final Map<String, String> GROUPS = ImmutableMap.of(
        "Admin", "§4§lAdmin",
        "Moderator", "§c§lModerator",
        "Supporter", "§9§lSupporter"
    );

    private VanishService vanishService;
    private PermissionManager permissionManager;
    private JavaPlugin plugin;

    @Override
    public void initialize() {
        this.permissionManager = PermissionsEx.getPermissionManager();
        this.plugin = SoulHive.getPlugin();
        this.vanishService = SoulHive.getServiceManager().getService(VanishService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> this.sendTeam(sender));
    }

    private void sendTeam(final CommandSender sender) {
        final Map<String, Set<String>> groupUsers = new HashMap<>();

        for (String group : GROUPS.keySet()) {
            final Set<String> coloredUsers = new HashSet<>();

            for (PermissionUser permissionUser : this.permissionManager.getGroup(group).getUsers()) {
                final String name = permissionUser.getName();
                coloredUsers.add((Bukkit.getPlayer(name) != null && !this.vanishService.getVanishedPlayers().contains(Bukkit.getPlayer(name)) ? "§a" : "§c") + name);
            }

            groupUsers.put(group, coloredUsers);
        }

        sender.sendMessage("§8§m---------------------------------------------");
        sender.sendMessage("");

        for (Map.Entry<String, String> entry : GROUPS.entrySet()) {
            sender.sendMessage("§8➥ " + entry.getValue() + "§8: " + String.join("§7, ", groupUsers.get(entry.getKey())));
        }

        sender.sendMessage("");
        sender.sendMessage("§8§m---------------------------------------------");
    }

}
