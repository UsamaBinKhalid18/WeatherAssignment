package com.example.weatherassignment.util

import android.content.Context
import com.example.weatherassignment.database.Repository
import com.example.weatherassignment.database.ReadingsDataBase

object Provider {

    fun provideDataBase(context:Context)=ReadingsDataBase.getInstance(context)

    fun provideRepository(context: Context)= Repository(provideDataBase(context))
}