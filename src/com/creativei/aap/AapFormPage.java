package com.creativei.aap;

import com.thoughtworks.selenium.Selenium;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static com.creativei.aap.Utils.writeTextToFile;
import static java.lang.String.format;

public class AapFormPage {
    private final Selenium selenium;
    private final File outputFolder;

    public AapFormPage(Selenium selenium, File outputFolder) {
        this.selenium = selenium;
        this.outputFolder = outputFolder;
    }

    public void scrapeMeta() {
        selenium.open("http://internal.aamaadmiparty.org/donate/Donate.aspx?master=blank");
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(format("%s/refdata/states.txt", outputFolder.getAbsolutePath())));
            String state = null;
            while ((state = file.readLine()) != null) {
                selenium.select("ctl00$MainContent$ddlState", state);
                selenium.waitForPageToLoad("1000");
                String[] districts = selenium.getSelectOptions("//select[@name='ctl00$MainContent$ddlDistrict']");
                writeTextToFile(format("%s/refdata/%s.txt", outputFolder.getAbsolutePath(), state), StringUtils.join(districts, "\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

