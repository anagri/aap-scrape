package com.creativei.aap;

import java.io.File;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) {
        String cwd = System.getProperty("user.dir");
        File outputFolder = new File(format("%s/output", cwd));
        if (!outputFolder.exists()) outputFolder.mkdir();

        Aap aap = new Aap(outputFolder);
        aap.scrapeDonations();
        aap.close();
    }
}
