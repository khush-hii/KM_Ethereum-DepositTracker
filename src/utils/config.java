package com.example.utils;

import io.github.cdimascio.dotenv.Dotenv;
import static io.reactivex.schedulers.Schedulers.io;

public class config {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getAlchemyApiKey() {
        return dotenv.get("ALCHEMY_API_KEY");
    }

    public static String getMongoUri() {
        return dotenv.get("MONGO_URI");
    }

    public static int getEthBlockFrom() {
        return Integer.parseInt(dotenv.get("ETH_BLOCK_FROM"));
    }

    public static String getTelegramNotificationsBotToken() {
        return dotenv.get("TELEGRAM_NOTIFICATIONS_BOT_TOKEN");
    }

    public static String getTelegramNotificationsChatId() {
        return dotenv.get("TELEGRAM_NOTIFICATIONS_CHAT_ID");
    }

    public static Dotenv getDotenv() {
        return dotenv;
    }
}
