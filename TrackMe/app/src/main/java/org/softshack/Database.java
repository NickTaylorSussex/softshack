package org.softshack;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION=1;

    //Database name
    private static final String DATABASE_NAME="TrackMeDB";

    //Database Table name
    private static final String TABLE_NAME="locations";

    //Database table columns names
    private static final String ANDROID_ID="id";
    private static final String LOCAL_ID="lid";
    private static final String LATITUDE="latitude";
    private static final String LONGITUDE="longitude";
    private static final String TIME="time";
    private static final String DATE="date";

    private Context currentContext;
    private static final String[] TABLE_COLUMNS={ANDROID_ID,LOCAL_ID, LATITUDE,LONGITUDE,TIME, DATE};

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.currentContext=context;
    }


    public void onCreate(SQLiteDatabase db){
        String trackmeTableSql="CREATE TABLE trackme("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"latitude LATITUDE,"+"longitude LONGITUDE,"+"time TIME,"+"date DATE)";
        db.execSQL(trackmeTableSql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS trackme");
        this.onCreate(db);

        ////////to access the database instantiate the subclass Database aDB=new Database(getContext());
    }

    public void insert(String id_value, String lid_value, String latitude_value, String longitude_value, String date_value, String time_value ){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(ANDROID_ID, id_value);
        values.put(LOCAL_ID, lid_value);
        values.put(LATITUDE, latitude_value);
        values.put(LONGITUDE, longitude_value);
        values.put(DATE, date_value);
        values.put(TIME, time_value);

        db.insert(TABLE_NAME, null, values);

        db.close();

    }

}
