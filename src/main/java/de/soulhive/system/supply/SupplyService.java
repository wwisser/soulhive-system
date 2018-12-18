package de.soulhive.system.supply;

import de.soulhive.system.SoulHive;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.command.CommandSupply;
import de.soulhive.system.supply.listener.BlockBreakListener;
import de.soulhive.system.supply.listener.PlayerInteractListener;
import de.soulhive.system.supply.task.SupplyLocationUpdateTask;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.util.Config;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@FeatureService
@Getter
public class SupplyService extends Service {

    private List<ItemStack> itemStacks = new ArrayList<>();
    private List<Location> signLocations = new ArrayList<>();
    private Config fileStorage = new Config(Settings.CONFIG_PATH, "supply.yml");

    @Override
    public void initialize() {
        final DelayService delayService = SoulHive.getServiceManager().getService(DelayService.class);
        final TaskService taskService = SoulHive.getServiceManager().getService(TaskService.class);

        this.loadItems();
        this.loadSigns();
        super.registerCommand("supply", new CommandSupply());
        super.registerListener(new PlayerInteractListener(this, delayService));
        super.registerListener(new BlockBreakListener(this));
        taskService.registerTasks(new SupplyLocationUpdateTask(this));
    }

    @Override
    public void disable() {
        this.saveItems();
        this.saveSigns();
    }

    public void update() {
        Collections.shuffle(this.itemStacks);
        Collections.shuffle(this.signLocations);

        for (int i = 0; i < this.signLocations.size(); i++) {
            if (this.itemStacks.size() > i) {
                final ItemStack itemStack = this.itemStacks.get(i);
                final BlockState state = this.signLocations.get(i).getBlock().getState();

                if (state instanceof Sign) {
                    final Sign sign = (Sign) state;
                    sign.setLine(0, "§5[KOSTENLOS]");
                    sign.setLine(1, "ID: " + i);
                    sign.setLine(2, "§0" + itemStack.getAmount());
                    sign.setLine(3, "§9" + itemStack.getType().toString());
                    sign.update();
                }
            }
        }
    }

    public void addSignLocation(final Location location) {
        this.signLocations.add(location);
    }

    public void removeSignLocation(final Location location) {
        this.signLocations.remove(location);
    }

    public void setItems(final List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    private void loadItems() {
        final List<?> items = this.fileStorage.getList("items");

        for (Object item : items) {
            this.itemStacks.add((ItemStack) item);
        }
    }

    private void loadSigns() {
        final List<?> locations = this.fileStorage.getList("locations");

        for (Object item : locations) {
            this.signLocations.add((Location) item);
        }
    }

    private void saveItems() {
        this.fileStorage.set("items", this.itemStacks);
        this.fileStorage.saveFile();
    }

    private void saveSigns() {
        this.fileStorage.set("locations", this.signLocations);
        this.fileStorage.saveFile();
    }

}
