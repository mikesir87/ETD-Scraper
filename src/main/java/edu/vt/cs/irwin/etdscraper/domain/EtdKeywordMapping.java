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
package edu.vt.cs.irwin.etdscraper.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.j256.ormlite.field.DatabaseField;

/**
 * Describes a mapping between etds and keywords.
 *
 * @author Michael Irwin
 */
@Entity(name = "etds_keywords")
public class EtdKeywordMapping {
  
  @Id
  @GeneratedValue
  private Long id;

  @DatabaseField(foreign = true, canBeNull = false)
  private EtdEntity etd;
  
  @DatabaseField(foreign = true, canBeNull = false)
  private KeywordEntity keyword;

  /**
   * Gets the {@code etd} property.
   */
  public EtdEntity getEtd() {
    return etd;
  }

  /**
   * Sets the {@code etd} property.
   */
  public void setEtd(EtdEntity etd) {
    this.etd = etd;
  }

  /**
   * Gets the {@code keyword} property.
   */
  public KeywordEntity getKeyword() {
    return keyword;
  }

  /**
   * Sets the {@code keyword} property.
   */
  public void setKeyword(KeywordEntity keyword) {
    this.keyword = keyword;
  }
  
}
