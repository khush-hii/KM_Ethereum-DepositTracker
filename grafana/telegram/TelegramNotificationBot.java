package com.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramNotificationBot extends TelegramLongPollingBot {
    private final String botToken = "7332969409:AAEfD5QrAI4VBJ-FpQZ3Dx-sr7guEMaSRVU";
    private final String chatId = "{ok\":false,\"error_code\":401,\"description\":\"Unauthorized}";

    public String getBotUsername() {
        return "EthereumdeposittrackeekhushiBot";
    }

    public String getBotToken() {
        return botToken;
    }

    public void onUpdateReceived(Update update) {
        // Handle updates
    }

    public void sendNotification(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
        public static void main(String[] args) {
            // Initialize the bot
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new TelegramNotificationBot());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }}






