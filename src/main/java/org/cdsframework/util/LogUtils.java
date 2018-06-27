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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdsframework.enumeration.LogLevel;

/**
 *
 * @author HLN Consulting, LLC
 */
public class LogUtils implements Serializable {

    private static final long serialVersionUID = 614570976714188422L;

    protected Logger logger;
    private final Level initialLevel;
    private static final String BEGIN = "Begin ";
    private static final String END = "End ";
    private static final String INIT = "Initialize ";
    private static final String DURATION = " duration(ms): ";
    private static final String EXCEPTION_STRING_FORMAT = "%s: %s";

    /**
     * String based LogUtils constructor.
     *
     * @param loggerString
     */
    public LogUtils(String loggerString) {
        logger = Logger.getLogger(loggerString);
        initialLevel = logger.getLevel();
    }

    /**
     * Class based LogUtils constructor.
     *
     * @param childClass
     */
    public LogUtils(Class childClass) {
        logger = Logger.getLogger(childClass);
        initialLevel = logger.getLevel();
        logger.debug("Initializing logger for class: " + childClass.getSimpleName());
    }

    /**
     * Class based logger construction.
     *
     * @param loggerClass
     * @return
     */
    public static LogUtils getLogger(Class loggerClass) {
        return new LogUtils(loggerClass);
    }

    /**
     * String based logger construction.
     *
     * @param loggerString
     * @return
     */
    public static LogUtils getLogger(String loggerString) {
        return new LogUtils(loggerString);
    }

    /**
     * Logs a message at the specified log level for the specified operation.
     *
     * @param logLevel
     * @param operation
     * @param vargs
     * @return
     */
    private String logMessage(LogLevel logLevel, String operation, Object... vargs) {

        List<Throwable> throwables = null;

        // if the operation name is not null then add it ad the first string message
        StringBuilder msg = new StringBuilder();
        if (operation != null) {
            msg.append(operation);
        }

        // iterate over the vargs and append all non-Throwables on the StringBuilder
        // add the Throwables to the throwables list
        for (Object item : vargs) {
            if (item == null) {
                msg.append("null");
            } else if (item.getClass().isPrimitive()) {
                msg.append(item);
            } else if (item instanceof Throwable) {
                if (throwables == null) {
                    throwables = new ArrayList<Throwable>();
                }
                throwables.add((Throwable) item);
            } else if (item instanceof Class) {
                msg.append(((Class) item).getSimpleName());
            } else {
                try {
                    msg.append(item.toString());
                } catch (Exception e) {
                    logger.error("Error converting msg item to String: " + item);
                    msg.append(item);
                }
            }
        }

        // get message and log it as well as the throwables
        String msgString = msg.toString();
        switch (logLevel) {
            case TRACE:
                logger.trace(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.trace(getExceptionMessage(t), t);
                    }
                }
                break;
            case DEBUG:
                logger.debug(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.debug(getExceptionMessage(t), t);
                    }
                }

