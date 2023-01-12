package co.miaki.islamicapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "uniqueId.db";
    private static final String TABLE_NAME = "unique_id_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "PHONE";
    private static final String COL_3 = "USER_ID";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,PHONE TEXT,USER_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String phone,String userId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, phone);
        contentValues.put(COL_3, userId);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {

            return false;
        } else
            return true;
    }

    public Cursor getPhoneData() {

        String phone = "PHONE";
        String userId = "USER_ID";

        String query = " SELECT * FROM unique_id_table ";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor res = sqLiteDatabase.rawQuery(query, null);

        return res;
    }

    public boolean updateData(String id, String phone, String userID) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, phone);
        contentValues.put(COL_3, userID);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;

    }
}
