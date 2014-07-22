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
package edu.vt.cs.irwin.etdscraper.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.j256.ormlite.field.ForeignCollectionField;

import edu.vt.cs.irwin.etdscraper.Degree;
import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.Keyword;

/**
 * An entity implementation of an {@link Etd}.
 *
 * @author Michael Irwin
 */
@Entity(name = "etds")
public class EtdEntity implements Etd {

  @Id
  private String urn;
  
  @Column
  private String title;
  
  @Column(name = "abstract", columnDefinition = "TEXT")
  private String etdAbstract;
  
  @Column
  private Degree degree;
  
  @Column
  private String url;
  
  @Column
  private Date date;
  
  @Column
  private String department;

  @ManyToMany
  @ForeignCollectionField
  private Collection<EtdKeywordMapping> keywords = new ArrayList<>();
  
  private List<EtdContributor> contributors = new ArrayList<>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrn() {
    return urn;
  }
  
  /**
   * Sets the {@code urn} property.
   */
  public void setUrn(String urn) {
    this.urn = urn;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }
  
  /**
   * Sets the {@code title} property.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAbstract() {
    return etdAbstract;
  }
  
  /**
   * Gets the {@code etdAbstract} property.
   */
  public String getEtdAbstract() {
    return etdAbstract;
  }
  
  /**
   * Sets the {@code etdAbstract} property.
   */
  public void setEtdAbstract(String etdAbstract) {
    this.etdAbstract = etdAbstract;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Degree getDegree() {
    return degree;
  }
  
  /**
   * Sets the {@code degree} property.
   */
  public void setDegree(Degree degree) {
    this.degree = degree;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {
    return url;
  }
  
  /**
   * Sets the {@code url} property
   */
  public void setUrl(String url) {
    this.url = url;
  }
  
  /**
   * Sets the {@code url} property.
   */
  public void setUrl(URL url) {
    this.url = url.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDate() {
    return date;
  }
  
  /**
   * Sets the {@code date} property.
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDepartment() {
    return department;
  }
  
  /**
   * Sets the {@code department} property.
   */
  public void setDepartment(String department) {
    this.department = department;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<EtdKeywordMapping> getKeywords() {
    return keywords;
  }
  
  /**
   * Sets the {@code keywords} property.
   */
  public void setKeywords(List<EtdKeywordMapping> keywords) {
    this.keywords = keywords;
  }
  
  /**
   * Add a keyword
   */
  public void addKeyword(Keyword keyword) {
    EtdKeywordMapping mapping = new EtdKeywordMapping();
    mapping.setEtd(this);
    mapping.setKeyword((KeywordEntity) keyword); 
    this.keywords.add(mapping);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<EtdContributor> getContributors() {
    return contributors;
  }
  
  /**
   * Sets the {@code contributors} property.
   */
  public void setContributors(List<EtdContributor> contributors) {
    this.contributors = contributors;
  }
  
  /**
   * Add a contributor
   * @param contributor The person to add
   */
  public void addContributor(EtdContributor contributor) {
    this.contributors.add(contributor);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("URN: ").append(urn).append("\n");
    sb.append("Title: ").append(title).append("\n");
    sb.append("Degree: ").append(degree).append("\n");
    sb.append("Abstract: ").append(etdAbstract).append("\n");
    sb.append("URL: ").append(url).append("\n");
    sb.append("Date: ").append(date).append("\n");
    
    sb.append("Keywords: ").append("\n");
    for (EtdKeywordMapping keyword : keywords)
      sb.append("-- ").append(keyword.getKeyword().getKeyword()).append("\n");
    sb.append("People: ").append("\n");
    for (EtdContributor contributor : contributors) {
      sb.append("-- ").append(contributor.getRole()).append(" - ")
        .append(contributor.getPerson().getLastName()).append(", ")
        .append(contributor.getPerson().getFirstName()).append("\n");
    }
    
    return sb.toString();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (!(obj instanceof EtdEntity))
      return false;
    return ((EtdEntity) obj).getUrn().equals(urn);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return getUrn().hashCode();
  }
  
}
