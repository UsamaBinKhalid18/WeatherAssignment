package com.example.weatherassignment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherassignment.R
import com.example.weatherassignment.database.model.ResultDateAndValue

class YearReportAdapter(
    private val list: List<List<ResultDateAndValue>>,
    private val monthArrayAdapter: ArrayAdapter<CharSequence>
) : RecyclerView.Adapter<YearReportAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvYear: TextView = view.findViewById(R.id.tv_year)
        val tvMaxTemp: TextView = view.findViewById(R.id.tv_high_temp)
        val tvLowTemp: TextView = view.findViewById(R.id.tv_low_temp)
        val tvHumidity: TextView = view.findViewById(R.id.tv_humidity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.year_report_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        Log.d("TAG", "onBindViewHolder: $item")
        val context = holder.itemView.context
        holder.tvYear.text = item[0].year.toString()
        holder.tvMaxTemp.text = context.getString(
            R.string.high_temp,
            item[0].value,
            item[0].day,
            monthArrayAdapter.getItem(item[0].month - 1)
        )
        holder.tvLowTemp.text = context.getString(
            R.string.low_temp,
            item[1].value,
            item[1].day,
            monthArrayAdapter.getItem(item[1].month - 1)
        )
        holder.tvHumidity.text = context.getString(
            R.string.most_hum,
            item[2].value,
            item[2].day,
            monthArrayAdapter.getItem(item[2].month - 1)
        )
    }

    override fun getItemCount() = list.count()
}