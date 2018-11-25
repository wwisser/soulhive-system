package de.soulhive.system.util.http;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HttpUtils {

    @SneakyThrows
    public void post(String requestUrl, String text, String contentType) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);

        URL url = new URL(requestUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        byte[] out = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", contentType);
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }

}
