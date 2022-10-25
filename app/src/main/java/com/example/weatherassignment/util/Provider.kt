package com.example.weatherassignment.util

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.weatherassignment.database.ReadingsDataBase
import com.example.weatherassignment.database.Repository
import com.example.weatherassignment.viewmodel.MainViewModel

object Provider {
    private lateinit var viewModel: MainViewModel
    fun provideDataBase(context: Context) = ReadingsDataBase.getInstance(context)

    fun provideRepository(context: Context) = Repository(provideDataBase(context))

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
}