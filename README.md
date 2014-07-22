ETD Scraper
================

This repository provides a code base for a robust ETD Scraper.

## Usage

To run, simply invoke

``` java -jar etdScraper.jar [-help | -jdbcUrl <URL> | -excelFile <location> | -nameFixFile <location>]```

### Command-line Options

| Option | Description | Example |
|--------|-------------|---------|
| help | Displays the help text | N/A |
| jdbcUrl | A JDBC URL that defines where you want to save the output (defaults to a local SQLite table named etd.db in the current folder) | jdbc:mysql:localhost/dbName?username=user&password=password |
| excelFile | Absolute file location to an Excel document with extra ETDs to be included (defaults to none) | /some/path/to/excel.xlsx |
| nameFixFile | Absolute file location to a name fix file source (more info below) | /some/path/to/nameFix.xml

### Name Fix Source

The name fix allows you to resolve names such as "Irwin, Michael", "Michael Irwin", and "Michael S Irwin" to a single form of "Irwin, Michael S".  The following XML structure would provide this translation:

```
<?xml version="1.0"?>
<names>
  <name>
    <correct>Irwin, Michael S.</correct>
    <wrong>Irwin, Michael</wrong>
    <wrong>Michael Irwin</wrong>
    <wrong>Michael S Irwin</wrong>
  </name>
</names>
```

You can have as many ```<name>``` listings as needed.

**JSON format coming soon**

## Building from Source

This project utilizes Apache Maven for dependency management and building (http://maven.apache.org/).  You will need Maven installed on your machine, then simply run:

```
mvn clean install
```

## Upcoming Enhancements

- Override the departments being scraped to allow the scraper to work for any organization
- Allow JSON formatting for name resolving/fixing
- More comprehensive test cases (code is written to be tested, just haven't plugged them in yet)

