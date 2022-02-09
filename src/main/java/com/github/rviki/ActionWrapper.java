package com.github.rviki;

import org.javacord.api.event.Event;

import java.io.FileNotFoundException;

public class ActionWrapper {

    public String message;
    public WriterWrapper writerWrapper;
    public ScannerWrapper scannerWrapper;

    public ActionWrapper(WriterWrapper writerWrapper, ScannerWrapper scannerWrapper) {
        this.writerWrapper = writerWrapper;
        this.scannerWrapper = scannerWrapper;
    }

    public void receiveEvent(Event event){
        String message = event.getMessageContent();
        if (message.startsWith("!remindme ")) {
            String fileContent;
            try {
                ScannerWrapper scannerWrapper = new ScannerWrapper(filePath);
                scannerWrapper.reader();
                fileContent= scannerWrapper.getEventByDate(message.substring(message.indexOf(" ")+1,20));

                //fileContent= scannerWrapper.getEventByDate("2022-05-05");
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
