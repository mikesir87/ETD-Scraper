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

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * A {@link ScholarHandleExtractor} that uses Jsoup to parse the DOM.
 *
 * @author Michael Irwin
 */
public class JsoupScholarHandleExtractor implements ScholarHandleExtractor {

  private static final List<String> VALID_DEPARTMENTS = new ArrayList<>();
  
  {{
    VALID_DEPARTMENTS.add("Computer Science");
    VALID_DEPARTMENTS.add("Computer Science and Applications");
  }}
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> extractHandles(String pageBody) {
    List<String> handles = new ArrayList<>();
    Document doc = Jsoup.parse(pageBody);
    
    boolean pastHeaders = false;
    for (Element element : doc.select("blockquote table tr")) {
      if (!pastHeaders) {
        if (element.select("th").size() > 0) {
          pastHeaders = true;
          continue;
        }
        continue;
      }
      
      String department = element.select("td:eq(3)").text().trim();
      if (!VALID_DEPARTMENTS.contains(department))
        continue;
      handles.add(element.select("td a").attr("href"));
    }
    return handles;
  }
  
}
