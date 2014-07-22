/*
 * File created on Jul 12, 2014 
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
package edu.vt.cs.irwin.etdscraper.retriever.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.cs.irwin.etdscraper.Configuration;
import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.Role;
import edu.vt.cs.irwin.etdscraper.domain.EtdEntity;
import edu.vt.cs.irwin.etdscraper.retriever.EtdSource;
import edu.vt.cs.irwin.etdscraper.service.PersonFactory;

/**
 * An {@link EtdSource} that loads ETDs out of an Excel document.
 *
 * @author Michael Irwin
 */
public class ExcelEtdSource implements EtdSource {
  
  private static final Logger logger = 
      LoggerFactory.getLogger(ExcelEtdSource.class);
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
  
  @Inject
  private Configuration configuration;
  
  @Inject
  private PersonFactory personFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSourceName() {
    return "Excel Loader";
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Etd> retrieveEtds() {
    List<Etd> etds = new ArrayList<>();
    try {
      logger.info("Starting to load ETDs from Excel document");
      
      File file = new File(configuration.getExcelFile());
      InputStream fis = new FileInputStream(file);
      XSSFWorkbook wb = new XSSFWorkbook(fis);
      Sheet sheet = wb.getSheetAt(0);
      
      int rowNumber = 0;
      for (Row row : sheet) {
        if (rowNumber++ == 0) continue;
        String name = getCellValue(row.getCell(0));
        if (name.isEmpty()) {
          logger.info("Skipping row " + rowNumber + " because there is no name");
          continue;
        }
        if (name.equals("count")) {
          logger.info("Skipping row " + rowNumber + " as it is a count row");
          continue;
        }
        
        EtdEntity etd = new EtdEntity();
        etd.addContributor(personFactory.createPerson(etd, name, Role.STUDENT));
        etd.setDate(sdf.parse(getCellValue(row.getCell(1))));
        
        String advisor = getCellValue(row.getCell(2));
        if (!advisor.isEmpty()) {
          etd.addContributor(personFactory.createPerson(etd, advisor, Role.CHAIR));
        }
        
        etd.setTitle(getCellValue(row.getCell(3)));
        
        etds.add(etd);
      }
      
      logger.info("Extracted " + etds.size() + " ETDs");
      return etds;
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private String getCellValue(Cell cell) {
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        return cell.getRichStringCellValue().getString();
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue().toString();
        } else {
          return ((Double) cell.getNumericCellValue()).toString();
        }
      case Cell.CELL_TYPE_BOOLEAN:
        return ((Boolean) cell.getBooleanCellValue()).toString();
      case Cell.CELL_TYPE_BLANK :
        return "";
    }
    throw new UnsupportedOperationException("Don't know how to work with type: " 
        + cell.getCellType());
  }
}
