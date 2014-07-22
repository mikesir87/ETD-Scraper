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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.Role;
import edu.vt.cs.irwin.etdscraper.domain.EtdEntity;

/**
 * Test case for the {@link DefaultPersonFactory} class.
 *
 * @author Michael Irwin
 */
public class DefaultPersonFactoryTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  
  Etd etd = new EtdEntity();
  @Mock NameFixer nameFixer;
  
  private DefaultPersonFactory factory = new DefaultPersonFactory();
  
  /**
   * Perform setup tasks
   */
  @Before
  public void setUp() {
    factory.nameFixer = nameFixer;
  }
  
  /**
   * Test last name first (Irwin, Michael)
   */
  @Test
  public void testLastNameFirst() {
    final String name = "Irwin, Michael";
    setExpectations(name);
    
    EtdContributor contributor = factory.createPerson(etd, name, Role.STUDENT);
    
    assertThat(contributor.getEtd(), is(sameInstance((Object) etd)));
    assertThat(contributor.getRole(), is(Role.STUDENT));
    assertThat(contributor.getPerson(), is(not(nullValue())));
    assertThat(contributor.getPerson().getLastName(), is(equalTo("Irwin")));
    assertThat(contributor.getPerson().getFirstName(), is(equalTo("Michael")));
  }
  
  /**
   * Test first name first (Michael Irwin)
   */
  @Test
  public void testFirstNameFirst() {
    final String name = "Michael Irwin";
    setExpectations(name);
    
    EtdContributor contributor = factory.createPerson(etd, name, Role.COCHAIR);
    
    assertThat(contributor.getEtd(), is(sameInstance((Object) etd)));
    assertThat(contributor.getRole(), is(Role.COCHAIR));
    assertThat(contributor.getPerson(), is(not(nullValue())));
    assertThat(contributor.getPerson().getLastName(), is(equalTo("Irwin")));
    assertThat(contributor.getPerson().getFirstName(), is(equalTo("Michael")));
  }
  
  /**
   * Test last name first with an initial - Irwin, Michael S
   */
  @Test
  public void testLastNameFirstWithInitial() {
    final String name = "Irwin, Michael S";
    setExpectations(name);
    
    EtdContributor contributor = factory.createPerson(etd, name, Role.COCHAIR);
    
    assertThat(contributor.getEtd(), is(sameInstance((Object) etd)));
    assertThat(contributor.getRole(), is(Role.COCHAIR));
    assertThat(contributor.getPerson(), is(not(nullValue())));
    assertThat(contributor.getPerson().getLastName(), is(equalTo("Irwin")));
    assertThat(contributor.getPerson().getFirstName(), is(equalTo("Michael S")));
  }
  
  /**
   * Test first name first with an initial - Michael S Irwin
   */
  @Test
  public void testFirstNameFirstWithInitial() {
    final String name = "Michael S Irwin";
    setExpectations(name);
    
    EtdContributor contributor = factory.createPerson(etd, name, Role.COCHAIR);

    assertThat(contributor.getEtd(), is(sameInstance((Object) etd)));
    assertThat(contributor.getRole(), is(Role.COCHAIR));
    assertThat(contributor.getPerson(), is(not(nullValue())));
    assertThat(contributor.getPerson().getLastName(), is(equalTo("Irwin")));
    assertThat(contributor.getPerson().getFirstName(), is(equalTo("Michael S")));
  }
  
  /**
   * Test when only a last name is given - Irwin
   */
  @Test
  public void testOnlyLastName() {
    final String name = "Irwin";
    setExpectations(name);
    
    EtdContributor contributor = factory.createPerson(etd, name, Role.COCHAIR);
    
    assertThat(contributor.getEtd(), is(sameInstance((Object) etd)));
    assertThat(contributor.getRole(), is(Role.COCHAIR));
    assertThat(contributor.getPerson(), is(not(nullValue())));
    assertThat(contributor.getPerson().getLastName(), is(equalTo("Irwin")));
    assertThat(contributor.getPerson().getFirstName(), is(nullValue()));
  }
  
  /**
   * Validate that if multiple requests are made for the same name, the same
   * person object is returned.
   */
  @Test
  public void testConsecutiveLookupsForSameName() {
    final String name = "Michael Irwin";
    setExpectations(name);
    setExpectations(name);
    
    EtdContributor contributor1 = factory.createPerson(etd, name, Role.STUDENT);
    EtdContributor contributor2 = factory.createPerson(etd, name, Role.CHAIR);
    
    assertThat(contributor1.getRole(), is(Role.STUDENT));
    assertThat(contributor2.getRole(), is(Role.CHAIR));
    assertThat(contributor1.getPerson(), 
        is(sameInstance((Object) contributor2.getPerson())));
  }
  
  private void setExpectations(final String name) {
    context.checking(new Expectations() { { 
      oneOf(nameFixer).getActualName(name);
        will(returnValue(name));
    } });
  }
}
