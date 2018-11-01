package de.soulhive.system.command.impl.admin;

import de.skycade.system.setting.Message;
import de.skycade.system.util.ActionBar;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandEnchant implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.COMMAND_ONLY_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission("skycade.enchant")) {
            ActionBar.send(Message.NO_PERMISSION, player);
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(Message.COMMAND_USAGE +
                    "/enchant <Enchantment> <Level>\n"
                            + "§cEnchantments: Stärke, Flamme, Unendlichkeit, Schlag, Schärfe, Bann,"
                            + " Effizienz, Haltbarkeit, Verbrennung, Rückstoß, Glück, Plünderung, Atmung,"
                            + " Schutz, Explosionsschutz, Feuerschutz, Schusssicher, Dornen");
            return true;
        }

        if (player.getItemInHand().getType() == Material.AIR) {
            ActionBar.send("§cDu musst ein Item in deiner Hand halten.", player);
            return true;
        }
        ItemStack hand = player.getItemInHand();
        int level = Integer.parseInt(args[1]);
        if ((level > 0) && (level < 10000)) {
            Enchantment enchantment;
            if (args[0].equalsIgnoreCase("stärke")) {
                enchantment = Enchantment.ARROW_DAMAGE;
            } else if (args[0].equalsIgnoreCase("flamme")) {
                enchantment = Enchantment.ARROW_FIRE;
            } else if (args[0].equalsIgnoreCase("unendlichkeit")) {
                enchantment = Enchantment.ARROW_INFINITE;
            } else if (args[0].equalsIgnoreCase("schlag")) {
                enchantment = Enchantment.ARROW_KNOCKBACK;
            } else if (args[0].equalsIgnoreCase("schärfe")) {
                enchantment = Enchantment.DAMAGE_ALL;
            } else if (args[0].equalsIgnoreCase("bann")) {
                enchantment = Enchantment.DAMAGE_UNDEAD;
            } else if (args[0].equalsIgnoreCase("effizienz")) {
                enchantment = Enchantment.DIG_SPEED;
            } else if (args[0].equalsIgnoreCase("haltbarkeit")) {
                enchantment = Enchantment.DURABILITY;
            } else if (args[0].equalsIgnoreCase("verbrennung")) {
                enchantment = Enchantment.FIRE_ASPECT;
            } else if (args[0].equalsIgnoreCase("rückstoß")) {
                enchantment = Enchantment.KNOCKBACK;
            } else if (args[0].equalsIgnoreCase("glück")) {
                enchantment = Enchantment.LOOT_BONUS_BLOCKS;
            } else if (args[0].equalsIgnoreCase("plünderung")) {
                enchantment = Enchantment.LOOT_BONUS_MOBS;
            } else if (args[0].equalsIgnoreCase("atmung")) {
                enchantment = Enchantment.OXYGEN;
            } else if (args[0].equalsIgnoreCase("schutz")) {
                enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
            } else if (args[0].equalsIgnoreCase("explosionsschutz")) {
                enchantment = Enchantment.PROTECTION_EXPLOSIONS;
            } else if (args[0].equalsIgnoreCase("feuerschutz")) {
                enchantment = Enchantment.PROTECTION_FALL;
            } else if (args[0].equalsIgnoreCase("schusssicher")) {
                enchantment = Enchantment.PROTECTION_PROJECTILE;
            } else if (args[0].equalsIgnoreCase("dornen")) {
                enchantment = Enchantment.THORNS;
            } else {
                player.performCommand("enchant");
                return true;
            }
            if (enchantment != null) {
                hand.addUnsafeEnchantment(enchantment, level);
            }
            ActionBar.send("§7Item auf §3" + args[0].toUpperCase() + " Level " + Integer.toString(level) + " §7verzaubert", player);
            return true;
        }
        return true;
    }

}
