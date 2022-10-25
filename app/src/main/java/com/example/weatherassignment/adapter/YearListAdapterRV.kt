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

class YearListAdapterRV(
    private val yearAdapter: ArrayAdapter<Int>,
    val updateList: (year: Int, position: Int) -> Unit
) : ListAdapter<Int, YearListAdapterRV.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.year_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.yearSpinner.adapter = yearAdapter
        holder.yearSpinner.setSelection(yearAdapter.getPosition(item))
        holder.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateList(yearAdapter.getItem(p2)!!, holder.adapterPosition)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearSpinner: Spinner = view.findViewById(R.id.year_spinner)
    }


    companion object {


        private object ItemCallback : DiffUtil.ItemCallback<Int>() {

            override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        }
    }
}