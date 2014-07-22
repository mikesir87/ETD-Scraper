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
package edu.vt.cs.irwin.etdscraper;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import edu.vt.cs.irwin.etdscraper.repository.EtdRepository;
import edu.vt.cs.irwin.etdscraper.repository.OrmLiteEtdRepository;
import edu.vt.cs.irwin.etdscraper.retriever.ConcreteEtdRetriever;
import edu.vt.cs.irwin.etdscraper.retriever.EtdRetriever;
import edu.vt.cs.irwin.etdscraper.retriever.EtdSource;
import edu.vt.cs.irwin.etdscraper.retriever.excel.ExcelEtdSource;
import edu.vt.cs.irwin.etdscraper.retriever.scholar.JsoupScholarEtdExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.scholar.JsoupScholarHandleExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.scholar.ScholarEtdExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.scholar.ScholarEtdSource;
import edu.vt.cs.irwin.etdscraper.retriever.scholar.ScholarHandleExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.vtech.JsoupVtechEtdExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.vtech.JsoupVtechHandleExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.vtech.VtechEtdExtractor;
import edu.vt.cs.irwin.etdscraper.retriever.vtech.VtechEtdSource;
import edu.vt.cs.irwin.etdscraper.retriever.vtech.VtechHandleExtractor;
import edu.vt.cs.irwin.etdscraper.service.DefaultKeywordFactory;
import edu.vt.cs.irwin.etdscraper.service.DefaultPersonFactory;
import edu.vt.cs.irwin.etdscraper.service.HttpClientPageFetcher;
import edu.vt.cs.irwin.etdscraper.service.KeywordFactory;
import edu.vt.cs.irwin.etdscraper.service.NameFixer;
import edu.vt.cs.irwin.etdscraper.service.NonFixingNameFixer;
import edu.vt.cs.irwin.etdscraper.service.PageFetcher;
import edu.vt.cs.irwin.etdscraper.service.PersonFactory;
import edu.vt.cs.irwin.etdscraper.service.XmlBasedNameFixer;

/**
 * The module definition that provides the configuration used for all of the
 * various injection points.
 *
 * @author Michael Irwin
 */
public class EtdModule extends AbstractModule {

  public static final String JDBC_URL = "jdbc.url";
  
  private static final Integer THREADPOOL_SIZE = 10;
  
  private Configuration configuration;
  
  /**
   * Create a new instance
   * @param properties
   */
  public EtdModule(Configuration configuration) {
    this.configuration = configuration;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    bind(PageFetcher.class).to(HttpClientPageFetcher.class).asEagerSingleton();
    bind(PersonFactory.class).to(DefaultPersonFactory.class).asEagerSingleton();
    bind(EtdRepository.class).to(OrmLiteEtdRepository.class).asEagerSingleton();
    bind(KeywordFactory.class).to(DefaultKeywordFactory.class).asEagerSingleton();
    
    Multibinder<EtdSource> etdSources = 
        Multibinder.newSetBinder(binder(), EtdSource.class);

    etdSources.addBinding().to(VtechEtdSource.class);
    bind(EtdRetriever.class).to(ConcreteEtdRetriever.class);
    bind(VtechHandleExtractor.class).to(JsoupVtechHandleExtractor.class);
    bind(VtechEtdExtractor.class).to(JsoupVtechEtdExtractor.class);

    etdSources.addBinding().to(ScholarEtdSource.class);
    bind(ScholarHandleExtractor.class).to(JsoupScholarHandleExtractor.class);
    bind(ScholarEtdExtractor.class).to(JsoupScholarEtdExtractor.class);
    
    if (configuration.getExcelFile() != null) {
      etdSources.addBinding().to(ExcelEtdSource.class);
    }
  }
  
  /**
   * Definition for an executorService that is shared amongst all injection
   * points
   */
  @Provides @Singleton
  private ExecutorService executorService() {
    return Executors.newFixedThreadPool(THREADPOOL_SIZE);
  }
  
  @Provides @Singleton
  private ConnectionSource connectionSource() throws Exception {
    return new JdbcConnectionSource(configuration.getJdbcUrl());
  }
  
  @Provides @Singleton
  private Configuration configuration() throws Exception {
    return configuration;
  }
  
  @Provides @Singleton
  private NameFixer nameFixer() throws Exception {
    if (configuration.getNameFixFileLocation() == null)
      return new NonFixingNameFixer();
    
    File file = new File(configuration.getNameFixFileLocation());
    if (!file.exists())
      throw new RuntimeException("Unable to find " + configuration.getNameFixFileLocation());
    
    if (configuration.getNameFixFileLocation().endsWith("xml"))
      return new XmlBasedNameFixer(file);
    throw new RuntimeException("Don't know how to work with the name fix file provided");
  }
}
