package com.github.rviki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ScannerWrapper {

    public String filePath;
    public ArrayList<String> events;

    public ScannerWrapper(String filePath) {
        this.filePath = filePath;
    }

    public void reader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        String line = null;
        this.events = new ArrayList<>();
        while (scanner.hasNextLine()) {
            events.add(scanner.nextLine());
        }
        scanner.close();
    }

    public String randomGenerator() {
        Random random = new Random();
        int index = random.nextInt(events.size());
        return events.get(index);
    }

    public String getEventByDate(String date) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).startsWith(date)) {
                return events.get(i);
            }
        }
        return "Not found";
    }
}