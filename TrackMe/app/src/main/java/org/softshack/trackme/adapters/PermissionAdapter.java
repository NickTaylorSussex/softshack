package org.softshack.trackme.adapters;

import android.content.Context;
import android.content.pm.PackageManager;

import org.softshack.trackme.interfaces.IPermissionManager;

public class PermissionAdapter implements IPermissionManager {
    private PackageManager packageManager;
    private Context applicationContext;

    public PermissionAdapter(PackageManager packageManager, Context applicationContext){
        this.packageManager = packageManager;
        this.applicationContext = applicationContext;
    }

    @Override
    public Boolean PermissionFineLocationAllowed(){
        return applicationContext.checkPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.os.Process.myPid(),
                android.os.Process.myUid()) == packageManager.PERMISSION_GRANTED;
    }

}
