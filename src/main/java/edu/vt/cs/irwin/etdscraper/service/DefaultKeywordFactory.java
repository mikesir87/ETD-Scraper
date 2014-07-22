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

import java.util.HashMap;
import java.util.Map;

import edu.vt.cs.irwin.etdscraper.Keyword;
import edu.vt.cs.irwin.etdscraper.domain.KeywordEntity;

/**
 * A default implementation of a {@link KeywordFactory}.
 *
 * @author Michael Irwin
 */
public class DefaultKeywordFactory implements KeywordFactory {

  private Map<String, Keyword> keywords = new HashMap<>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Keyword generateKeyword(String keyword) {
    String lcKeyword = keyword.toLowerCase();
    if (keywords.containsKey(lcKeyword))
      return keywords.get(lcKeyword);
    
    KeywordEntity entity = new KeywordEntity();
    entity.setKeyword(keyword);
    keywords.put(lcKeyword, entity);
    return entity;
  }
}
