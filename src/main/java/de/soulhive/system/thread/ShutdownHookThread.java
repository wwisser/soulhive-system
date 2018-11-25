package de.soulhive.system.thread;

import com.google.gson.JsonObject;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.task.impl.PlannedShutdownTask;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.http.HttpUtils;
import lombok.AllArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class ShutdownHookThread extends Thread {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");
    private static final Config WEBHOOK_CONFIG = new Config(Settings.CONFIG_PATH, "webhook.yml");

    private PlannedShutdownTask plannedShutdownTask;
    private final String url = WEBHOOK_CONFIG.getString("url");

    @Override
    public void run() {
        if (!this.plannedShutdownTask.isRegularShutdown()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("icon_emoji", ":exclamation:");
            jsonObject.addProperty(
                "text",
                "Unregulärer Serverstopp: " + DATE_FORMAT.format(new Date())
            );

            HttpUtils.post(this.url, jsonObject.toString(), "application/json");
        }
    }

}
