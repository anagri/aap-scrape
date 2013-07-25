package com.creativei.aap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import static com.creativei.aap.Utils.writeTextToFile;
import static java.lang.String.format;

public class AapFormPage {
    private final RemoteWebDriver driver;
    private final File outputFolder;

    public AapFormPage(RemoteWebDriver driver, File outputFolder) {
        this.driver = driver;
        this.outputFolder = outputFolder;
    }

    public void scrapeMeta() {
        driver.get("http://internal.aamaadmiparty.org/donate/Donate.aspx?master=blank");
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(format("%s/refdata/states.txt", outputFolder.getAbsolutePath())));
            String state = null;
            while ((state = file.readLine()) != null) {
                new Select(driver.findElementByName("ctl00$MainContent$ddlState")).selectByVisibleText(state);

                List<WebElement> options = new Select(driver.findElementByXPath("//select[@name='ctl00$MainContent$ddlDistrict']")).getOptions();
                StringBuilder districts = new StringBuilder();
                for (WebElement option : options) {
                    districts.append(option.getText()).append("\n");
                }
                writeTextToFile(format("%s/refdata/%s.txt", outputFolder.getAbsolutePath(), state), districts.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

