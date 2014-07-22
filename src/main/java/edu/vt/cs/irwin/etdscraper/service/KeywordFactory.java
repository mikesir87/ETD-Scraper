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

import edu.vt.cs.irwin.etdscraper.Keyword;

/**
 * Defines a factory that is used to create keywords.  It is expected that each
 * keyword is stored and any subsequent requests for the same keyword value will
 * return the same Keyword object.
 *
 * @author Michael Irwin
 */
public interface KeywordFactory {

  /**
   * Generate a keyword object for the provided keyword.
   * @param keyword The keyword
   * @return The keyword object
   */
  Keyword generateKeyword(String keyword);
}
