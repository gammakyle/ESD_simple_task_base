package com.example.everysingleday

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.util.*

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val deleteall: Button = findViewById(R.id.deleteall)
        deleteall.setOnClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            Toast.makeText(this, "База очищена.", Toast.LENGTH_SHORT).show()
                            val db = DBHelper(this, null)
                            db.dropbase()

                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Действительно удалить базу?")
                .setPositiveButton("Да", dialogClickListener)
                .setNegativeButton("Нет", dialogClickListener)
                .show()
        }
        //-----------------------------------//
        val savebase: Button = findViewById(R.id.savebase)
        savebase.setOnClickListener {
            val exportDir = File(Environment.getExternalStorageDirectory(), "")
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }
            val file = File(exportDir, "documents/ESD_data_dump.csv")
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
            try {
                file.createNewFile()
                val csvWrite = CSVWriter(FileWriter(file))
                val db = DBHelper(this, null)
                val curCSV = db.getNode()
                csvWrite.writeNext(curCSV!!.getColumnNames())
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    val arrStr = arrayOfNulls<String>(curCSV.getColumnCount())
                    for (i in 0 until curCSV.getColumnCount() ) arrStr[i] = curCSV.getString(i)
                    csvWrite.writeNext(arrStr)
                }
                csvWrite.close()
                curCSV.close()
                Toast.makeText(this, "База сохранена.", Toast.LENGTH_SHORT).show()
            } catch (sqlEx: Exception) {
            }
        }
    }
}