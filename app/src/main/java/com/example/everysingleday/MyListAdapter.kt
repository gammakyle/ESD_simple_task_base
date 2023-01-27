package com.example.everysingleday

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
class MyListAdapter(private val context: Activity, private val datatime: Array<String?>, private val description: Array<String?>, private val idid: Array<String?>)
    : ArrayAdapter<String>(context, R.layout.custom_list, datatime) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleId = rowView.findViewById(R.id.idid) as TextView
        val titleDatatime = rowView.findViewById(R.id.datatime) as TextView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleId.text = idid[position]
        titleDatatime.text = datatime[position]
        subtitleText.text = description[position]

        return rowView
    }
}