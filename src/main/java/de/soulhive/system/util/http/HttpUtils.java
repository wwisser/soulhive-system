package de.soulhive.system.util.http;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HttpUtils {

    @SneakyThrows
    public void post(String requestUrl, String data, String contentType) {
        URL url = new URL(requestUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        byte[] out = data.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", contentType);
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }

}
