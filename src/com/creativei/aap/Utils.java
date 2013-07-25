package com.creativei.aap;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class Utils {
    public static void writeToFile(String text, String fileName) {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTextToFile(String fileName, String text) {
        try {
            File page = new File(fileName);
            FileWriter writer = new FileWriter(page, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int fileIndex(File outputFolder) {
        File[] htmls = outputFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith("html");
            }
        });
        int max = 1;
        for (File html : htmls) {
            max = Math.max(max, Integer.parseInt(html.getName().split("-")[1].replace(".html", "")));
        }
        return max;
    }
}
