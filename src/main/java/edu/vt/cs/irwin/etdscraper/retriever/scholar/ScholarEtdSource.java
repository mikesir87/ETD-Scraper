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
package edu.vt.cs.irwin.etdscraper.retriever.scholar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.retriever.EtdSource;
import edu.vt.cs.irwin.etdscraper.service.PageFetcher;

/**
 * A {@link EtdSource} that pulls ETDs from the digital library and archives.
 *
 * @author Michael Irwin
 */
public class ScholarEtdSource implements EtdSource {
  
  private static Logger logger = 
      LoggerFactory.getLogger(ScholarEtdSource.class);
  
  private static final String URL = 
      "http://scholar.lib.vt.edu/theses/browse/by_department/c.html";
  
  @Inject
  private PageFetcher pageFetcher;
  
  @Inject
  private ScholarHandleExtractor handleExtractor;
  
  @Inject
  private Provider<IndividualScholarEtdTask> taskProvider;
  
  @Inject
  private ExecutorService executorService;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSourceName() {
    return "Scholar Digital Library and Archives";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Etd> retrieveEtds() {
    logger.info("Fetching handles for " + getSourceName());
    List<String> handles = getHandles();
    logger.info("Fetched a total of " + handles.size() + " handles");
    return fetchEtds(handles);
  }

  private List<String> getHandles() {
    try {
      String pageBody = pageFetcher.getPageContents(URL);
      return handleExtractor.extractHandles(pageBody);
    } catch (IOException e) {
      throw new RuntimeException("Error fetching " + URL, e);
    }
  }
  
  private List<Etd> fetchEtds(List<String> handles) {
    List<Etd> etds = new ArrayList<>();
    List<IndividualScholarEtdTask> tasks = new ArrayList<>();
    for (String handle : handles) {
      IndividualScholarEtdTask task = taskProvider.get();
      task.setUrl(handle);
      tasks.add(task);
    }
    
    try {
      logger.info("Starting to fetch all ETDs (may take a bit)");
      List<Future<Etd>> results = executorService.invokeAll(tasks);
      for (Future<Etd> result : results) {
        etds.add(result.get());
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
    logger.info("Completed fetching info for " + etds.size() + " ETDs");
    return etds;
  }
}
