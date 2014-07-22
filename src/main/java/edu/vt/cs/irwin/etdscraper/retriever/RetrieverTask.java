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
package edu.vt.cs.irwin.etdscraper.retriever;

import java.util.List;
import java.util.concurrent.Callable;

import edu.vt.cs.irwin.etdscraper.Etd;

/**
 * Defines a callable that is used to fetch the ETDs using a specific 
 * {@link EtdSource}.
 *
 * @author Michael Irwin
 */
public class RetrieverTask implements Callable<List<Etd>> {

  private final EtdSource retriever;
  
  public RetrieverTask(EtdSource retriever) {
    this.retriever = retriever;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Etd> call() throws Exception {
    return retriever.retrieveEtds();
  }
}
