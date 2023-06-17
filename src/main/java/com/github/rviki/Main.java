package com.github.rviki;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;


public class Main {

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = "***********";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        String filePath = "C:\\Users\\mrakh\\IdeaProjects\\myfirstbot\\src\\input\\Eskuvo.txt";
        ActionWrapper actionWrapper;
        try {
            actionWrapper = new ActionWrapper(new WriterWrapper(filePath), new ScannerWrapper(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        api.addMessageCreateListener(actionWrapper::receiveEvent);
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}
