package de.skydust.system.motd;

import de.skydust.system.PluginLauncher;
import de.skydust.system.motd.commands.CommandMotd;
import de.skydust.system.motd.listeners.ServerListPingListener;
import de.skydust.system.service.Service;
import de.skydust.system.util.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MotdService implements Service {

    private static final String FILE_NAME = "motd.yml";

    private static final String KEY_HEADER = "header";
    private static final String KEY_FOOTER = "footer";

    private Config config;

    public MotdService() {
        this.config = new Config(PluginLauncher.CONFIG_PATH, FILE_NAME);
        this.config.setDefault(KEY_HEADER, "§fSkyDust.de");
        this.config.setDefault(KEY_FOOTER, "§fhuh, motd?");
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

    @Override
    public Set<Listener> getListeners() {
        return Collections.singleton(new ServerListPingListener(this));
    }

    @Override
    public Map<String, CommandExecutor> getCommands() {
        return Collections.singletonMap("motd", new CommandMotd(this));
    }

}
