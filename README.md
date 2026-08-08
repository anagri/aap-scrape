

# AAP Scrape & Visualization

A web scraping and data visualization tool for analyzing Aam Aadmi Party (AAP) donation records. The project automates the extraction of donation data from a web portal, stores it in a MySQL database, and renders interactive district-wise choropleth maps using D3.js.

## Features
- **Automated Web Scraping**: Uses Selenium WebDriver with ChromeDriver to navigate, paginate, and save donation list pages.
- **HTML Parsing**: Leverages Jsoup to extract structured donation data (donor name, location, transaction ID, amount, date) from saved HTML snapshots.
- **Database Storage**: Imports and normalizes parsed data into a MySQL database for efficient querying.
- **Interactive Visualization**: D3.js-powered maps displaying total donation amounts and donor counts by Indian district.

## Prerequisites
- Java 8+
- MySQL Server
- Google Chrome & ChromeDriver
- D3.js v3 (included in `public/`)
- `india-districts.svg` vector map file
- Required Java libraries: Selenium WebDriver, Jsoup, MySQL Connector/J

## Setup & Installation

### 1. Database Setup
Initialize the database and table schema using the provided SQL script:
```bash
mysql -u <username> -p < resources/donations.sql
```

### 2. Configure Dependencies
Ensure the following libraries are in your classpath (via Maven, Gradle, or manual JAR inclusion):
- `selenium-java`
- `jsoup`
- `mysql-connector-java`

### 3. ChromeDriver Configuration
The scraper expects ChromeDriver to be located at `~/bin/chromedriver` (defined in `Aap.java`). Adjust the path in the code if your setup differs.

## Usage

### 1. Scrape Data
Execute `Main.java` to start the scraping process. It will navigate to the source donation list, handle pagination, and save raw HTML and text dumps to the `output/` directory.
```java
// Run src/com/creativei/aap/Main.java
```

### 2. Parse & Import to MySQL
Update the database credentials in `DonationParser.java` if needed. Run its `main` method to parse the generated HTML files and insert the cleaned records into the `donations` table.
```java
// Run src/com/creativei/aap/DonationParser.java
```

### 3. Generate Visualization Data
Run the aggregation query to export district-level statistics into a CSV file:
```bash
mysql -u <username> -p aap_donations < resources/materialized_district_level_view.sql
```
Move the generated `materialized_district_level_view.csv` to the `public/` directory.

### 4. View Visualizations
Ensure `india-districts.svg`, `materialized_district_level_view.csv`, `d3.v3.min.js`, and `map-aap.js` are in the `public/` folder. Open the following files in a browser:
- `public/amount.html` - Visualizes total donation amounts per district.
- `public/donors.html` - Visualizes the number of donors per district.

## Project Structure
```
├── public/
│   ├── amount.html          # Frontend: Total Amount by District
│   ├── donors.html          # Frontend: Donor Count by District
│   ├── map-aap.js           # D3.js choropleth logic
│   └── d3.v3.min.js         # D3 library
├── resources/
│   ├── donations.sql        # DB schema creation
│   └── materialized_district_level_view.sql # Data aggregation & CSV export
├── src/
│   └── com/creativei/aap/
│       ├── Aap.java         # Scraper controller & Selenium setup
│       ├── AapDonationPage.java # Page object for donation list
│       ├── AapFormPage.java     # Page object for metadata scraping
│       ├── Donation.java        # Data model (POJO)
│       ├── DonationParser.java  # Jsoup parser & DB importer
│       ├── Main.java            # Application entry point
│       ├── MySQL.java           # JDBC connection & query helper
│       └── Utils.java           # File I/O utilities
└── output/                  # Directory for scraped HTML/Text files
```

## Notes
- **Target URLs**: The scraper targets `http://internal.aamaadmiparty.org/...`. These endpoints may be legacy or require updates if the source site changes.
- **Map Requirements**: The D3 visualization expects the `india-districts.svg` file to have `title` attributes on district `<path>` elements that match the district names in the database.
- **Pagination**: The scraping logic in `AapDonationPage.java` is tightly coupled to the DOM structure of the original donation list page. Adjust XPath selectors if the UI layout changes.
- **MySQL `INTO OUTFILE`**: The materialization query writes directly to the MySQL server's filesystem. You may need to adjust the `secure_file_priv` directory or move the generated CSV manually to the `public/` folder.
