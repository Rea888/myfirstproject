package com.github.rviki;

import org.javacord.api.event.message.MessageCreateEvent;

import java.io.FileNotFoundException;

public class ActionWrapper {

    public WriterWrapper writerWrapper;
    public ScannerWrapper scannerWrapper;

    public ActionWrapper(WriterWrapper writerWrapper, ScannerWrapper scannerWrapper) {
        this.writerWrapper = writerWrapper;
        this.scannerWrapper = scannerWrapper;
    }

    public void receiveEvent(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (message.startsWith("!remindme get ")) {
            String result = getEvent(message);
            event.getChannel().sendMessage(result);
        } else if (message.startsWith("!remindme set")) {
            setEvent(message);
            event.getChannel().sendMessage("Message has been recorded.");
        }

    }

    String getEvent(String message) {
        String fileContent;
        try {
            scannerWrapper.reader();
            fileContent = scannerWrapper.getEventByDate(message.substring(message.indexOf(" get ") + 5, 24));

        } catch (FileNotFoundException e) {
            fileContent = "No one has wrote yet";

        } catch (Exception e) {
            fileContent = "Error occurred: " + e.getMessage();
            e.printStackTrace();
        }
        return fileContent;
    }


    void setEvent(String message) {
        message = message.substring(message.indexOf(" set ") + 5);
        String str = String.format("%s%n", message);
        writerWrapper.write(str);

    }
}
