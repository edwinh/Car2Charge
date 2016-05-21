package com.example.edwin.car2charge;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class CarDataProvider extends ContentProvider {
	
	private CarDatabase carData;
	public static final String AUTHORITY = "com.hype.car2charge.CarDataProvider"; 
	public static final int CARS = 100;
	public static final int CAR_ID = 110;
	
	private static final String CARS_BASE_PATH = "cars";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CARS_BASE_PATH);
	
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/car2charge";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/car2charge";
	
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, CARS_BASE_PATH, CARS);
        sURIMatcher.addURI(AUTHORITY, CARS_BASE_PATH + "/#", CAR_ID);
    }
		
	@Override
	public boolean onCreate() {
		carData = new CarDatabase(getContext());
		return true;
	}	
	
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CarDatabase.TABLE);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case CAR_ID:
            queryBuilder.appendWhere(CarDatabase.C_ID + "="
                    + uri.getLastPathSegment());
            break;
        case CARS:
            // no filter
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(carData.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
	
    @Override
    public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case CAR_ID:
        	return CONTENT_ITEM_TYPE;
        case CARS:
            return CONTENT_TYPE;
        default:
            return null;
        }
    }
		
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = carData.getWritableDatabase();
        int rowsAffected = 0;
        switch (uriType) {
        case CAR_ID:
        	String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsAffected = sqlDB.delete(CarDatabase.TABLE,
                		CarDatabase.C_ID + "=" + id, null);
            } else {
                rowsAffected = sqlDB.delete(CarDatabase.TABLE,
                        selection + " and " + CarDatabase.C_ID + "=" + id,
                        selectionArgs);
            }	
        case CARS:
            rowsAffected = sqlDB.delete(CarDatabase.TABLE,
                    selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
	
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = carData.getWritableDatabase();

        int rowsAffected;

        switch (uriType) {
        case CAR_ID:
        	 String id = uri.getLastPathSegment();
             StringBuilder modSelection = new StringBuilder(CarDatabase.C_ID
                     + "=" + id);

             if (!TextUtils.isEmpty(selection)) {
                 modSelection.append(" AND " + selection);
             }

             rowsAffected = sqlDB.update(CarDatabase.TABLE,
                     values, modSelection.toString(), null);	
        case CARS:
            rowsAffected = sqlDB.update(CarDatabase.TABLE,
                    values, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        if (uriType != CARS) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        SQLiteDatabase sqlDB = carData.getWritableDatabase();
        long newID = sqlDB
                .insert(CarDatabase.TABLE, null, values);
        if (newID > 0) {
            Uri newUri = ContentUris.withAppendedId(uri, newID);
            getContext().getContentResolver().notifyChange(uri, null);
            return newUri;
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }
    }
    
}
