package com.example.weatherassignment.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherassignment.database.model.Reading
import com.example.weatherassignment.database.model.ResultDateValue

@Dao
interface ReadingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReading(reading: Reading)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReadings(reading: List<Reading>)

    @Query(
        """
        select year,month,day,max(maxTemp) as value 
        from readings_table
        where year=:year
        limit 1
    """
    )
    suspend fun maxTempForYear(year: Int): ResultDateValue

    @Query(
        """
        select year,month,day,min(minTemp) as value
        from readings_table
        where year=:year
        limit 1
    """
    )
    suspend fun minTempForYear(year: Int): ResultDateValue

    @Query(
        """
        select year,month,day,max(maxHum) as value
        from readings_table
        where year=:year
        limit 1
    """
    )
    suspend fun maxHumForYear(year: Int): ResultDateValue

    @Query(
        """
        select avg(maxTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMaxTempForMonth(year: Int, month: Int): Float?

    @Query(
        """
        select avg(minTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMinTempForMonth(year: Int, month: Int): Float?

    @Query(
        """
        select avg(meanTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMeanHumForMonth(year: Int, month: Int): Float?

}