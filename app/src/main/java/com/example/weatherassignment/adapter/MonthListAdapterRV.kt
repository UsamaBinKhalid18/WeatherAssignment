package com.example.weatherassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherassignment.R
import com.example.weatherassignment.database.model.YearMonth

class MonthListAdapterRV(
    private val yearAdapter: ArrayAdapter<Int>,
    private val monthAdapter: ArrayAdapter<CharSequence>,
    private val updateYearMonth: (year: Int, month: Int, position: Int) -> Unit
) : ListAdapter<YearMonth, MonthListAdapterRV.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.year_month_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.yearSpinner.adapter = yearAdapter
        holder.yearSpinner.setSelection(yearAdapter.getPosition(item.year))
        holder.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateYearMonth(yearAdapter.getItem(p2)!!, item.month, holder.adapterPosition)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        holder.monthSpinner.adapter = monthAdapter
        holder.monthSpinner.setSelection(item.month - 1)
        holder.monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateYearMonth(item.year, p2 + 1, holder.adapterPosition)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearSpinner: Spinner = view.findViewById(R.id.year_spinner)
        val monthSpinner: Spinner = view.findViewById(R.id.month_spinner)
    }

    companion object {

        private object ItemCallback : DiffUtil.ItemCallback<YearMonth>() {

            override fun areItemsTheSame(oldItem: YearMonth, newItem: YearMonth) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: YearMonth, newItem: YearMonth) =
                (oldItem.month == newItem.month && oldItem.year == newItem.year)
        }
    }
}