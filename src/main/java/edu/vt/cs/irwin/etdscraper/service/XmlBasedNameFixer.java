/*
 * File created on Jul 22, 2014 
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
package edu.vt.cs.irwin.etdscraper.service;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * A {@link NameFixer} that uses XML to define the rules.
 * 
 * The rules should be defined in an XML structure such as:
 * &gt;names&lt;
 *   &gt;name&lt;
 *     &gt;correct&lt;Irwin, Michael S.&gt;/correct&lt;
 *     &gt;wrong&lt;Irwin, Michael&gt;/wrong&lt;
 *     &gt;wrong&lt;Irwin, M S&gt;/wrong&lt;
 *   &gt;/name&lt;
 * &gt;/name&lt;
 * 
 * Schema is available in src/main/resources/nameFixerSchema.xsd
 *
 * @author Michael Irwin
 */
public class XmlBasedNameFixer implements NameFixer {

  private Document doc;
  
  public XmlBasedNameFixer(File fileLocation) {
    try {
      doc = Jsoup.parse(fileLocation, "UTF-8");
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getActualName(String name) {
    Elements elements = doc.select("wrong:contains(" + name + ")");
    if (elements.size() == 0)
      return name;
    
    return elements.first().parent().select("correct").first().text();
  }
  
}
