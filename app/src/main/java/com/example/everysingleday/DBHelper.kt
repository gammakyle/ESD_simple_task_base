package com.example.everysingleday

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                DATA_COl + " TEXT," +
                TIME_COl + " TEXT," +
                TEXT_COl + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    public fun dropbase()
    {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db)
    }
    // This method is for adding data in our database
    fun addNote(esd_data : String, esd_time : String, esd_text: String ){
        // below we are creating
        // a content values variable
        val values = ContentValues()
        // we are inserting our values
        // in the form of key-value pair
        values.put(DATA_COl, esd_data)
        values.put(TIME_COl, esd_time)
        values.put(TEXT_COl, esd_text)
        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase
        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)
        // at last we are
        // closing our database
        db.close()
    }
    // below method is to get
    // all data from our database
    fun getNode(): Cursor? {
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }
    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "esd_base"
        // below is the variable for database version
        private val DATABASE_VERSION = 1
        // below is the variable for table name
        val TABLE_NAME = "esd_table"
        // below is the variable for id column
        val ID_COL = "esd_id"
        // below is the variable for name column
        val DATA_COl = "esd_data"
        val TIME_COl = "esd_time"
        val TEXT_COl = "esd_text"
    }
}