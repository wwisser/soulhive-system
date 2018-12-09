package de.soulhive.system.motd;

import de.soulhive.system.SoulHive;
import de.soulhive.system.motd.command.CommandMotd;
import de.soulhive.system.motd.listener.ServerListPingListener;
import de.soulhive.system.motd.task.MotdUpdateTask;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.TaskService;
import de.soulhive.system.util.Config;

public class MotdService extends Service {

    private static final String FILE_NAME = "motd.yml";

    private static final String KEY_HEADER = "header";
    private static final String KEY_FOOTER = "footer";

    private Config config;

    @Override
    public void initialize() {
        this.reloadConfig();
        this.config.setDefault(KEY_HEADER, "§fSoulHive.de");
        this.config.setDefault(KEY_FOOTER, "§fLoading MOTD...");

        super.registerCommand("motd", new CommandMotd(this));
        super.registerListener(new ServerListPingListener(this));

        SoulHive.getServiceManager().getService(TaskService.class).registerTasks(
            new MotdUpdateTask(this)
        );
    }

    public void reloadConfig() {
        this.config = new Config(Settings.CONFIG_PATH, FILE_NAME);
    }

    public void updateHeader(String header) {
        this.config.set(KEY_HEADER, header);
        this.config.saveFile();
    }

    public void updateFooter(String footer) {
        this.config.set(KEY_FOOTER, footer);
        this.config.saveFile();
    }

    public String fetchHeader() {
        return this.config.getString(KEY_HEADER);
    }

    public String fetchFooter() {
        return this.config.getString(KEY_FOOTER);
    }

}
