package de.soulhive.system.util;

import com.google.gson.JsonObject;
import de.soulhive.system.setting.Settings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlackUtils {

    private static final Config WEB_HOOK_CONFIG = new Config(Settings.CONFIG_PATH, "webhook.yml");

    public void postMessage(final String message, final String icon, final String webhook) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("icon_emoji", ":" + icon + ":");
        jsonObject.addProperty(
            "text",
            message
        );

        HttpUtils.post(WEB_HOOK_CONFIG.getString(webhook), jsonObject.toString(), "application/json");
    }

}
