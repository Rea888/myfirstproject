package com.github.rviki;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ActionWrapper {


    public WriterWrapper writerWrapper;
    public ScannerWrapper scannerWrapper;
    public TextChannel channel;


    public ActionWrapper(WriterWrapper writerWrapper, ScannerWrapper scannerWrapper) {
        this.writerWrapper = writerWrapper;
        this.scannerWrapper = scannerWrapper;
    }

    public void receiveEvent(MessageCreateEvent event) {
        channel = event.getChannel();
        String message = event.getMessageContent();
        String[] messageSplit = message.split(" ");
        String sendMessage = null;
        if (messageSplit[1].equals("get")) {
            sendMessage = getEvent(messageSplit);

        } else if (messageSplit[1].equals("set")) {
            setEvent(messageSplit);
            sendMessage = "Message has been recorded.";

        } else if (messageSplit[1].equals("random")) {
            sendMessage = getRandomEvent();

        } else if (messageSplit[1].equals("help")) {
            getHelp();

        } else if (messageSplit[0].equals("!remindme")) {
            displayHelp(messageSplit);

        }
        event.getChannel().sendMessage(sendMessage);
    }

    String getEvent(String[] messageSplit) {
        String fileContent;
        try {
            scannerWrapper.reader();
            fileContent = scannerWrapper.getEventByDate(messageSplit[2]);

        } catch (FileNotFoundException e) {
            fileContent = "No one has wrote yet";

        } catch (Exception e) {
            fileContent = "Error occurred: " + e.getMessage();
            e.printStackTrace();
        }
        return fileContent;
    }

    void setEvent(String[] messageSplit) {
        StringBuilder messageToStore= new StringBuilder(messageSplit[2]);
        for (int i = 3; i < messageSplit.length; i++) {
            messageToStore.append(" ").append(messageSplit[i]);
        }
        String str = String.format("%s%n", messageToStore);
        writerWrapper.write(str);
        System.out.println(Arrays.toString(messageSplit));
        System.out.println(messageSplit[2]);
    }

    String getRandomEvent() {
        String randomContent = null;
        try {
            scannerWrapper.reader();
            randomContent = scannerWrapper.randomGenerator();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return randomContent;
    }

    void getHelp() {
        MessageBuilder helpMessage = new MessageBuilder();
        helpMessage.appendCode("java", " !remindme set 2022-02-14 valentin nap");
        helpMessage.appendCode("java", " !remindme get 2022-02-14");
        helpMessage.appendCode("java", " !remindme search valentin");
        helpMessage.appendCode("java", " !remindme random");
        helpMessage.send(channel);
    }

    void displayHelp(String[] messageSplit) {
        MessageBuilder displayHelp = new MessageBuilder();
        displayHelp.append("Unrecognised option: " + messageSplit[1]);
        displayHelp.appendNewLine();
        displayHelp.append(" For detailed help use: !remindme help ");
        displayHelp.send(channel);
    }
}
