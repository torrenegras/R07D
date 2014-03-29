
//ADAPTADOR BASE DE DATOS PARA NOMBRES EN REPORTE

package com.xoaquin.r07d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter2 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_NOMBRELI = "nombreli";
    public static final String KEY_MDL = "metadelec";
        
    private static final String TAG = "DBAdapter2";
    
    private static final String DATABASE_NAME = "R07DDBLNNM";
    private static final String DATABASE_TABLE = "nomrep";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table nomrep (_id integer primary key autoincrement, " + "nombre text not null," +  "nombreli text not null," + "metadelec text not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter2(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter2 open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertTitle(String nombre, String nombreli,String metadelec) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOMBRE,nombre );
        initialValues.put(KEY_NOMBRELI, nombreli);
        initialValues.put(KEY_MDL, metadelec);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteTitle(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllTitles() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_NOMBRE,
        		KEY_NOMBRELI,
        		KEY_MDL,
                }, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular title---
    public Cursor getTitle(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NOMBRE, 
                		KEY_NOMBRELI,
                		KEY_MDL,
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a title---
    public boolean updateTitle(long rowId, String nombre, 
    String nombreli,String metadelec) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NOMBRE, nombre);
        args.put(KEY_NOMBRELI, nombreli);
        args.put(KEY_MDL, metadelec);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
}
