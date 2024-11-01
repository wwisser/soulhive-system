package de.soulhive.system.service.micro;

import com.vexsoftware.votifier.model.VotifierEvent;
import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnalyticsService extends Service {

    private static final DateFormat FORMAT_MONTHLY = new SimpleDateFormat("M:y");
    private static final DateFormat FORMAT_DAILY = new SimpleDateFormat("D:y");

    private static final String LABEL_COMMANDS = "commands.";
    private static final String LABEL_JOINS = "joins.";
    private static final String LABEL_NEWBIES = "newbies.";
    private static final String LABEL_VOTES = "votes.";
    private static final String LABEL_MAXPLAYERS = "maxplayers.";
    private static final String LABEL_HOSTNAME = "hostname.";

    private static final String LABEL_MONTLY = "monthly.";
    private static final String LABEL_DAILY = "daily.";

    private Config database = new Config(Settings.CONFIG_PATH, "analytics.yml");

    @Override
    public void initialize() {
        super.registerListener(new EventListenerHolder());
    }

    private void setProperty(final String label, final String value, final boolean monthly) {
        final Date date = new Date();
        String timeLabel = monthly ? LABEL_MONTLY : LABEL_DAILY;
        String timeKey = monthly ? FORMAT_MONTHLY.format(date) : FORMAT_DAILY.format(date);

        this.database.set(timeLabel + label + timeKey, value);
        this.database.saveFile();
    }

    private String getProperty(final String label, final boolean monthly) {
        final Date date = new Date();
        String timeLabel = monthly ? LABEL_MONTLY : LABEL_DAILY;
        String timeKey = monthly ? FORMAT_MONTHLY.format(date) : FORMAT_DAILY.format(date);

        if (!this.database.contains(timeLabel + label + timeKey)) {
            this.database.set(timeLabel + label + timeKey, "0");
            this.database.saveFile();
        }

        return this.database.getString(timeLabel + label + timeKey);
    }

    private class EventListenerHolder implements Listener {

        private AnalyticsService analyticsService = AnalyticsService.this;

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            int joinsDaily = Integer.valueOf(this.analyticsService.getProperty(LABEL_JOINS, false));
            int joinsMonthly = Integer.valueOf(this.analyticsService.getProperty(LABEL_JOINS, true));

            this.analyticsService.setProperty(LABEL_JOINS, String.valueOf(++joinsDaily), false);
            this.analyticsService.setProperty(LABEL_JOINS, String.valueOf(++joinsMonthly), true);

            final int size = Bukkit.getOnlinePlayers().size();

            final int maxMonthly = Integer.valueOf(EventListenerHolder.this.analyticsService.getProperty(LABEL_MAXPLAYERS, true));
            final int maxDaily = Integer.valueOf(EventListenerHolder.this.analyticsService.getProperty(LABEL_MAXPLAYERS, false));

            if (size > maxMonthly) {
                EventListenerHolder.this.analyticsService.setProperty(LABEL_MAXPLAYERS, String.valueOf(size), true);

            }
            if (size > maxDaily) {
                EventListenerHolder.this.analyticsService.setProperty(LABEL_MAXPLAYERS, String.valueOf(size), false);
            }

            if (event.getPlayer().hasPlayedBefore()) {
                return;
            }

            int newbiesDaily = Integer.valueOf(this.analyticsService.getProperty(LABEL_NEWBIES, false));
            int newbiesMonthly = Integer.valueOf(this.analyticsService.getProperty(LABEL_NEWBIES, true));

            this.analyticsService.setProperty(LABEL_NEWBIES, String.valueOf(++newbiesDaily), false);
            this.analyticsService.setProperty(LABEL_NEWBIES, String.valueOf(++newbiesMonthly), true);
        }

        @EventHandler
        public void onPlayerLoginEvent(PlayerLoginEvent event) {
            final String hostname = event.getHostname().toLowerCase().split(":")[0];
            final String label = hostname.replace(".", "*") + ".";

            int newbiesDaily = Integer.valueOf(this.analyticsService.getProperty(label, false));
            int newbiesMonthly = Integer.valueOf(this.analyticsService.getProperty(label, true));

            this.analyticsService.setProperty(LABEL_HOSTNAME + label, String.valueOf(++newbiesDaily), false);
            this.analyticsService.setProperty(LABEL_HOSTNAME + label, String.valueOf(++newbiesMonthly), true);
        }

        @EventHandler
        public void onVotifier(VotifierEvent event) {
            int votesDaily = Integer.valueOf(this.analyticsService.getProperty(LABEL_VOTES, false));
            int votesMonthly = Integer.valueOf(this.analyticsService.getProperty(LABEL_VOTES, true));

            this.analyticsService.setProperty(LABEL_VOTES, String.valueOf(++votesDaily), false);
            this.analyticsService.setProperty(LABEL_VOTES, String.valueOf(++votesMonthly), true);
        }

        @EventHandler
        public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
            final String command = event.getMessage().toLowerCase().replace("/", "").split(" ")[0] + ".";

            int commandDaily = Integer.valueOf(this.analyticsService.getProperty(LABEL_COMMANDS + command, false));
            int commandMonthly = Integer.valueOf(this.analyticsService.getProperty(LABEL_COMMANDS + command, true));

            this.analyticsService.setProperty(LABEL_COMMANDS + command, String.valueOf(++commandDaily), false);
            this.analyticsService.setProperty(LABEL_COMMANDS + command, String.valueOf(++commandMonthly), true);
        }

    }

}
