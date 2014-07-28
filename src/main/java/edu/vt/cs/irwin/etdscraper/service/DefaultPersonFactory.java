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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.Person;
import edu.vt.cs.irwin.etdscraper.Role;
import edu.vt.cs.irwin.etdscraper.domain.EtdContributorEntity;
import edu.vt.cs.irwin.etdscraper.domain.PersonEntity;

/**
 * A default implementation of the {@link PersonFactory}.
 *
 * @author Michael Irwin
 */
public class DefaultPersonFactory implements PersonFactory {

  @Inject
  protected NameFixer nameFixer;
  
  private Map<String, Person> people = new HashMap<>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public EtdContributor createPerson(Etd etd, String name, Role role) {
    name = standardizeName(name);
    name = nameFixer.getActualName(name);
    Person person = getPerson(name);
    
    EtdContributorEntity contributor = new EtdContributorEntity();
    contributor.setEtd(etd);
    contributor.setRole(role);
    contributor.setPerson((PersonEntity) person);
    
    return contributor;
  }
  
  private synchronized Person getPerson(String name) {
    String lcName = name.toLowerCase();
    if (people.containsKey(lcName))
      return people.get(lcName);
    
    String lastName = null;
    String firstName = null;
    
    if (name.indexOf(", ") > 0) {
      lastName = name.substring(0, name.indexOf(", "));
      firstName = name.substring(name.indexOf(", ") + 2);
    }
    else { // Since names are standardized, this gets names that are only last name
      String[] nameSplit = name.split(" ");
      lastName = nameSplit[nameSplit.length - 1];
      if (lastName.length() != name.length())
        firstName = name.substring(0, name.indexOf(lastName) - 1);
    }
    
    PersonEntity person = new PersonEntity();
    person.setFirstName(firstName);
    person.setLastName(lastName);
    
    people.put(lcName, person);
    
    return person;
  }
  
  private String standardizeName(String name) {
    if (name.indexOf(",") > 0)
      return name;
    String lastName = null, firstName = null;
    
    String[] nameSplit = name.split(" ");
    lastName = nameSplit[nameSplit.length - 1];
    if (lastName.length() != name.length())
      firstName = name.substring(0, name.indexOf(lastName) - 1);
    return (firstName != null) ? lastName + ", " + firstName : lastName;
  }
}
