package com.github.rviki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerWrapper {

    public String filePath;

    public ScannerWrapper(String filePath) {
        this.filePath = filePath;
    }

    public String reader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        int lineNumber = 1;
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {

            lines.add(scanner.nextLine());
            lineNumber++;

        }
        scanner.close();
        return String.valueOf(lines.get(lines.size()-2));
    }
}