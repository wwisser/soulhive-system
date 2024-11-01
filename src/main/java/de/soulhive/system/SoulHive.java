package de.soulhive.system;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.soulhive.system.combat.CombatService;
import de.soulhive.system.command.CommandService;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.delay.DelayService;
import de.soulhive.system.ip.IpResolverService;
import de.soulhive.system.kit.KitService;
import de.soulhive.system.listener.ListenerService;
import de.soulhive.system.motd.MotdService;
import de.soulhive.system.npc.NpcService;
import de.soulhive.system.particle.ParticleService;
import de.soulhive.system.peace.PeaceService;
import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.service.Service;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.special.SpecialItemMerchantService;
import de.soulhive.system.stats.StatsService;
import de.soulhive.system.supply.SupplyService;
import de.soulhive.system.task.impl.*;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.thread.ShutdownHookThread;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.ReflectUtils;
import de.soulhive.system.vanish.VanishService;
import de.soulhive.system.vault.JewelEconomy;
import de.soulhive.system.vote.VoteService;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class SoulHive extends JavaPlugin {

    @Getter private static JavaPlugin plugin;
    @Getter private static ServiceManager serviceManager;

    @Override
    public void onEnable() {
        SoulHive.plugin = this;
        ServiceManager serviceManager = new ServiceManager(this);
        SoulHive.serviceManager = serviceManager;

        final TaskService taskService = new TaskService(this);
        final PlannedShutdownTask plannedShutdownTask = new PlannedShutdownTask();
        final NpcService npcService = new NpcService(this);
        final UserService userService = new UserService();

        super.getServer().getServicesManager().register(
            Economy.class,
            new JewelEconomy(userService),
            this,
            ServicePriority.Highest
        );

        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(plannedShutdownTask));
        taskService.registerTasks(
            new TablistUpdateTask(),
            plannedShutdownTask,
            new PlayerVoidKillTask(),
            new FlyDisableTask(),
            new JumpboostSupplierTask(),
            new YoloBootsUpdateTask(),
            new IslandLevelToplistUpdateTask()
        );
        Settings.NPCS.forEach(npcService::addNpc);

        Arrays.asList(
            taskService,
            new ContainerService(),
            new MotdService(),
            userService,
            new DelayService(),
            new StatsService(),
            new CommandService(this),
            new ListenerService(),
            new VanishService(this),
            new CombatService(),
            new ScoreboardService(),
            new SupplyService(),
            npcService,
            new ParticleService(),
            new PeaceService(),
            new VoteService(),
            new KitService(),
            new SpecialItemMerchantService(),
            new IpResolverService()
        ).forEach(serviceManager::registerService);

        ReflectUtils.getPacketObjects("de.soulhive.system.service.micro", Service.class)
            .forEach(serviceManager::registerService);
    }

    @Override
    public void onDisable() {
        SoulHive.serviceManager.getServices().forEach(SoulHive.serviceManager::unregisterService);
        HologramsAPI.getHolograms(this).forEach(Hologram::delete);
    }

}
