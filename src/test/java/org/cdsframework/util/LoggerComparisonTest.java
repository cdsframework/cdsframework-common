/**
 * The cdsframework common project implements some base framework functionality.
 *
 * Copyright 2016 HLN Consulting, LLC
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
 * For more information about the this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to scm@cdsframework.org.
 */
package org.cdsframework.util;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author HLN Consulting, LLC
 */
public class LoggerComparisonTest {
    
    public LoggerComparisonTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testLogUtilsApple() {
        final LogUtils loggerUtils = LogUtils.getLogger(LoggerComparisonTest.class);
        long logUtils = 0;
        long maxIter = 300;
        loggerUtils.info("logUtils - init");

        System.out.println("testLoggerComparison");
        for (int i = 0; i < maxIter; i++) {
            long start = System.nanoTime();
            loggerUtils.info("logUtils");
            logUtils += (System.nanoTime() - start);
        }
        System.out.println("Avg logUtils = " + (logUtils/maxIter)/1000000.0);
        System.out.println("Tot logUtils = " + (logUtils)/1000000.0);

    }    
   
    @Test
    public void testLog4jApple() {
        final Logger logger4J = Logger.getLogger(LoggerComparisonTest.class);
        long log4j = 0;
        long maxIter = 300;
        logger4J.info("log4J - init");

        System.out.println("testLoggerComparison");
        for (int i = 0; i < maxIter; i++) {
            long start = System.nanoTime();
            logger4J.info("log4J");
            log4j += (System.nanoTime() - start);
        }
        System.out.println("Avg log4j = " + (log4j/maxIter)/1000000.0);
        System.out.println("Tot log4j = " + (log4j)/1000000.0);

    }    
}
