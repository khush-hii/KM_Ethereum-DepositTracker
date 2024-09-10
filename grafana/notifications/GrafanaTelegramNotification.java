//http://localhost:3005/api/alert-notifications


//grafana id:   https://khushimittal2811.grafana.net/dashboards

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GrafanaTelegramNotification {

    private static final String GRAFANA_URL = "http://localhost:3005/api/alert-notifications";
    private static final String GRAFANA_USERNAME = "admin";
    private static final String GRAFANA_PASSWORD = "admin";

    // Telegram bot credentials
    private static final String TELEGRAM_BOT_TOKEN = "7332969409:AAEfD5QrAI4VBJ-FpQZ3Dx-sr7guEMaSRVU";
    private static final String TELEGRAM_CHAT_ID = "{\"ok\":false,\"error_code\":401,\"description\":\"Unauthorized\"}";

    public static void main(String[] args) {
        try {
            // Create the payload for the Telegram notification channel
            String jsonPayload = "{\n" +
                    "  \"name\": \"Telegram\",\n" +
                    "  \"type\": \"telegram\",\n" +
                    "  \"settings\": {\n" +
                    "    \"botToken\": \"" + TELEGRAM_BOT_TOKEN + "\",\n" +
                    "    \"chatId\": \"" + TELEGRAM_CHAT_ID + "\",\n" +
                    "    \"parseMode\": \"HTML\"\n" +
                    "  },\n" +
                    "  \"isDefault\": true\n" +
                    "}";

            // Create the URL object
            URL url = new URL(GRAFANA_URL);

            // Open a connection to the Grafana API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set up HTTP request properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " +
                    java.util.Base64.getEncoder().encodeToString((GRAFANA_USERNAME + ":" + GRAFANA_PASSWORD).getBytes()));
            connection.setDoOutput(true);

            // Send the JSON payload
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(jsonPayload.getBytes());
                outputStream.flush();
            }

            // Check for the response code from Grafana
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Telegram notification channel created successfully.");
            } else {
                System.out.println("Failed to create notification channel. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            System.err.println("Error setting up Telegram notification: " + e.getMessage());
        }
    }
}
