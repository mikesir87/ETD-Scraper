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
package edu.vt.cs.irwin.etdscraper;

/**
 * Represents the relationship between a person and an ETD. Allows for the same
 * person to serve on multiple ETDs, with varying roles.
 *
 * @author Michael Irwin
 */
public interface EtdContributor {
  
  /**
   * Get the etd
   * @return
   */
  Etd getEtd();

  /**
   * Get the person's last name
   * @return The last name
   */
  Person getPerson();
  
  /**
   * Get the role of the person
   * @return The role of the person
   */
  Role getRole();
  
}
