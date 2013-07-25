package com.creativei.aap;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public class Aap {
    private Selenium selenium;
    private AapDonationPage aapDonationPage;
    private final AapFormPage aapFormPage;

    public Aap(File outputFolder) {
        String baseUrl = "http://internal.aamaadmiparty.org/donate/donation_list.aspx";
        selenium = new WebDriverBackedSelenium(new FirefoxDriver(), baseUrl);
        aapDonationPage = new AapDonationPage(selenium, outputFolder);
        aapFormPage = new AapFormPage(selenium, outputFolder);
    }

    public void scrapeDonations() {
        aapDonationPage.startOrContinueScrape();
    }

    public void scrapeMeta() {
        aapFormPage.scrapeMeta();
    }

    public void close() {
        selenium.stop();
    }
}
