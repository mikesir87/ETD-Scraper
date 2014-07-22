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
package edu.vt.cs.irwin.etdscraper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.j256.ormlite.field.DatabaseField;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.Person;
import edu.vt.cs.irwin.etdscraper.Role;

/**
 * An entity representation of an {@link EtdContributor}.
 *
 * @author Michael Irwin
 */
@Entity(name = "contributors")
public class EtdContributorEntity implements EtdContributor {

  @Id
  @GeneratedValue
  private Long id;
  
  // Doesn't seem to like the JPA annotations here
  @DatabaseField(foreign = true, canBeNull = false)
  private EtdEntity etd;
  
  // Doesn't seem to like the JPA annotations here
  @DatabaseField(foreign = true, canBeNull = false)
  private PersonEntity person;
  
  @Column(name = "role", nullable = false)
  private Role role;
  
  /**
   * Gets the {@code id} property.
   */
  public Long getId() {
    return id;
  }
  
  /**
   * Sets the {@code id} property.
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Gets the {@code etd} property.
   */
  public Etd getEtd() {
    return etd;
  }
  
  /**
   * Sets the {@code etd} property.
   */
  public void setEtd(Etd etd) {
    this.etd = (EtdEntity) etd;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Person getPerson() {
    return person;
  }
  
  /**
   * Sets the {@code person} property.
   */
  public void setPerson(PersonEntity person) {
    this.person = person;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Role getRole() {
    return role;
  }
  
  /**
   * Sets the {@code role} property.
   */
  public void setRole(Role role) {
    this.role = role;
  }
}
