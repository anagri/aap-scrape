package com.creativei.aap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) {
        String cwd = System.getProperty("user.dir");
        File outputFolder = new File(format("%s/output", cwd));
        if (outputFolder.exists()) {
            outputFolder.renameTo(new File(format("%s/output-%s", cwd, new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date()))));
            outputFolder = new File(format("%s/output", cwd));
        }
        outputFolder.mkdir();


        Aap aap = new Aap(outputFolder);
        aap.scrapeDonations();
        aap.scrapeMeta();
        aap.close();
    }
}
