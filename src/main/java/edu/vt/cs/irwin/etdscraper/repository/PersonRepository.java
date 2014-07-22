/*
 * File created on Jul 21, 2014 
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
package edu.vt.cs.irwin.etdscraper.repository;

import edu.vt.cs.irwin.etdscraper.Person;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Michael Irwin
 */
public interface PersonRepository {

  /**
   * Save the provided person into persistence
   * @param person The saved person
   */
  void savePerson(Person person);
  
  /**
   * Find a person based on the provided first and last names
   * @param firstName The first name
   * @param lastName The last name
   * @return A Person, if a match is found. Otherwise, null.
   */
  Person findPerson(String firstName, String lastName);
}
