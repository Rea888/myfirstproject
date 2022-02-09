package com.github.rviki;

import java.io.FileWriter;
import java.io.IOException;

public class WriterWrapper {

    public String filePath;

    public WriterWrapper(String filePath) {
        this.filePath = filePath;
    }


    public void write(String writeMessage) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(writeMessage);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
