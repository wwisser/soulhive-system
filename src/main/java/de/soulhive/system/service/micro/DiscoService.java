package de.soulhive.system.service.micro;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.LocationUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@FeatureService
public class DiscoService extends Service {

    private Config database = new Config(Settings.CONFIG_PATH, "disco.yml");
    private Map<String, Location> locations = new HashMap<>();

    @Override
    public void initialize() {
        final Set<String> keys = this.database.getKeys(false);

        for (final String key : keys) {
            this.locations.put(key, this.getLocation(key));
            this.getLocation(key).getWorld().playEffect(this.getLocation(key), Effect.STEP_SOUND, 35);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Location location : DiscoService.this.locations.values()) {
                    final Block wool = location.getBlock();

                    if (wool.getType().equals(Material.WOOL)) {
                        if (wool.getData() == 13) {
                            wool.setData((byte) 0);
                        } else {
                            wool.setData((byte) 13);
                        }
                    }

                }
            }
        }.runTaskTimer(SoulHive.getPlugin(), 15L, 15L);

        super.registerCommand("disco", new CommandDisco());
    }

    @Override
    public void disable() {
        this.locations.forEach((name, location) -> this.setLocation(location, name));

        this.database.saveFile();
    }

    private class CommandDisco extends CommandExecutorWrapper {

        @Override
        public void process(CommandSender sender, String label, String[] args) throws CommandException {
            ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
            ValidateCommand.minArgs(1, args, "/disco s <id>");
            final Player player = ValidateCommand.onlyPlayer(sender);
            final Block block = LocationUtils.getTargetBlock(player, 5);


            if (!block.getType().equals(Material.WOOL)) {
                player.sendMessage(Settings.PREFIX + "§cBitte schaue auf einen Wollblock!");
                return;
            }

            DiscoService.this.setLocation(block.getLocation(), args[1]);
            player.sendMessage(Settings.PREFIX + "§7Der Block mit der ID §f#" + args[1] + " §7wurde erstellt!");
        }

    }

    private void setLocation(final Location location, final String name) {
        this.database.set(name + ".X", location.getBlockX());
        this.database.set(name + ".Y", location.getBlockY());
        this.database.set(name + ".Z", location.getBlockZ());
        this.database.set(name + ".world", location.getWorld().getName());
    }

    private Location getLocation(final String name) {
        final int x = this.database.getInt(name + ".X");
        final int y = this.database.getInt(name + ".Y");
        final int z = this.database.getInt(name + ".Z");
        final World world = Bukkit.getWorld(this.database.getString(name + ".world"));

        return new Location(world, x, y, z);
    }

}
