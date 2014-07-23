/*
 * File created on Jul 23, 2014 
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

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link XmlBasedNameFixer} class.
 *
 * @author Michael Irwin
 */
public class XmlBasedNameFixerTest {

  private XmlBasedNameFixer nameFixer;
  
  /**
   * Perform setup tasks
   */
  @Before
  public void setUp() {
    String filename = 
        XmlBasedNameFixer.class.getClassLoader().getResource("nameFixFile.xml").toString();
    File file = new File(filename.substring(5));
    nameFixer = new XmlBasedNameFixer(file);
  }
  
  /**
   * Validate that a name gets rewritten
   */
  @Test
  public void testNameResolution() {
    String fixedName = "Irwin, Michael S.";
    assertThat(nameFixer.getActualName("Michael Irwin"), is(equalTo(fixedName)));
    assertThat(nameFixer.getActualName("Irwin, Michael"), is(equalTo(fixedName)));
    
    fixedName = "Jackson, Michael";
    assertThat(nameFixer.getActualName("Michael Jackson"), is(equalTo(fixedName)));
  }
  
  
}