                break;
            case INFO:
                logger.info(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.info(getExceptionMessage(t), t);
                    }
                }

                break;
            case WARN:
                logger.warn(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.warn(getExceptionMessage(t), t);
                    }
                }

                break;
            case ERROR:
                logger.error(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.error(getExceptionMessage(t), t);
                    }
                }

                break;
            case FATAL:
                logger.fatal(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.fatal(getExceptionMessage(t), t);
                    }
                }

                break;
            default:
                logger.info(msgString);
                if (throwables != null) {
                    for (Throwable t : throwables) {
                        logger.info(getExceptionMessage(t), t);
                    }
                }
        }
        return msgString;
    }

    /**
     * Returns a formatted string: Exception class name: exception message.
     *
     * @param t
     * @return
     */
    private String getExceptionMessage(Throwable t) {
        return String.format(EXCEPTION_STRING_FORMAT, t.getClass().getSimpleName(), t.getMessage());
    }

    /**
     * Logs a message at log level trace.
     *
     * @param msgs
     * @return
     */
    public String trace(Object... msgs) {
        return log(LogLevel.TRACE, null, msgs);
    }

    /**
     * Logs a message at log level debug.
     *
     * @param msgs
     * @return
     */
    public String debug(Object... msgs) {
        return log(LogLevel.DEBUG, null, msgs);
    }

    /**
     * Logs a message at log level info.
     *
     * @param msgs
     * @return
     */
    public String info(Object... msgs) {
        return log(LogLevel.INFO, null, msgs);
    }

    /**
     * Logs a message at log level warn.
     *
     * @param msgs
     * @return
     */
    public String warn(Object... msgs) {
        return log(LogLevel.WARN, null, msgs);
    }

    /**
     * Logs a message at log level error.
     *
     * @param msgs
     * @return
     */
    public String error(Object... msgs) {
        return log(LogLevel.ERROR, null, msgs);
    }

    /**
     * Logs a message at log level fatal.
     *
     * @param msgs
     * @return
     */
    public String fatal(Object... msgs) {
        return log(LogLevel.FATAL, null, msgs);
    }

    /**
     * Logs the full stacktrace of a Throwable object. The logger by default only logs the top level exception and only the cause
     * message.
     *
     * @param e
     */
    public void fullStack(Throwable e) {
        if (e != null) {
            error(e);
            if (e.getCause() != null) {
                error("CAUSE: ", e.getCause().getMessage());
                fullStack(e.getCause());
            }
        } else {
            error("Null exception passed to error().");
        }
    }

    /**
     * Log an INITIALIZE operation message. Defaults to log level trace.
     *
     * @param vargs
     */
    public void logInit(Object... vargs) {
        logInit(LogLevel.TRACE, vargs);
    }

    /**
     * Log a BEGIN operation message. Defaults to log level debug.
     *
     * @param vargs
     */
    public void logBegin(Object... vargs) {
        logBegin(LogLevel.DEBUG, vargs);
    }

    /**
     * Log an END operation message. Defaults to log level debug.
     *
     * @param vargs
     */
    public void logEnd(Object... vargs) {
        logEnd(LogLevel.DEBUG, vargs);
    }

    /**
     * Log an INITIALIZE operation message at the specified log level.
     *
     * @param logLevel
     * @param vargs
     */
    public void logInit(LogLevel logLevel, Object... vargs) {
        log(logLevel, INIT, vargs);
    }

    /**
     * Log a BEGIN operation message at the specified log level.
     *
     * @param logLevel
     * @param vargs
     */
    public void logBegin(LogLevel logLevel, Object... vargs) {
        log(logLevel, BEGIN, vargs);
    }

    /**
     * Log an END operation message at the specified log level.
     *
     * @param logLevel
     * @param vargs
     */
    public void logEnd(LogLevel logLevel, Object... vargs) {
        log(logLevel, END, vargs);
    }

    /**
     * Log a message at the specified level for the specified operation.
     *
     * @param logLevel
     * @param operation
     * @param vargs
     * @return
     */
    public String log(LogLevel logLevel, String operation, Object... vargs) {
        String result = null;
        switch (logLevel) {
            case TRACE:
                if (logger.isTraceEnabled()) {
                    result = logMessage(logLevel, operation, vargs);
                }
                break;
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    result = logMessage(logLevel, operation, vargs);
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    result = logMessage(logLevel, operation, vargs);
                }
                break;
            case WARN:
                result = logMessage(logLevel, operation, vargs);
                break;
            case ERROR:
                result = logMessage(logLevel, operation, vargs);
                break;
            case FATAL:
                result = logMessage(logLevel, operation, vargs);
                break;
            default:
                result = logMessage(LogLevel.INFO, operation, vargs);
        }
        return result;
    }

    /**
     * Returns whether or not log level debug is enabled.
     *
     * @return
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Sets the log level to debug.
     */
    public void setDebugEnabled() {
        logger.setLevel(Level.DEBUG);
    }

    /**
     * Returns whether or not log level trace is enabled.
     *
     * @return
     */
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * Sets log level to trace.
     */
    public void setTraceEnabled() {
        logger.setLevel(Level.TRACE);
    }

    /**
     * Reset the logger level to the value that the logger was initialized with.
     */
    public void resetLevel() {
        logger.setLevel(initialLevel);
    }

    /**
     * Logs the duration of an operation at the specified log level.
     *
     * @param logLevel
     * @param operation
     * @param start the result of System.nanoTime() as a start value
     */
    public void logDuration(LogLevel logLevel, String operation, long start) {
        log(logLevel, operation, DURATION, (System.nanoTime() - start) / 1000000.0);
    }

    /**
     * Logs the duration of an operation. Defaults to log level info.
     *
     * @param operation
     * @param start the result of System.nanoTime() as a start value
     */
    public void logDuration(String operation, long start) {
        logDuration(LogLevel.INFO, operation, start);
    }
}
