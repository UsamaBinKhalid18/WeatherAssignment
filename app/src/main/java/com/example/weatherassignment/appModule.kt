package com.example.weatherassignment

import android.app.Application
import com.example.weatherassignment.database.ReadingsDataBase
import com.example.weatherassignment.database.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {

    @Provides
    @Singleton
    fun dataBase(app:Application):ReadingsDataBase=ReadingsDataBase.getInstance(app)

    @Provides
    @Singleton
    fun repository(dataBase: ReadingsDataBase):Repository=Repository(dataBase)

}