/*
 * File created on Jul 10, 2014 
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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.vt.cs.irwin.etdscraper.domain.EtdKeywordMapping;

/**
 * Defines an electronic thesis dissertation (ETD).
 *
 * @author Michael Irwin
 */
public interface Etd {

  /**
   * Get the URN for the ETD
   * @return
   */
  String getUrn();
  
  /**
   * Get the title for the ETD
   * @return The title
   */
  String getTitle();
  
  /**
   * Get the abstract, if there is one
   * @return The abstract
   */
  String getAbstract();
  
  /**
   * Get the degree for the ETD
   * @return The degree
   */
  Degree getDegree();
  
  /**
   * Get the url that the ETD is hosted at
   * @return The url
   */
  String getUrl();
  
  /**
   * Get the date the ETD was published
   * @return The publish date
   */
  Date getDate();
  
  /**
   * Get the department
   * @return The department
   */
  String getDepartment();
  
  /**
   * Get the keywords associated with the ETD
   * @return The keywords
   */
  Collection<EtdKeywordMapping> getKeywords();
  
  /**
   * Get the people responsible for the ETD
   * @return The people for the ETD. Includes the author and committee members
   */
  List<EtdContributor> getContributors();
  
}
