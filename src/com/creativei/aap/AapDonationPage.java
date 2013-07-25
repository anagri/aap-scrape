package com.creativei.aap;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FilenameFilter;

import static java.lang.String.format;

public class AapDonationPage {
    private final RemoteWebDriver driver;
    private final File outputFolder;
    private int pageIndexToScrape;

    public AapDonationPage(RemoteWebDriver driver, File outputFolder) {
        this.driver = driver;
        this.outputFolder = outputFolder;
        pageIndexToScrape = nextIndex();
    }

    public void startOrContinueScrape() {
        driver.get("http://internal.aamaadmiparty.org/donate/donation_list.aspx");
        loop();
    }

    private void loop() {
        try {
            while (true) {
                openAndSave(pageIndexToScrape);
                pageIndexToScrape++;
            }
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }


    private void openAndSave(int pageNumber) {
        int startingPageNumber = Integer.parseInt(driver.findElementByXPath("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr//span").getText());
        while (startingPageNumber + 10 < pageNumber) {
            int nextPage = startingPageNumber == 1 ? 11 : 12;
            driver.findElementByXPath(format("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr/td[%d]/a", nextPage)).click();
            startingPageNumber = Integer.parseInt(driver.findElementByXPath("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr//span").getText());
        }
        driver.findElementByXPath(format("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody//a[contains(@href, 'Page$%d')]", pageNumber)).click();
        save(pageNumber);
    }

    private void save(int pageNumber) {
        Utils.writeToFile(driver.getPageSource(), format("%s/html-%d.html", outputFolder.getAbsolutePath(), pageNumber));
        Utils.writeTextToFile(format("%s/text-%d.html", outputFolder.getAbsolutePath(), pageNumber), driver.findElementByTagName("body").getText());
    }

    private int nextIndex() {
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
