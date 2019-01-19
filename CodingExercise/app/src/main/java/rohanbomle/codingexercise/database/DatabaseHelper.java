package rohanbomle.codingexercise.database;

import android.arch.core.executor.DefaultTaskExecutor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import rohanbomle.codingexercise.common.Base;
import rohanbomle.codingexercise.model.Album;

import java.util.ArrayList;
import java.util.List;

import static rohanbomle.codingexercise.database.DatabaseReaderContract.*;


/***
 * Database helper class
 * USE TO : Define database operation
 * can be accessed from any module
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getCanonicalName();

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AlbumEntry.TABLE_NAME + " (" +
                    AlbumEntry._ID + " INTEGER PRIMARY KEY," +
                    AlbumEntry.COLUMN_NAME_TITLE + " TEXT," +
                    AlbumEntry.COLUMN_NAME_USEAR_ID + " INT," +
                    AlbumEntry.COLUMN_NAME_ID + " INT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AlbumEntry.TABLE_NAME;

    public DatabaseHelper(Context context){
         super(context, Base.DATABASE_NAME, null, Base.DATABASE_VERSION );
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertAlbumDetails(SQLiteDatabase db, List<Album> list){
        try {
            if (db != null && list != null) {
                for (Album item : list) {
                    ContentValues values = new ContentValues();
                    values.put(AlbumEntry.COLUMN_NAME_TITLE, item.getTitle());
                    values.put(AlbumEntry.COLUMN_NAME_ID, item.getId());
                    values.put(AlbumEntry.COLUMN_NAME_USEAR_ID, item.getUserId());

                    long newRowId = db.insert(AlbumEntry.TABLE_NAME, null, values);
                }
            }
        }catch (SQLException e){
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    public List<Album> selectAlbumDetails(SQLiteDatabase db){
        List<Album> list = new ArrayList<>();
        try{
            String[] projection = {
                    BaseColumns._ID,
                    AlbumEntry.COLUMN_NAME_ID,
                    AlbumEntry.COLUMN_NAME_TITLE,
                    AlbumEntry.COLUMN_NAME_USEAR_ID
            };

            Cursor cursor = db.query(
                    AlbumEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );


            while(cursor.moveToNext()) {
                Album item = new Album(
                        cursor.getInt(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_USEAR_ID)),
                        cursor.getInt(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_ID)) ,
                        cursor.getString(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_TITLE))
                );
                list.add(item);
            }
            cursor.close();

        }catch(SQLException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
        return list;
    }

    public List<Album> selectAlbumDetailsSortByTitle(SQLiteDatabase db, String order){
        List<Album> list = new ArrayList<>();
        try{
            String[] projection = {
                    BaseColumns._ID,
                    AlbumEntry.COLUMN_NAME_ID,
                    AlbumEntry.COLUMN_NAME_TITLE,
                    AlbumEntry.COLUMN_NAME_USEAR_ID
            };

            String sortOrder =
                    AlbumEntry.COLUMN_NAME_TITLE + " " + order;

            Cursor cursor = db.query(
                    AlbumEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );


            while(cursor.moveToNext()) {
                Album item = new Album(
                        cursor.getInt(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_USEAR_ID)),
                        cursor.getInt(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_ID)) ,
                        cursor.getString(cursor.getColumnIndex(AlbumEntry.COLUMN_NAME_TITLE))
                );
                list.add(item);
            }
            cursor.close();

        }catch(SQLException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
        return list;
    }

    public boolean isAlbumDetailsAvailable(SQLiteDatabase db){
        boolean flag = false;
        try{
            String[] projection = {
                    BaseColumns._ID,
                    AlbumEntry.COLUMN_NAME_ID,
                    AlbumEntry.COLUMN_NAME_TITLE,
                    AlbumEntry.COLUMN_NAME_USEAR_ID
            };

            Cursor cursor = db.query(
                    AlbumEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );


            if(cursor.moveToNext()){
                flag = true;
            }

            cursor.close();

        }catch(SQLException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
        return flag;
    }
}
