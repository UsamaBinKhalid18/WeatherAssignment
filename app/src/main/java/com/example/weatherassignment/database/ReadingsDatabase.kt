package com.example.weatherassignment.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherassignment.R
import com.example.weatherassignment.database.dao.ReadingDao
import com.example.weatherassignment.database.model.Reading
import com.example.weatherassignment.util.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val listOfMonths = listOf(
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec"
)

@Database(entities = [Reading::class], version = 1, exportSchema = false)
abstract class ReadingsDataBase : RoomDatabase() {

    abstract fun getReadingDao(): ReadingDao

    companion object {
        @Volatile
        private var INSTANCE: ReadingsDataBase? = null
        fun updateDataBase(context:Context){
            val dao=getInstance(context).getReadingDao()
            Log.d("TAG", "onCreate: ")
            CoroutineScope(Dispatchers.IO).launch {
                val validYears = Provider.provideValidYears(context)
                Log.d("TAG", "onCreate: $validYears")
                for (year in validYears) {
                    for (month in listOfMonths) {
                        Log.d("TAG", "onCreate: $year $month")
                        try {
                            val stringList = context.assets.open(
                                context.resources.getString(
                                    R.string.file_name,
                                    year, month
                                )
                            ).reader().readLines()
                            dao.addReadings(Reading.listFromString(stringList))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        fun getInstance(context: Context): ReadingsDataBase {
            Log.d("TAG", "getInstance: ")
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ReadingsDataBase::class.java,
                    "readingDB"
                ).build()

                INSTANCE = tempInstance
                return tempInstance
            }
        }
    }
}