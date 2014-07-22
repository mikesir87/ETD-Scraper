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

import edu.vt.cs.irwin.etdscraper.Etd;

/**
 * Defines a service that is used to retrieve ETDs.
 * <p>
 * The general use case is that the retriever will manage a threadpool and
 * each source will be given its own thread(s).
 *
 * @author Michael Irwin
 */
public interface EtdRetriever {

  /**
   * Retrieve all of the ETDs
   * @return The etds
   */
  List<Etd> retrieveEtds();
  
}
