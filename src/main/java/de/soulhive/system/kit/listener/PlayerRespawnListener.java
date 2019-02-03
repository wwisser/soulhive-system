package de.soulhive.system.kit.listener;

import de.soulhive.system.SoulHive;
import de.soulhive.system.util.item.ItemBuilder;
import de.soulhive.system.vote.VoteService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlayerRespawnListener implements Listener {

    private static final List<ItemStack> AUTO_KIT_DEFAULT = Arrays.asList(
        new ItemBuilder(Material.STONE_SWORD).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.FISHING_ROD).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_HELMET).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_BOOTS).name("§2§lAutoKit Lvl. I").build()
    );

    private static final List<ItemStack> AUTO_KIT_EVENT = Arrays.asList(
        new ItemBuilder(Material.IRON_SWORD).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.BOW).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_HELMET).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_CHESTPLATE).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_LEGGINGS).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_BOOTS).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.ARROW).amount(5).name("§6§lAutoKit Lvl. II").build()
    );

    private VoteService voteService;

    public PlayerRespawnListener() {
        this.voteService = SoulHive.getServiceManager().getService(VoteService.class);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        List<ItemStack> autoKit = this.voteService.hasEventKit(player) ? AUTO_KIT_EVENT : AUTO_KIT_DEFAULT;

        player.getInventory().addItem(autoKit.get(0));
        player.getInventory().addItem(autoKit.get(1));
        player.getInventory().setHelmet(autoKit.get(2));
        player.getInventory().setChestplate(autoKit.get(3));
        player.getInventory().setLeggings(autoKit.get(4));
        player.getInventory().setBoots(autoKit.get(5));

        if (this.voteService.hasEventKit(player)) {
            player.getInventory().addItem(autoKit.get(6));
        }
    }

}
