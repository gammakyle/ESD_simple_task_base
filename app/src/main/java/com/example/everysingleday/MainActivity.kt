package com.example.everysingleday

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    //-------------------------------------//
    val formatter_d = SimpleDateFormat("D.MM.yyyy")
    val formatter_t = SimpleDateFormat("HH:mm")
    //-------------------------------------//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-------------------------------------//
        val textView_date = findViewById<EditText>(R.id.editTextDate)
        val textView_time = findViewById<EditText>(R.id.editTextTime)
        val textView_name = findViewById<EditText>(R.id.enterName)
        //-------------------------------------//
        textView_date.setEnabled(false);
        textView_time.setEnabled(false);
        //-------------------------------------//
        settext(textView_date,textView_time )
        //-------------------------------------//
        updateTextView(textView_date,textView_time);
        //-------------------------------------//
        val button: Button = findViewById(R.id.addTask)
        button.setOnClickListener {
            savedata(textView_date,textView_time,textView_name);
        }
        val button_history: Button = findViewById(R.id.Button1)
        button_history.setOnClickListener {
            val intent_his = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent_his)
        }
        val button_settings: Button = findViewById(R.id.Button2)
        button_settings.setOnClickListener {
            val intent_set = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent_set)
        }
    }
    //-------------------------------------//
    public fun updateTextView(textView_date: TextView, textView_time: TextView) {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                settext(textView_date,textView_time )
                start()
            }
        }.start()
    }
    //-------------------------------------//
    public fun settext(textView_date: TextView, textView_time: TextView)
    {
        val date = formatter_d.format(Date())
        val time = formatter_t.format(Date())
        textView_date.setText(date.toString())
        textView_time.setText(time.toString())
    }
    //-------------------------------------//
    public fun savedata(textView_date: TextView, textView_time: TextView, textView_name: TextView)
    {
        if(textView_name.length() > 0) {
            try{
                val date = formatter_d.format(Date())
                val time = formatter_t.format(Date())
                val db = DBHelper(this, null)
                db.addNote(date.toString(), time.toString(), textView_name.text.toString())
                Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show()
                textView_name.text = ""
            }
            catch (e: Exception)
            {
                Toast.makeText(this, "Что-то неладное...", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
        }
    }
    //---------------------------//
}
