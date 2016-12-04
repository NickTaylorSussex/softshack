package org.softshack.trackme.adapters;

import android.util.Log;

import org.softshack.utils.log.ILogger;

public class AndroidLogAdapter implements ILogger{
    public void LogError(String tag, Exception e) {
        Log.e(tag, e.getMessage());
    }

    public void LogWarning(String tag, String message) {
        Log.w(tag, message);
    }

    public void LogDebug(String tag, String message) {
        Log.d(tag, message);
    }
}
