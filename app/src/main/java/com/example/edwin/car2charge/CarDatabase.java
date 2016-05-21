package com.example.edwin.car2charge;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarDatabase extends SQLiteOpenHelper {
	private static final String DEBUG_TAG = "CarDatabasebase";
    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "CarDatabase";

    public static final String TABLE = "CarDatabase";
	public static final String C_ID = "_id";
	public static final String C_LICENSE = "license";
	public static final String C_ADDRESS = "address";
	public static final String C_LAT = "lat";
	public static final String C_LONG = "lng";
	public static final String C_BATTERY = "battery";
	public static final String C_CHARGING = "charging";
	public static final String C_DISTANCE = "distance";
	public static final String C_DISTANCE_CP = "distance_cp";
	
	private static final String CREATE_TABLE_CarDatabase = String.format("create table %s " +
			"(%s integer primary key AUTOINCREMENT, %s text, %s text, %s double, %s double, %s int, %s int, %s int, %s int)",
			TABLE, C_ID, C_LICENSE, C_ADDRESS, C_LAT, C_LONG, C_BATTERY, C_CHARGING, C_DISTANCE, C_DISTANCE_CP);
			 
    private static final String DB_SCHEMA = CREATE_TABLE_CarDatabase;

	public CarDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_SCHEMA);
		seedData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
		
	}
	
	public void insertOrIgnore(SQLiteDatabase db, ContentValues values) {
	    Log.d(DEBUG_TAG, "insertOrIgnore on " + values);
	    //SQLiteDatabase db = this.dbHelper.getWritableDatabase();
	    try {
	      db.insertWithOnConflict(TABLE, null, values,
	          SQLiteDatabase.CONFLICT_IGNORE);
	    } finally {
	      //db.close();
	    }
	  }
	
	private void seedData(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(CarDatabase.C_ADDRESS, "Spaarndammerstraat 149G");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Haarlemmerstraat 124E");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Roetersstraat 16");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Hudsonstraat 120");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Bijlmerdreef");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Cornelis Krusemanstraat 62");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "De Boelelaan 1067");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Eerste Boerhaavestraat 13");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Lutmastraat 13");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Noordhollandstraat 82");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Spaarndammerstraat 149G");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Reinwardtstraat 326");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Lumierestraat 59 - 89");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Arent Janszoon Ernststraat 869");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Bijltjespad 2 - 82");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Domela Nieuwenhuisplantsoen 12 - 13");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);		

		values.put(CarDatabase.C_ADDRESS, "West Merwedekanaaldijk 5");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Karel du Jardinstraat 11");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Kloveniersburgwal 127 - 143");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Den Brielstraat 27");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Olympiaplein 31");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Haarlemmerweg 8");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Joop Geesinkweg 306");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Spaarndammerdijk 302");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Van Limburg Stirumstraat 40");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Oostzaanstraat 125");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Laan Der Hesperiden 2 - 66");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);

		values.put(CarDatabase.C_ADDRESS, "Assumburg 67");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Willem de Zwijgerlaan 145");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Cornelis Lelylaan 93 - 117");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "H.j.e. Wenckebachweg 55 - 65");
		values.put(CarDatabase.C_LAT, 20);
		values.put(CarDatabase.C_LONG, 20);
		values.put(CarDatabase.C_BATTERY, 49);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);
		
		values.put(CarDatabase.C_ADDRESS, "Slotermeerlaan 52");
		values.put(CarDatabase.C_LAT, 12);
		values.put(CarDatabase.C_LONG, 21);
		values.put(CarDatabase.C_BATTERY, 48);
		values.put(CarDatabase.C_CHARGING, 0);
		values.put(CarDatabase.C_DISTANCE, 0);
		values.put(CarDatabase.C_DISTANCE_CP, 0);
		this.insertOrIgnore(db, values);		
	}
}
