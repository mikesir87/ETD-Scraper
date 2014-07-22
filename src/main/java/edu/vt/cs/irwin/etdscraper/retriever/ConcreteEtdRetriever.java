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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.cs.irwin.etdscraper.Etd;

/**
 * A {@link EtdRetriever} that uses a threadpool to retrieve the ETDs.
 *
 * @author Michael Irwin
 */
public class ConcreteEtdRetriever implements EtdRetriever {
  
  private static final Logger logger = 
      LoggerFactory.getLogger(ConcreteEtdRetriever.class);

  @Inject
  private ExecutorService executorService;
  
  @Inject
  private Set<EtdSource> sources;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Etd> retrieveEtds() {
    List<Etd> etds = new ArrayList<>();
    
    List<Callable<List<Etd>>> tasks = new ArrayList<>();
    for (EtdSource source : sources) {
      tasks.add(new RetrieverTask(source));
    }
    
    try {
      logger.info("Invoking all ETD retrieval tasks (" + tasks.size() + " tasks)");
      List<Future<List<Etd>>> results = executorService.invokeAll(tasks);
      for (Future<List<Etd>> future : results) {
        etds.addAll(future.get());
      }
      logger.info("Completed all ETD retrieval tasks");
      return etds;
    }
    catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
