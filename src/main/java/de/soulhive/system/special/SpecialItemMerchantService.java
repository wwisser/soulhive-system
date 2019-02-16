package de.soulhive.system.special;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.delay.DelayConfiguration;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.npc.impl.VillagerHologramNpc;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.special.factories.BoostRocketItemFactory;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.TextComponentUtils;
import de.soulhive.system.util.item.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@FeatureService
public class SpecialItemMerchantService extends Service implements Listener {

    private static final Location NPC_LOCATION = new Location(Settings.WORLD_MAIN, -62.5, 183, -347);
    private static final SpecialItemFactory ITEM_FACTORY = new BoostRocketItemFactory();
    private static final DelayConfiguration DELAY_CONFIGURATION = new DelayConfiguration(
        null,
        ITEM_FACTORY.getDelay()
    );

    private Config database = new Config(Settings.CONFIG_PATH, "sold_special_items.yml");
    private DelayService delayService;
    private UserService userService;

    @Override
    public void initialize() {
        this.delayService = SoulHive.getServiceManager().getService(DelayService.class);
        this.userService = SoulHive.getServiceManager().getService(UserService.class);

        super.registerListener(this);
        super.registerCommand("purchaseitem", new CommandPurchaseitem());

        SoulHive.getServiceManager().getService(NpcService.class).addNpc(new VillagerHologramNpc(
            NPC_LOCATION,
            BlockFace.EAST,
            clicker -> {
                final User user = SpecialItemMerchantService.this.userService.getUser(clicker);

                if (SpecialItemMerchantService.this.boughtItem(clicker)) {
                    clicker.sendMessage("§a§lHändler> §cIch verkaufe meine Items nur einmal.");
                    clicker.playSound(clicker.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return;
                }

                if (user.getJewels() < ITEM_FACTORY.getCosts()) {
                    clicker.sendMessage("§a§lHändler> §cDu kannst dir mein Angebot sowieso nicht leisten.");
                    clicker.playSound(clicker.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return;
                }

                clicker.sendMessage("§a§lHändler> §7Nur für kurze Zeit: §c§l" + ITEM_FACTORY.getUniqueName() + " §7(§d" + ITEM_FACTORY.getCosts() + " §7Juwelen)");
                clicker.spigot().sendMessage(TextComponentUtils.createClickableComponent(
                    "§a§lHändler> §6[KLICKE HIER]§7, um das Item zu kaufen.",
                    "§c§l" + ITEM_FACTORY.getUniqueName() + " §7für §d" + ITEM_FACTORY.getCosts() + " §7Juwelen kaufen",
                    "/purchaseitem referrer=npc"
                ));
                clicker.playSound(clicker.getLocation(), Sound.VILLAGER_IDLE, 1, 1);
            },
            "§a§lHändler",
            Villager.Profession.FARMER.ordinal()
        ));
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInHand = player.getItemInHand();

        if (itemInHand != null && ITEM_FACTORY.shouldExecute(itemInHand, event.getAction())) {
            event.setCancelled(true);

            if (player.hasPermission(Settings.PERMISSION_ADMIN)) {
                ITEM_FACTORY.execute(player);
                return;
            }

            this.delayService.handleDelay(player, DELAY_CONFIGURATION, ITEM_FACTORY::execute);
        }
    }

    private class CommandPurchaseitem extends CommandExecutorWrapper {

        @Override
        public void process(CommandSender sender, String label, String[] args) throws CommandException {
            if (args.length == 1 && args[0].equals("referrer=npc")) {
                final Player player = ValidateCommand.onlyPlayer(sender);
                final User user = SpecialItemMerchantService.this.userService.getUser(player);

                if (SpecialItemMerchantService.this.boughtItem(player)) {
                    return;
                }

                if (user.getJewels() >= ITEM_FACTORY.getCosts()) {
                    user.setJewels(user.getJewels() - ITEM_FACTORY.getCosts());
                    ItemUtils.addAndDropRest(player, ITEM_FACTORY.createItem(player));
                    player.sendMessage("§a§lHändler> §7Viel Spaß mit deinem Item.");
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    SpecialItemMerchantService.this.setBought(player);
                }
            } else {
                sender.sendMessage(Settings.PREFIX + "§cUnbekannter Befehl. Vertippt?");
            }
        }

    }

    private boolean boughtItem(final Player player) {
        if (!this.database.contains(ITEM_FACTORY.getDatabaseEntry())) {
            return false;
        }

        return this.database.getStringList(ITEM_FACTORY.getDatabaseEntry()).contains(player.getUniqueId().toString());
    }

    private void setBought(final Player player) {
        List<String> boughtList;

        if (!this.database.contains(ITEM_FACTORY.getDatabaseEntry())) {
            boughtList = new ArrayList<>();
        } else {
            boughtList = this.database.getStringList(ITEM_FACTORY.getDatabaseEntry());
        }

        boughtList.add(player.getUniqueId().toString());
        this.database.set(ITEM_FACTORY.getDatabaseEntry(), boughtList);
        this.database.saveFile();
    }

}
