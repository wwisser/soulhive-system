package de.soulhive.system.command.impl.admin;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.npc.impl.VillagerNpc;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ParticleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class CommandDebug extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, Settings.PERMISSION_ADMIN);
        final Player player = ValidateCommand.onlyPlayer(sender);

        if (this.checkArg(args, "v")) {
            final NpcService npcService = SoulHive.getServiceManager().getService(NpcService.class);

            npcService.addNpc(
                new VillagerNpc(player.getLocation(), player1 -> player1.sendMessage("§cIt works :)"))
            );
        }

        if (this.checkArg(args, "guardian")) {
            ParticleUtils.play(player, player.getLocation(), EnumParticle.MOB_APPEARANCE, 0, 0, 0, 0, 0);
        }

        if (this.checkArg(args, "randeff")) {
            final EnumParticle[] values = EnumParticle.values();
            final int i = ThreadLocalRandom.current().nextInt(values.length);

            ParticleUtils.play(player, player.getLocation(), values[i], 0, 0, 0, 0, 0);
            sender.sendMessage(Settings.PREFIX + "§a§l" + values[i].toString());
        }

        if (this.checkArg(args, "a")) {
            final ArmorStand armorStand = player.getLocation().getWorld().spawn(player.getLocation(), ArmorStand.class);

            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setHelmet(new ItemStack(Material.PACKED_ICE));
        }

        if (this.checkArg(args, "s")) {
            player.getNearbyEntities(10, 10, 10)
                .stream()
                .filter(entity -> entity instanceof ArmorStand)
                .forEach(Entity::remove);
        }

        if (this.checkArg(args, "ce")) {
            player.getNearbyEntities(3, 3, 3).forEach(Entity::remove);
        }

        player.sendMessage(Settings.PREFIX + "§dDebug command ran successfully");
    }

    private boolean checkArg(final String[] args, final String expected) {
        return args.length > 0 && args[0].equalsIgnoreCase("--" + expected);
    }

}
