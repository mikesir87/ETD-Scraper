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
package edu.vt.cs.irwin.etdscraper.retriever.vtech;

import java.net.MalformedURLException;
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
import edu.vt.cs.irwin.etdscraper.service.PersonFactory;

/**
 * An {@link VtechEtdExtractor} that uses Jsoup to parse the DOM and extract
 * the details.
 *
 * @author Michael Irwin
 */
public class JsoupVtechEtdExtractor implements VtechEtdExtractor {
  
  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  
  @Inject
  private KeywordFactory keywordFactory;
  
  @Inject
  private PersonFactory personFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public Etd extractEtd(String pageBody) {
    Document doc = Jsoup.parse(pageBody);
    
    EtdEntity etd = new EtdEntity();
    for (Element element : doc.select(".detailtable tbody tr")) {
      decorateEntity(etd, element);
    }
    
    return etd;
  }
  
  private void decorateEntity(EtdEntity etd, Element element) {
    String key = element.select("td:eq(0)").text();
    String value = element.select("td:eq(1)").text();

    try {
      switch (key) {
        case "dc.date.issued":
          etd.setDate(sdf.parse(value));
          break;
        case "dc.identifier.other" :
          etd.setUrn(value);
          break;
        case "dc.identifier.uri" :
          etd.setUrl(new URL(value));
          break;
        case "dc.description.abstract":
          etd.setEtdAbstract(value);
          break;
        case "dc.title" :
          etd.setTitle(value);
          break;
        case "thesis.degree.level" :
          etd.setDegree((value.contains("masters") ? Degree.MS : Degree.PHD));
          break;
        case "dc.contributor.department":
          etd.setDepartment(value);
          break;
        case "dc.subject" :
          etd.addKeyword(keywordFactory.generateKeyword(value));
          break;
        case "dc.contributor.committeechair" :
          etd.addContributor(personFactory.createPerson(etd, value, Role.CHAIR));
          break;
        case "dc.contributor.committeemember" :
          etd.addContributor(personFactory.createPerson(etd, value, Role.MEMBER));
          break;
        case "dc.contributor.author" :
          etd.addContributor(personFactory.createPerson(etd, value, Role.STUDENT));
          break;
      }
    } catch (ParseException | MalformedURLException e) {
      throw new RuntimeException("Error with '" + key + "' : '" + value + "'", e);
    }
  }
  
}
