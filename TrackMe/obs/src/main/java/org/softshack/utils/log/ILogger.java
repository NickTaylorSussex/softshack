package org.softshack.utils.log;

public interface ILogger {
    void LogError(String tag, Exception e);

    void LogWarning(String tag, String message);

    void LogDebug(String tag, String message);
}
