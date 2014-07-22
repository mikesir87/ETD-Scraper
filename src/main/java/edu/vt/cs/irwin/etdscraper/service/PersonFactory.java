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
package edu.vt.cs.irwin.etdscraper.service;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.Role;

/**
 * Defines a factory that is used to create contributor objects.
 *
 * @author Michael Irwin
 */
public interface PersonFactory {

  /**
   * Create a person based on the provided name and role
   * @param name The name of the person. Supports [Last name, First Name] and
   * [First name Last name] formats
   * @param role The role of the user
   * @return The Person object
   */
  EtdContributor createPerson(Etd etd, String name, Role role);
}
