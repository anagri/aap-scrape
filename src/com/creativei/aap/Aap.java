package com.creativei.aap;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Aap {
    private final File outputFolder;
    private AapDonationPage aapDonationPage;
    private AapFormPage aapFormPage;
    private ChromeDriverService service;
    private RemoteWebDriver driver;

    public Aap(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    private void init() {
        if (service != null) return;

        service = new ChromeDriverService.Builder()
                .usingChromeDriverExecutable(new File(format("%s/bin/chromedriver", System.getenv("HOME"))))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        aapDonationPage = new AapDonationPage(driver, outputFolder);
        aapFormPage = new AapFormPage(driver, outputFolder);
    }

    public void scrapeDonations() {
        init();
        aapDonationPage.startOrContinueScrape();
    }

    public void scrapeMeta() {
        init();
        aapFormPage.scrapeMeta();
    }

    public void close() {
        driver.quit();
        service.stop();
    }
}
