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

import java.util.Arrays;
import java.util.List;
import org.cdsframework.enumeration.LogLevel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author HLN Consulting, LLC
 */
public class LogUtilsTest {

    private static List<String> TEST_OBJECT = Arrays.asList(new String[]{"Foo", "bar", "BAZ"});
    public LogUtilsTest() {
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
    public void testLogUtilsDebug() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.setDebugEnabled();
        logger.debug("this ", "is ", "a ", "test", 1, 2, 3, 9.0001, 12L);
        logger.debug("this ", "is ", "a ", "test Exception: ", new Exception("debug"));
        logger.debug("this ", "is ", "a ", "test nested Exception: ", new IllegalArgumentException("I couldn't find it!!!", new Exception("debug")));
        logger.debug("this ", "is ", "a ", "another test nested Exception: ", new Exception("not again!", new IllegalArgumentException("I couldn't find it!!!", new Exception("debug"))));
    }

    @Test
    public void testLogUtilsInfo() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.info("this ", "is ", "a ", "test");
        logger.info("this ", "is ", "a ", "test Exception: ", new Exception("info"));
    }

    @Test
    public void testLogUtilsWarn() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.warn("this ", "is ", "a ", "test");
        logger.warn("this ", "is ", "a ", "test Exception: ", new Exception("warn"));
    }

    @Test
    public void testLogUtilsError() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.error("this ", "is ", "a ", "test");
        logger.error("this ", "is ", "a ", "test Exception: ", new Exception("error"));
    }

    @Test
    public void testLogUtilsException() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.error(new Exception("not again!", new IllegalArgumentException("I couldn't find it!!!", new Exception("error"))));
    }

    @Test
    public void testLogUtilsFullStack() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
        logger.fullStack(new Exception("not again!", new IllegalArgumentException("I couldn't find it!!!", new Exception("error"))));
    }

    @Test
    public void testLogUtilsOperations() {
        System.out.println("testing LogUtils");
        LogUtils logger = LogUtils.getLogger(LogUtilsTest.class);
//        logger.setTraceEnabled();
        final String METHODNAME = "myMethodName ";
        logger.logBegin(METHODNAME);
        logger.logBegin(LogLevel.INFO, METHODNAME);
        logger.logBegin(LogLevel.INFO, METHODNAME, TEST_OBJECT);
        logger.logBegin(LogLevel.INFO, METHODNAME, TEST_OBJECT, " Bleh");
        logger.logBegin(LogLevel.DEBUG, METHODNAME, TEST_OBJECT, " Bleh");
        logger.logInit(METHODNAME);
        logger.logInit(LogLevel.INFO, METHODNAME);
        logger.logInit(LogLevel.INFO, METHODNAME, TEST_OBJECT);
        logger.logInit(LogLevel.INFO, METHODNAME, TEST_OBJECT, " Bleh");
        logger.logInit(LogLevel.DEBUG, METHODNAME, TEST_OBJECT, " Bleh");
        logger.logEnd(METHODNAME);
        logger.logEnd(LogLevel.INFO, METHODNAME);
        logger.logEnd(LogLevel.INFO, METHODNAME, TEST_OBJECT);
        logger.logEnd(LogLevel.INFO, METHODNAME, TEST_OBJECT, " Bleh");
        logger.logEnd(LogLevel.DEBUG, METHODNAME, TEST_OBJECT, " Bleh");
    }

}
