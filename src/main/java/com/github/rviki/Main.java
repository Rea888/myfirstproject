package com.github.rviki;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = "OTM5MjMzOTQ2MzAxMDA1ODc0.Yf13xg.qakGx_MJ0XEX1nENFI9WG7QiaBw";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        String filePath = "C:\\Users\\mrakh\\IdeaProjects\\myfirstbot\\src\\input\\Eskuvo.txt";
        WriterWrapper writerWrapper;
        try {
            writerWrapper = new WriterWrapper(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        api.addMessageCreateListener(event -> {
            String message = event.getMessageContent();
            if (message.startsWith("!remindme ")) {
                String fileContent;
                try {
                    ScannerWrapper scannerWrapper = new ScannerWrapper(filePath);
                    fileContent = scannerWrapper.reader();

                } catch (FileNotFoundException e){
                    fileContent = "No one has wrote yet";

                } catch (Exception e) {
                    fileContent = "Error occurred: " + e.getMessage();
                    e.printStackTrace();
                }
                message = message.substring(message.indexOf(" ") + 1);
                String str = String.format("%s%n", message);
                writerWrapper.write(str);
                event.getChannel().sendMessage(fileContent);
            }

        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }


}
