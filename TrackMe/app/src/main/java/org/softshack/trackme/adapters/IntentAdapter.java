package org.softshack.trackme.adapters;

import android.content.Intent;

import org.softshack.trackme.BaseActivity;
import org.softshack.trackme.interfaces.IScreen;

/**
 * Adapter for the Android Intent.
 */

public class IntentAdapter implements IScreen {
    Intent intent;
    BaseActivity activity;

    public IntentAdapter(BaseActivity activity, Intent intent)
    {
        this.intent = intent;
        this.activity = activity;

    }

    @Override
    public void show(){
        this.activity.startActivity(this.intent);
    }

    @Override
    public void setLocation(double latitude, double longitude) {
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
    }
}
