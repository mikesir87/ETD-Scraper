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

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.vt.cs.irwin.etdscraper.repository.EtdRepository;
import edu.vt.cs.irwin.etdscraper.retriever.EtdRetriever;

/**
 * The main class for the project.
 *
 * @author Michael Irwin
 */
public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  
  public static void main(String[] args) throws Exception {
    Configuration config = Configuration.setup(args);
    if (config == null)
      return;
    Injector injector = Guice.createInjector(new EtdModule(config));
    Main main = injector.getInstance(Main.class);
    main.run();
  }
  
  @Inject
  protected EtdRetriever retriever;
  
  @Inject
  protected ExecutorService executorService;
  
  @Inject 
  protected EtdRepository etdRepository;
  
  public void run() {
    try {
      List<Etd> etds = retriever.retrieveEtds();
      logger.info("Retrieved a total of " + etds.size() + " etds");
      logger.info("Saving all ETDs to the database now");
      for (Etd etd : etds) {
        etdRepository.saveEtd(etd);
      }
      logger.info("Finished saving ETDs to the database");
    } finally {
      executorService.shutdownNow();
    }
  }
  
}
