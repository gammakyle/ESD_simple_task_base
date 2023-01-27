package com.example.everysingleday

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class HistoryActivity : AppCompatActivity() {

    val List_id = ArrayList<String>()
    val List_date = ArrayList<String>()
    val List_text = ArrayList<String>()




    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        //-------------------------------//

        try{
            val db = DBHelper(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getNode()
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            List_id.add(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)))
            List_date.add(cursor.getString(cursor.getColumnIndex(DBHelper.DATA_COl)) + " - " + cursor.getString(cursor.getColumnIndex(DBHelper.TIME_COl)))
            List_text.add(cursor.getString(cursor.getColumnIndex(DBHelper.TEXT_COl)))

            // moving our cursor to next
            // position and appending values
            while(cursor.moveToNext()){
                List_id.add(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)))
                List_date.add(cursor.getString(cursor.getColumnIndex(DBHelper.DATA_COl)) + " - " + cursor.getString(cursor.getColumnIndex(DBHelper.TIME_COl)))
                List_text.add(cursor.getString(cursor.getColumnIndex(DBHelper.TEXT_COl)))
            }

            // at last we close our cursor
            cursor.close()
        }
        catch (e: Exception)
        {

        }

        //-------------------------------//
        var idArr = arrayOfNulls<String>(List_id.size)
        idArr = List_id.toArray(idArr)
        var dateArr = arrayOfNulls<String>(List_date.size)
        dateArr = List_date.toArray(dateArr)
        var textArr = arrayOfNulls<String>(List_text.size)
        textArr = List_text.toArray(textArr)
        //-------------------------------//
        val listView = findViewById<ListView>(R.id.listing)
        val myListAdapter = MyListAdapter(this,dateArr,textArr,idArr)
        listView.adapter = myListAdapter
        //-------------------------------//
        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            //Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }
    }
}