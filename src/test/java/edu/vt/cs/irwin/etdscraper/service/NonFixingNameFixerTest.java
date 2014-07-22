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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

/**
 * Test case for the {@link NonFixingNameFixer} class.
 *
 * @author Michael Irwin
 */
public class NonFixingNameFixerTest {
  
  private NonFixingNameFixer fixer = new NonFixingNameFixer();
  
  /**
   * Validate that the fixer works as expected
   */
  @Test
  public void testExecution() {
    final String name = "Irwin, Michael";
    assertThat(fixer.getActualName(name), is(sameInstance((Object) name)));
  }

}
