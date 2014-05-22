
//HANDLER DE BASE DE DATOS LOCAL PARA GUARDAR RECORD DIARIO/CACHE OFFLINE

package com.xoaquin.r07d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;   //VERSION DB
	private static final String DATABASE_NAME = "recordsdiariosDB.db";  //NOMBRE DE DB
	private static final String TABLE_RECORDS = "records";  //NOMBRE DE TABLA
	
	//NOMBRES DE LAS COLUMNAS
	private static final String KEY_ID = "id";
	private static final String KEY_ADG = "adg";
	private static final String KEY_ANIO = "anio";
	private static final String KEY_AOP = "aop";
	private static final String KEY_AOPI = "aopi";
	private static final String KEY_COO = "coo";
	private static final String KEY_DIA = "dia";
	private static final String KEY_FECHA = "fecha";
	private static final String KEY_HORAF = "horaf";
	private static final String KEY_HORAI = "horai";
	private static final String KEY_LB = "lb";
	private static final String KEY_LDN = "ldn";
	private static final String KEY_MES = "mes";
	private static final String KEY_MGC = "mgc";
	private static final String KEY_OPD = "opd";
	private static final String KEY_OPLCO = "oplco";
	private static final String KEY_OPL = "opl";
	private static final String KEY_PEI = "pei";
	private static final String KEY_QMHD = "qmhD";
	
	RecordDiarioObject rdoget;
	
	public DatabaseHandler(Context context) {  //CONSTRUCTOR GENERAL
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	
	// CREANDO TABLAS 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECORDS_TABLE = "CREATE TABLE " + TABLE_RECORDS + 
         "("+ KEY_ID + " INTEGER PRIMARY KEY," 
            + KEY_ADG + " TEXT,"  
            + KEY_ANIO + " TEXT," 
            + KEY_AOP + " TEXT," 
            + KEY_AOPI + " TEXT," 
            + KEY_COO + " TEXT," 
            + KEY_DIA + " TEXT,"  
            + KEY_FECHA + " TEXT," 
            + KEY_HORAF + " TEXT," 
            + KEY_HORAI + " TEXT," 
            + KEY_LB + " TEXT," 
            + KEY_LDN + " TEXT," 
            + KEY_MES + " TEXT,"   
            + KEY_MGC + " TEXT," 
            + KEY_OPD + " TEXT," 
            + KEY_OPLCO + " TEXT," 
            + KEY_OPL + " TEXT," 
            + KEY_PEI + " TEXT," 
            + KEY_QMHD + " TEXT" + ")";
        
        db.execSQL(CREATE_RECORDS_TABLE);
    }
 
    // POR SI SE HACE UN UPGRADE 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
 
        // Create tables again
        onCreate(db);
    }
    
    
    //CRUDS = CREAR LEER ACTUALIZAR Y BORRAR
    
 // A�adir record
    void addRDO(RecordDiarioObject rdo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ADG, rdo.getadg()); 
        values.put(KEY_ANIO, rdo.getanio()); 
        values.put(KEY_AOP, rdo.getaop()); 
        values.put(KEY_AOPI, rdo.getaopi()); 
        values.put(KEY_COO, rdo.getcoo()); 
        values.put(KEY_DIA, rdo.getdia()); 
        values.put(KEY_FECHA, rdo.getfecha()); 
        values.put(KEY_HORAF, rdo.gethoraf()); 
        values.put(KEY_HORAI, rdo.gethorai()); 
        values.put(KEY_LB, rdo.getlb()); 
        values.put(KEY_LDN, rdo.getldn()); 
        values.put(KEY_MES, rdo.getmes()); 
        values.put(KEY_MGC, rdo.getmgc()); 
        values.put(KEY_OPD, rdo.getopd()); 
        values.put(KEY_OPLCO, rdo.getoplco()); 
        values.put(KEY_OPL, rdo.getopl()); 
        values.put(KEY_PEI, rdo.getpei()); 
        values.put(KEY_QMHD, rdo.getqmhD()); 
        
 
        // Insertando fila
        db.insert(TABLE_RECORDS, null, values);
        db.close(); // Cerrando conexion db
    }
 
    
    // Obteniendo record desde fecha 
    RecordDiarioObject getRDO(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        //Query con la fecha
        Cursor cursor = db.query(TABLE_RECORDS, new String[] { KEY_ID,KEY_ADG,KEY_ANIO,KEY_AOP,KEY_AOPI,KEY_COO,KEY_DIA,KEY_FECHA,KEY_HORAF,KEY_HORAI,KEY_LB,KEY_LDN,KEY_MES,KEY_MGC,KEY_OPD,KEY_OPLCO,KEY_OPL,KEY_PEI,KEY_QMHD}, KEY_FECHA + "=?",
                new String[] { fecha }, null, null, null, null);
        if (cursor != null)
            cursor.moveToLast();
 
        
        int rows=cursor.getCount();
        
        if(rows>0){
        //grabando resultado de query en un objeto
         rdoget = new RecordDiarioObject(
        		cursor.getString(1),
                cursor.getString(2), 
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15),
                cursor.getString(16),
                cursor.getString(17),
                cursor.getString(18));
        }
        
        
        if(cursor != null){
            cursor.close();
        }
        
        // retorna Recorddiario
        return rdoget;
    }
     
   
 
    // actualizar record
    public int updateRDO(RecordDiarioObject rdo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ADG, rdo.getadg());
        values.put(KEY_ANIO, rdo.getanio());
        values.put(KEY_AOP, rdo.getaop());
        values.put(KEY_AOPI, rdo.getaopi());
        values.put(KEY_COO, rdo.getcoo());
        values.put(KEY_DIA, rdo.getdia());
        values.put(KEY_FECHA, rdo.getfecha());
        values.put(KEY_HORAF, rdo.gethoraf());
        values.put(KEY_HORAI, rdo.gethorai());
        values.put(KEY_LB, rdo.getlb());
        values.put(KEY_LDN, rdo.getldn());
        values.put(KEY_MES, rdo.getmes());
        values.put(KEY_MGC, rdo.getmgc());
        values.put(KEY_OPD, rdo.getopd());
        values.put(KEY_OPLCO, rdo.getoplco());
        values.put(KEY_OPL, rdo.getopl());
        values.put(KEY_PEI, rdo.getpei());
        values.put(KEY_QMHD, rdo.getqmhD());
        
 
        // actualizando fila
        
        db.update(TABLE_RECORDS, values, KEY_FECHA + " = ?",  new String[] { rdo.getfecha() });
        db.close();
        return 1;
        
        //return db.update(TABLE_RECORDS, values, KEY_FECHA + " = ?",  new String[] { rdo.getfecha() });   //original
    }
 
  
	
	
	
}
