package com.example.weatherassignment.util

import android.content.Context
import android.widget.ArrayAdapter

object Provider {

    fun provideYearArrayAdapter(context: Context) = ArrayAdapter(
        context,
        com.example.weatherassignment.R.layout.spinner_item,
        context.resources.getIntArray(com.example.weatherassignment.R.array.years).asList()
    )

    fun provideMonthArrayAdapter(context: Context) = ArrayAdapter.createFromResource(
        context,
        com.example.weatherassignment.R.array.months,
        com.example.weatherassignment.R.layout.spinner_item
    )
}