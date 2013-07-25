package com.creativei.aap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class DonationParser {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d yyyy hh:mma");
    private final File file;

    public DonationParser(String fileName) {
        file = new File(fileName);
    }

    public List<Donation> results() {
        try {
            Document document = Jsoup.parse(file, "UTF-8", "http://www.example.com");
            Elements rows = document.select("table[id=MainContent_grdInnerData]").select("tr[align=left]");
            List<Donation> donations = new ArrayList<Donation>();
            for (Element row : rows) {
                if (row.select("td").first() == null || !row.select("td").first().text().matches("\\d+")) continue;
                Elements datum = row.select("td");
                int id = Integer.parseInt(datum.get(0).text());
                String name = datum.get(1).text();
                String country = datum.get(2).text();
                String state = datum.get(3).text();
                String district = datum.get(4).text();
                String transactionId = datum.get(5).text();
                String amountStr = datum.get(6).text();
                Integer amount = tryParseInt(amountStr);
                String dateStr = datum.get(7).text();
                Date date = tryParseDate(dateStr);
                donations.add(new Donation(id, name, country, state, district, transactionId, amountStr, amount, dateStr, date));
            }
            return donations;
        } catch (Exception e) {
            System.err.println(format("Cannot process file %s, Error %s", file.getName(), e.getMessage()));
            return Collections.EMPTY_LIST;
        }
    }

    private Date tryParseDate(String dateStr) {
        try {
            return new Date(DATE_FORMAT.parse(dateStr).getTime());
        } catch (ParseException e) {
        }
        return null;
    }

    private int tryParseInt(String text) {
        if (text.matches("\\d+"))
            return Integer.parseInt(text);
        return -1;
    }

    public static void main(String[] args) {
        String dbHost = "localhost";
        String dbPort = "3306";
        String dbName = "aap_donations";
        String dbUser = "root";
        String dbPassword = "";
        String cwd = System.getProperty("user.dir");

        MySQL mySQL = new MySQL(dbHost, dbPort, dbName, dbUser, dbPassword);

        File outputDir = new File(format("%s/output", cwd));
        int toIndex = Utils.fileIndex(outputDir);

        for (int i = 1; i <= toIndex; i++) {
            String fileName = format("%s/output/html-%d.html", cwd, i);
            List<Donation> donations = new DonationParser(fileName).results();
            for (Donation donation : donations) {
                mySQL.save(donation);
            }
        }
        mySQL.close();
    }
}
