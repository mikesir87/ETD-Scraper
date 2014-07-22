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
package edu.vt.cs.irwin.etdscraper.retriever.vtech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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
 * An {@link EtdSource} that knows how to retrieve ETDs from the VTECH
 * database.
 *
 * @author Michael Irwin
 */
public class VtechEtdSource implements EtdSource {
  
  private static final Logger logger = 
      LoggerFactory.getLogger(VtechEtdSource.class);
  
  private static final Integer LIMIT = 1000;
  
  private static final String BASE_URL = "http://vtechworks.lib.vt.edu";
  private static final String[] URLS = {
      BASE_URL + "/handle/10919/9291/browse?value=Computer+Science&type=department&rpp=" + LIMIT,
      BASE_URL + "/handle/10919/11041/browse?value=Computer+Science&type=department&rpp=" + LIMIT,
      BASE_URL + "/handle/10919/9291/browse?value=Computer+Science+and+Applications&type=department&rpp=" + LIMIT,
      BASE_URL + "/handle/10919/11041/browse?value=Computer+Science+and+Applications&type=departmen&rpp=" + LIMIT
  };
  
  @Inject
  protected ExecutorService executorService;
  
  @Inject
  protected PageFetcher pageFetcher;
  
  @Inject
  protected VtechHandleExtractor handleExtractor;
  
  @Inject
  protected Provider<IndividualVtechEtdTask> taskProvider;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getSourceName() {
    return "VT Scholar Database";
  }
  
  /**
   * {@inheritDoc}
   */
  public List<Etd> retrieveEtds() {
    logger.info("Fetching handles for ETDs");
    List<String> handles = getHandles();
    return fetchEtds(handles);
  }
  
  private List<String> getHandles() {
    List<String> handles = new ArrayList<>();
    for (String url : URLS) {
      logger.info("Fetching handles for url: " + url);
      handles.addAll(getHandlesForIndex(url));
    }
    logger.info("Found total of " + handles.size() + " handles");
    return handles;
  }
  
  private List<String> getHandlesForIndex(String url) {
    try {
      String pageBody = pageFetcher.getPageContents(url);
      if (pageBody.contains("next-page-link")) {
        logger.error("Not all handles might have been fetched as only " + LIMIT 
            + " were fetched, and there appears to be more for url: " + url);
      }
      return handleExtractor.getHandles(pageBody);
    }
    catch (IOException e) {
      throw new RuntimeException("Unable to fetch " + url, e);
    }
  }
  
  private List<Etd> fetchEtds(List<String> handles) {
    logger.info("Starting to fetch data for each handle");
    List<Etd> etds = new ArrayList<>();
    List<Callable<Etd>> tasks = new ArrayList<>();
    for (String handle : handles) {
      IndividualVtechEtdTask task = taskProvider.get();
      task.setUrl(formatHandle(handle));
      tasks.add(task);
    }
    
    try {
      List<Future<Etd>> results = executorService.invokeAll(tasks);
      logger.info("Fetching of each handle is completed");
      for (Future<Etd> result : results) {
        etds.add(result.get());
      }
      return etds;
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
  private String formatHandle(String handle) {
    return (handle.charAt(0) != '/') ? handle : BASE_URL + handle;
  }
}
