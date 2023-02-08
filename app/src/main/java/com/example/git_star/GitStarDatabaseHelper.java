package com.example.git_star;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GitStarDatabaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "new.db";
        public static final String TABLE_NAME = "repo_data";
        public static final String COL0= "ID";
        public static final String COL1 = "REPO";
        public static final String COL2 = "DES";
        public static final String COL3 = "URL";
        public static final String COL4 = "AVATAR";
        public static final String COL5 = "OWNER";
        public static final String COL6 = "VISIBLE";



        public GitStarDatabaseHelper (Context context) {
            super (context, DATABASE_NAME,null,1);
        }
            @Override
            public void onCreate(SQLiteDatabase db) {
                String createTable = "CREATE TABLE " + TABLE_NAME + " (ID TEXT PRIMARY KEY, REPO TEXT, DES TEXT, URL TEXT, AVATAR TEXT, OWNER TEXT, VISIBLE TEXT)";
                db.execSQL(createTable);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP  TABLE IF EXISTS " + TABLE_NAME);
            }
            public boolean isPresent(String newEntry){
                SQLiteDatabase db=this.getWritableDatabase();
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = '" + newEntry + "'";
                Cursor cursor=db.rawQuery(query,null);
                if(cursor.moveToFirst()){
                    cursor.close();
                    return true;
                }
                cursor.close();
                return false;

            }
            public boolean addData(String id, String repo, String desc, String url, String avatar, String owner, String visible) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL0, id);
                contentValues.put(COL1, repo);
                contentValues.put(COL2, desc);
                contentValues.put(COL3, url);
                contentValues.put(COL4, avatar);
                contentValues.put(COL5, owner);
                contentValues.put(COL6, visible);

                long result = db.insert(TABLE_NAME, null, contentValues);
                if (result == -1)
                    return false;
                else
                    return true;
            }
            public Cursor getListContents()
            {
                SQLiteDatabase db=this.getWritableDatabase();
                Cursor data=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
                return data;
            }

    }

