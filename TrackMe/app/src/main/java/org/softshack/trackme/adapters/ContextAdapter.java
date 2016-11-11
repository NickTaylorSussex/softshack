package org.softshack.trackme.adapters;

import android.content.Context;

import org.softshack.trackme.interfaces.IContext;

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
