package com.creativei.aap;

import com.thoughtworks.selenium.Selenium;

import java.io.File;
import java.io.FilenameFilter;

public class AapDonationPage {
    private final Selenium selenium;
    private final File outputFolder;
    private int pageIndexToScrape;

    public AapDonationPage(Selenium selenium, File outputFolder) {
        this.selenium = selenium;
        this.outputFolder = outputFolder;
        pageIndexToScrape = nextIndex();
    }

    public void startOrContinueScrape() {
        selenium.open("/");
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
        int startingPageNumber = Integer.parseInt(selenium.getText("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr//span"));
        while (startingPageNumber + 10 < pageNumber) {
            int nextPage = startingPageNumber == 1 ? 11 : 12;
            clickAndWait(String.format("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr/td[%d]/a", nextPage));
            startingPageNumber = Integer.parseInt(selenium.getText("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody/tr//span"));
        }
        clickAndWait(String.format("//*[@id=\"MainContent_grdInnerData\"]/tbody/tr[52]/td/table/tbody//a[contains(@href, 'Page$%d')]", pageNumber));
        save(pageNumber);
    }

    private void save(int pageNumber) {
        Utils.writeToFile(selenium.getHtmlSource(), String.format("%s/html-%d.html", outputFolder.getAbsolutePath(), pageNumber));
        Utils.writeTextToFile(String.format("%s/text-%d.html", outputFolder.getAbsolutePath(), pageNumber), selenium.getBodyText());
    }

    private void clickAndWait(String element) {
        selenium.click(element);
        selenium.waitForPageToLoad("10000");
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
