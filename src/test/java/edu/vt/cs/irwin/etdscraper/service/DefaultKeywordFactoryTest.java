/*
 * File created on Jul 23, 2014 
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

import edu.vt.cs.irwin.etdscraper.Keyword;

/**
 * Test case for the {@link DefaultKeywordFactory} class.
 *
 * @author Michael Irwin
 */
public class DefaultKeywordFactoryTest {

  private DefaultKeywordFactory factory = new DefaultKeywordFactory();
  
  /**
   * Validate that keyword creation works as expected
   */
  @Test
  public void testKeywordCreation() {
    String keywordValue = "Something fancy";
    Keyword keyword = factory.generateKeyword(keywordValue);
    assertThat(keyword.getKeyword(), is(equalTo(keywordValue)));
  }
  
  /**
   * Validate that if consecutive queries are made for the same keyword, the
   * same Keyword object is returned
   */
  @Test
  public void testThatConsecutiveQueriesForSameKeyword() {
    String keywordValue = "Something else fancy";
    Keyword keyword = factory.generateKeyword(keywordValue);
    Keyword keyword2 = factory.generateKeyword(keywordValue);
    assertThat(keyword2, is(sameInstance((Object) keyword)));
    
    Keyword keyword3 = factory.generateKeyword(keywordValue.toUpperCase());
    assertThat(keyword3, is(sameInstance((Object) keyword)));
  }
  
}
