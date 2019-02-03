package de.soulhive.system.task.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.particle.ParticleService;
import de.soulhive.system.task.ComplexTask;
import de.soulhive.system.util.nms.ParticleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class YoloBootsUpdateTask extends BukkitRunnable implements ComplexTask {

    private ParticleService particleService;

    @Override
    public void setup(JavaPlugin plugin) {
        this.particleService = SoulHive.getServiceManager().getService(ParticleService.class);
        super.runTaskTimer(plugin, 0L, 2L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().getBoots() != null
                && player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS
                && player.getInventory().getBoots().getItemMeta() != null
                && player.getInventory().getBoots().getItemMeta().getDisplayName() != null
                && player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "YOLO-Boots")
            ) {
                if (!this.particleService.getSelectedParticle(player).isPresent()) {
                    ParticleUtils.play(player.getLocation(), EnumParticle.FIREWORKS_SPARK, 0, 0, 0, 0, 0);
                }

                final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
                final LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

                switch (this.randInt(1, 25)) {
                    case 1: {
                        bootsMeta.setColor(Color.fromRGB(255, 0, 0));
                        break;
                    }
                    case 2: {
                        bootsMeta.setColor(Color.fromRGB(255, 255, 0));
                        break;
                    }
                    case 3: {
                        bootsMeta.setColor(Color.fromRGB(76, 153, 0));
                        break;
                    }
                    case 4: {
                        bootsMeta.setColor(Color.fromRGB(0, 255, 255));
                        break;
                    }
                    case 5: {
                        bootsMeta.setColor(Color.fromRGB(153, 0, 153));
                        break;
                    }
                    case 6: {
                        bootsMeta.setColor(Color.fromRGB(255, 255, 255));
                        break;
                    }
                    case 7: {
                        bootsMeta.setColor(Color.fromRGB(255, 51, 153));
                        break;
                    }
                    case 8: {
                        bootsMeta.setColor(Color.fromRGB(255, 128, 0));
                        break;
                    }
                    case 9: {
                        bootsMeta.setColor(Color.fromRGB(224, 224, 224));
                        break;
                    }
                    case 10: {
                        bootsMeta.setColor(Color.fromRGB(0, 0, 153));
                        break;
                    }
                    case 11: {
                        bootsMeta.setColor(Color.fromRGB(47, 79, 79));
                        break;
                    }
                    case 12: {
                        bootsMeta.setColor(Color.fromRGB(255, 215, 0));
                        break;
                    }
                    case 13: {
                        bootsMeta.setColor(Color.fromRGB(255, 246, 143));
                        break;
                    }
                    case 14: {
                        bootsMeta.setColor(Color.fromRGB(127, 255, 0));
                        break;
                    }
                    case 15: {
                        bootsMeta.setColor(Color.fromRGB(0, 245, 255));
                        break;
                    }
                    case 16: {
                        bootsMeta.setColor(Color.fromRGB(205, 193, 197));
                        break;
                    }
                    case 17: {
                        bootsMeta.setColor(Color.fromRGB(0, 250, 154));
                        break;
                    }
                    case 18: {
                        bootsMeta.setColor(Color.fromRGB(32, 178, 170));
                        break;
                    }
                    case 19: {
                        bootsMeta.setColor(Color.fromRGB(248, 248, 255));
                        break;
                    }
                    case 20:
                    case 21: {
                        bootsMeta.setColor(Color.fromRGB(240, 248, 255));
                        break;
                    }
                    case 22: {
                        bootsMeta.setColor(Color.fromRGB(25, 25, 112));
                        break;
                    }
                    case 23: {
                        bootsMeta.setColor(Color.fromRGB(110, 123, 139));
                        break;
                    }
                    case 24: {
                        bootsMeta.setColor(Color.fromRGB(139, 69, 19));
                        break;
                    }
                    case 25: {
                        bootsMeta.setColor(Color.fromRGB(132, 112, 255));
                        break;
                    }
                    default: {
                        bootsMeta.setColor(Color.fromRGB(153, 255, 153));
                        break;
                    }
                }

                bootsMeta.setDisplayName(ChatColor.AQUA + "YOLO-Boots");
                boots.setItemMeta(bootsMeta);
                player.getInventory().setBoots(boots);
            }

        }
    }


    private int randInt(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

}
