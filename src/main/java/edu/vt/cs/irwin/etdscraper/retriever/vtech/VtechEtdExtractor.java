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

import edu.vt.cs.irwin.etdscraper.Etd;

/**
 * A service that is used to extract the Etd details from a page body that
 * represents the full details for an ETD from the VTech library system.
 *
 * @author Michael Irwin
 */
public interface VtechEtdExtractor {

  /**
   * Extract the ETD contained in the provided page body
   * @param pageBody The page body
   * @return The ETD contained in the page body
   */
  Etd extractEtd(String pageBody);
  
}
