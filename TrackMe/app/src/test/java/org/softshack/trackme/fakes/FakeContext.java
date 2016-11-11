package org.softshack.trackme.fakes;


import org.softshack.trackme.interfaces.IContext;

public class FakeContext implements IContext{
    @Override
    public String getString(int resId, Object... formatArgs) {
        return null;
    }
}
