package com.example.weatherassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherassignment.R
import com.example.weatherassignment.database.dao.ReadingDao
import com.example.weatherassignment.database.model.Reading
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

        fun getInstance(context: Context): ReadingsDataBase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ReadingsDataBase::class.java,
                    "readingDB"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val dao = getInstance(context).getReadingDao()
                        CoroutineScope(Dispatchers.IO).launch {
                            for (year in 2004..2016) {
                                for (month in listOfMonths) {
                                    addToDatabase(year,month,dao)

                                }
                            }
                        }
                    }

                    suspend fun addToDatabase(year:Int,month:String,dao:ReadingDao){
                        try {
                            val stringlist = context.assets.open(
                                context.resources.getString(
                                    R.string.file_name,
                                    year, month
                                )
                            ).reader().readLines().let {
                                it.subList(1, it.size)
                            }

                            for (i in stringlist) {
                                dao.addReading(Reading.fromString(i))
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }).build()

                INSTANCE = tempInstance
                return tempInstance
            }
        }
    }
}