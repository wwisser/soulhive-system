package de.soulhive.system.listener.impl.guard;

import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.nms.ActionBar;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class PlayerInteractListener implements Listener {

    private static final List<Material> BLACKLISTED_MATERIALS = Arrays.asList(
        Material.CHEST,
        Material.TRAPPED_CHEST,
        Material.HOPPER,
        Material.FURNACE,
        Material.DROPPER,
        Material.DISPENSER,
        Material.ITEM_FRAME,
        Material.MONSTER_EGG
    );
    private static final Map<Action, List<Material>> CANCELLED_ACTIONS = new HashMap<Action, List<Material>>() {{
        super.put(Action.RIGHT_CLICK_BLOCK, BLACKLISTED_MATERIALS);
        super.put(Action.PHYSICAL, Collections.singletonList(Material.SOIL));
    }};

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getClickedBlock() != null && !Settings.SKYBLOCK_WORLDS.contains(player.getWorld())) {
            CANCELLED_ACTIONS.forEach((action, materials) -> {
                if (event.getAction() == action
                    && materials.contains(event.getClickedBlock().getType())
                    && !player.hasPermission(Settings.PERMISSION_BUILD)) {
                    ActionBar.send("§cDu darfst damit nicht interagieren.", player);
                    event.setCancelled(true);
                } else if (event.getClickedBlock().getType() == Material.ANVIL) {
                    event.getClickedBlock().setData((byte) 0);
                }
            });
        }
    }

}
