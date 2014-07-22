/*
 * File created on Jul 21, 2014 
 *
 * Copyright 2008-2013 Nerdwin15
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package edu.vt.cs.irwin.etdscraper;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the command-line options.
 *
 * @author Michael Irwin
 */
public class Configuration {
  
  private static final String DEFAULT_JDBC_URL = "jdbc:sqlite:etd.db";
  
  private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
  
  private static Configuration INSTANCE;
  private static Options options;
  
  private boolean displayHelp;
  private String jdbcUrl;
  private String excelFile;
  private String nameFixFileLocation;
  
  private Configuration(String[] args) {
    try {
      options = getOptions();
      CommandLine cli = (new BasicParser()).parse(options, args);
      
      displayHelp = cli.hasOption("help");
      jdbcUrl = cli.hasOption("jdbcUrl") ? 
          cli.getOptionValue("jdbcUrl") : DEFAULT_JDBC_URL;
      nameFixFileLocation = cli.hasOption("nameFixFile") ? 
          cli.getOptionValue("nameFixFile") : null;
      excelFile = cli.hasOption("excelFile") ? 
          cli.getOptionValue("excelFile") : null;
          
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static Configuration setup(String[] args) {
    INSTANCE = new Configuration(args);
    if (INSTANCE.displayHelp) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.setWidth(100);
      formatter.printHelp( "etd-scraper", options );
      return null;
    }    
    logger.info("Using jdbc URL: " + INSTANCE.getJdbcUrl());
    return INSTANCE;
  }

  @SuppressWarnings("static-access")
  private Options getOptions() {
    Options options = new Options();
    
    options.addOption(new Option("help", "prints this message"));
    options.addOption(OptionBuilder.withArgName("jdbcUrl")
        .hasArg()
        .withDescription("URL for JDBC connection. Examples include:\n" +
            "- jdbc:sqlite:etd.db\n" +
            "- jdbc:mysql://localhost/dbName?username=root&password=Passw0rd\n" +
            "- jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true")
        .create("jdbcUrl"));
    options.addOption(OptionBuilder.withArgName("nameFixFile")
        .hasArg()
        .withDescription("File path to definitions for name fixing rules")
        .create("nameFixFile"));
    options.addOption(OptionBuilder.withArgName("excelFile")
        .hasArg()
        .withDescription("Path to file that contains additional ETD data")
        .create("excelFile"));
    
    return options;
  }
  
  /**
   * Gets the {@code excelFile} property.
   */
  public String getExcelFile() {
    return excelFile;
  }
  
  /**
   * Gets the {@code jdbcUrl} property.
   */
  public String getJdbcUrl() {
    return jdbcUrl;
  }
  
  /**
   * Gets the {@code nameFixFileLocation} property.
   */
  public String getNameFixFileLocation() {
    return nameFixFileLocation;
  }
  
}
