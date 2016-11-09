package org.softshack.trackme;

import android.content.Context;

public class ContextAdapter implements IContext {

    Context context;

    public ContextAdapter(Context context){
        this.context = context;
    }

    @Override
    public String getString(int resId, Object... formatArgs){
        return this.context.getString(resId, formatArgs);
    }
}
