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

    public void receiveEvent(MessageCreateEvent event){
        String message = event.getMessageContent();
        if (message.startsWith("!remindme ")) {
            String fileContent;
            try {
                scannerWrapper.reader();
                fileContent= scannerWrapper.getEventByDate(message.substring(message.indexOf(" ")+1,20));

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
    }
}
