package com.example.weatherassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherassignment.R
import com.example.weatherassignment.data.model.YearMonth

class MonthReportAdapter(
    private val listYearMonth: List<YearMonth>,
    private val list: List<List<Float?>>,
    private val monthArrayAdapter: ArrayAdapter<CharSequence>
) : RecyclerView.Adapter<MonthReportAdapter.ViewHolder>() {
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
        val yearMonth = listYearMonth[position]
        val context = holder.itemView.context
        holder.tvYear.text =
            monthArrayAdapter.getItem(yearMonth.month - 1).toString() + " ${yearMonth.year}"
        if(item[0]!=null){
        holder.tvMaxTemp.text = context.getString(R.string.avg_high_temp, item[0])
        holder.tvLowTemp.text = context.getString(R.string.avg_low_temp, item[1])
        holder.tvHumidity.text = context.getString(R.string.avg_mean_hum, item[2])
        }else{
            holder.tvMaxTemp.text=context.getString(R.string.data_not_available)
            holder.tvLowTemp.text=context.getString(R.string.data_not_available)
            holder.tvHumidity.text=context.getString(R.string.data_not_available)
        }
    }

    override fun getItemCount() = list.count()
}