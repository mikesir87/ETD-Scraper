/*
 * File created on Jul 11, 2014 
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
package edu.vt.cs.irwin.etdscraper.retriever.scholar;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.vt.cs.irwin.etdscraper.Degree;
import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.Role;
import edu.vt.cs.irwin.etdscraper.domain.EtdEntity;
import edu.vt.cs.irwin.etdscraper.service.KeywordFactory;
import edu.vt.cs.irwin.etdscraper.service.PageFetcher;
import edu.vt.cs.irwin.etdscraper.service.PersonFactory;

/**
 * A {@link ScholarEtdExtractor} that uses Jsoup to traverse the DOM.
 *
 * @author Michael Irwin
 */
public class JsoupScholarEtdExtractor implements ScholarEtdExtractor {

  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  
  @Inject
  private PageFetcher pageFetcher;

  @Inject
  private KeywordFactory keywordFactory;
  
  @Inject
  private PersonFactory personFactory;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Etd extractEtd(String url) {
    try {
      String pageBody = pageFetcher.getPageContents(url);
      EtdEntity etd = new EtdEntity();
      etd.setUrl(new URL(url));
      
      Document doc = Jsoup.parse(pageBody);
      Element table = doc.select("blockquote > div > table").first();
      for (Element row : table.select("tbody tr")) {
        String key = row.select("th, td").first().text().trim();
        Element value = row.select("td").first();
        augmentEtd(etd, key, value);
      }
      return etd;
    } catch (Exception e) {
      throw new RuntimeException("Error working with " + url, e);
    }
  }
  
  public static void main(String[] args) {
    String key = "Abstract\n   Something is here";
    System.out.println(key.substring(8).trim());
  }
  
  private void augmentEtd(EtdEntity etd, String key, Element value) {
    try {
      switch (key) {
        case "Author":
          etd.addContributor(personFactory.createPerson(etd, value.text().trim(), Role.STUDENT));
          return;
        case "URN" :
          etd.setUrn(value.text().trim());
          return;
        case "Title" :
          etd.setTitle(value.text().trim());
          return;
        case "Degree" :
          etd.setDegree((value.text().trim().equals("PhD")) ? Degree.PHD : Degree.MS);
          return;
        case "Department" :
          etd.setDepartment(value.text().trim());
          return;
        case "Date of Defense" :
          etd.setDate(sdf.parse(value.text().trim()));
          return;
        case "Keywords" :
          for (Element element : value.select("li")) {
            etd.addKeyword(keywordFactory.generateKeyword(element.text().trim()));
          }
          return;
        case "Advisory Committee" :
          extractCommittee(value, etd);
          return;
      }
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    
    if (key.startsWith("Abstract")) {
      etd.setEtdAbstract(key.substring(8));
    }
  }
  
  private void extractCommittee(Element element, EtdEntity etd) {
    for (Element row : element.select("table tr")) {
      if (row.select("td").size() == 0)
        continue;
      
      String name = row.select("td:eq(0)").text().trim();
      if (name.startsWith("No Advisors"))
        return;
      
      Role role = getRole(row.select("td:eq(1)").text().trim());
      etd.addContributor(personFactory.createPerson(etd, name, role));
    }
  }
  
  private Role getRole(String roleName) {
    switch (roleName) {
      case "Committee Chair" :
        return Role.CHAIR;
      case "Committee Member" :
        return Role.MEMBER;
      case "Committee Co-Chair" :
        return Role.COCHAIR;
      case "":
        return Role.MEMBER;
      default :
        throw new RuntimeException("Don't know how to work with role " + roleName);
    }
  }
}
