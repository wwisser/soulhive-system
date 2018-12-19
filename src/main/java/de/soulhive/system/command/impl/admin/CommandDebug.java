package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CommandDebug extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        final Player player = ValidateCommand.onlyPlayer(sender);

        if (args.length > 0 && args[0].equalsIgnoreCase("--s")) {
            player.getNearbyEntities(10, 10, 10)
                .stream()
                .filter(entity -> entity instanceof ArmorStand)
                .forEach(Entity::remove);
            return;
        }

        final ArmorStand armorStand = player.getLocation().getWorld().spawn(player.getLocation(), ArmorStand.class);

        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setHelmet(new ItemStack(Material.PACKED_ICE));
    }

}
