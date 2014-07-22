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

import java.util.concurrent.Callable;

import javax.inject.Inject;

import edu.vt.cs.irwin.etdscraper.Etd;

/**
 * Defines a task that fetches a single ETD from the Scholar database.
 *
 * @author Michael Irwin
 */
public class IndividualScholarEtdTask implements Callable<Etd> {

  @Inject
  private ScholarEtdExtractor etdExtractor;
  
  private String url;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Etd call() throws Exception {
    return etdExtractor.extractEtd(url);
  }
  
  /**
   * Sets the {@code url} property.
   */
  public void setUrl(String url) {
    this.url = url;
  }
  
}
