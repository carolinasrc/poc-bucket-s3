package com.poc.helper;

import org.springframework.stereotype.Component;

@Component
public final class LogGenerator {
  private LogGenerator(){}

  public static String logMsg(final String fileName, final String message) {
    return "[LOG] " + messageBody(fileName, message);
  }

  public static String errorMsg(final String fileName, final String message, final Throwable e) {
    var logMessage = "[ERRO] " + messageBody(fileName, message);

    if (e != null) {
      logMessage += " | Exception Message: " + e.getMessage();
    }

    return logMessage;
  }

  private static String messageBody(final String fileName, final String message) {
    return " Key: " + fileName + " | Message: " + message;
  }
}
