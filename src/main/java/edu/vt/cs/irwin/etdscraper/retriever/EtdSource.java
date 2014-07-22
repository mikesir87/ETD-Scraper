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
 * Defines a retriever that knows how to retrieve ETDs from a specific source.
 * <p>
 * It is intended that if a new source is discovered for ETDs, only a new
 * implementation is needed.
 *
 * @author Michael Irwin
 */
public interface EtdSource {
  
  /**
   * Get the name of the source
   * @return The name of the source
   */
  String getSourceName();

  /**
   * Retrieve all of the ETDs
   * @return The etds
   */
  List<Etd> retrieveEtds();
  
}
