package com.example.weatherassignment.util

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.weatherassignment.data.MyDataSource
import com.example.weatherassignment.data.Repository
import com.example.weatherassignment.viewmodel.MainViewModel

object Provider {
    private lateinit var viewModel: MainViewModel
    private fun provideDataSource(context: Context) = MyDataSource(context.applicationContext)

    private fun provideRepository(context: Context) = Repository(provideDataSource(context))

    fun provideYearArrayAdapter(context: Context) = ArrayAdapter(
        context,
        com.example.weatherassignment.R.layout.spinner_item,
        provideValidYears(context).toList()
    )

    fun provideMonthArrayAdapter(context: Context) = ArrayAdapter.createFromResource(
        context,
        com.example.weatherassignment.R.array.months,
        com.example.weatherassignment.R.layout.spinner_item
    )

    fun provideViewModel(owner: androidx.lifecycle.ViewModelStoreOwner, context: Context) =
        if (this::viewModel.isInitialized) {
            viewModel
        } else {
            viewModel =
                ViewModelProvider(
                    owner,
                    MainViewModel.Factory(provideRepository(context))
                )[MainViewModel::class.java]
            viewModel
        }

    fun provideValidYears(context: Context):IntRange{
        var min=Int.MAX_VALUE
        var max=0
        val filenames=context.assets.list("WeatherDetails/")
        for (name in filenames!!){
            val year=name.split("_")[2].toInt()
            min= kotlin.math.min(min,year)
            max= kotlin.math.max(max,year)
        }
        return min..max
    }
}