package de.soulhive.system.kit;

import de.soulhive.system.SoulHive;
import de.soulhive.system.kit.command.CommandKit;
import de.soulhive.system.kit.listener.PlayerDeathListener;
import de.soulhive.system.kit.listener.PlayerJoinListener;
import de.soulhive.system.kit.listener.PlayerRespawnListener;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.MillisecondsConverter;
import de.soulhive.system.util.item.ItemBuilder;
import de.soulhive.system.vote.VoteService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@FeatureService
public class KitService extends Service {

    private static final List<ItemStack> AUTO_KIT_DEFAULT = Arrays.asList(
        new ItemBuilder(Material.STONE_SWORD).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.FISHING_ROD).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.COOKED_BEEF).amount(8).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_HELMET).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("§2§lAutoKit Lvl. I").build(),
        new ItemBuilder(Material.CHAINMAIL_BOOTS).name("§2§lAutoKit Lvl. I").build()
    );

    private static final List<ItemStack> AUTO_KIT_EVENT = Arrays.asList(
        new ItemBuilder(Material.IRON_SWORD).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.BOW).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.COOKED_CHICKEN).amount(10).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_HELMET).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_CHESTPLATE).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_LEGGINGS).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.IRON_BOOTS).name("§6§lAutoKit Lvl. II").build(),
        new ItemBuilder(Material.ARROW).amount(5).name("§6§lAutoKit Lvl. II").build()
    );

    private Config database = new Config(Settings.CONFIG_PATH, "kits.yml");
    private VoteService voteService;

    @Override
    public void initialize() {
        this.voteService = SoulHive.getServiceManager().getService(VoteService.class);
        super.registerCommand("kit", new CommandKit(this));
        super.registerListeners(
            new PlayerRespawnListener(this),
            new PlayerJoinListener(this),
            new PlayerDeathListener()
        );
    }

    @Override
    public void disable() {
        this.database.saveFile();
    }

    public void equipWithStarter(final Player player) {
        List<ItemStack> autoKit = this.voteService.hasEventKit(player) ? AUTO_KIT_EVENT : AUTO_KIT_DEFAULT;

        player.getInventory().addItem(autoKit.get(0));
        player.getInventory().addItem(autoKit.get(1));
        player.getInventory().addItem(autoKit.get(2));
        player.getInventory().setHelmet(autoKit.get(3));
        player.getInventory().setChestplate(autoKit.get(4));
        player.getInventory().setLeggings(autoKit.get(5));
        player.getInventory().setBoots(autoKit.get(6));

        if (this.voteService.hasEventKit(player)) {
            player.getInventory().addItem(autoKit.get(7));
        }
    }

    public boolean hasRecieved(final Player player) {
        if (this.database.contains(player.getUniqueId().toString())) {
            return System.currentTimeMillis() < this.database.getLong(player.getUniqueId().toString());
        }
        return false;
    }

    public void setRecieved(final Player player) {
        long time = 60000 * 60 * 12;

        if (player.hasPermission("soulhive.obsidian")
            || player.hasPermission("soulhive.emerald")
            || player.hasPermission("soulhive.diamond")
            || player.hasPermission("soulhive.gold")) {
            time = 60000 * 60 * 24 * 7;
        }

        this.database.set(player.getUniqueId().toString(), System.currentTimeMillis() + time);
    }

    public String getTimeRemaining(final Player player) {
        final long remaining = this.database.getLong(player.getUniqueId().toString()) - System.currentTimeMillis();

        return MillisecondsConverter.convertToString(remaining);
    }

}
