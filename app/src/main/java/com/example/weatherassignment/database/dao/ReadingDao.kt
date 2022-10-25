package com.example.weatherassignment.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherassignment.database.model.Reading
import com.example.weatherassignment.database.model.ResultDateAndValue

@Dao
interface ReadingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReading(reading: Reading)

    @Query(
        """
        select year,month,day,maxTemp as value 
        from readings_table
        where(year==:year and maxTemp=(
            select max(maxTemp)
            from readings_table
            where year=:year
        ))
        limit 1
    """
    )
    suspend fun maxTempForYear(year: Int): ResultDateAndValue

    @Query(
        """
        select year,month,day,minTemp as value
        from readings_table
        where(year==:year and minTemp=(
            select min(minTemp)
            from readings_table
            where year=:year
        ))
        limit 1
    """
    )
    suspend fun minTempForYear(year: Int): ResultDateAndValue

    @Query(
        """
        select year,month,day,maxHum as value
        from readings_table
        where(year==:year and maxHum=(
            select max(maxHum)
            from readings_table
            where year=:year
        ))
        limit 1
    """
    )
    suspend fun maxHumForYear(year: Int): ResultDateAndValue

    @Query(
        """
        select avg(maxTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMaxTempForMonth(year: Int, month: Int): Float

    @Query(
        """
        select avg(minTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMinTempForMonth(year: Int, month: Int): Float

    @Query(
        """
        select avg(meanTemp) 
        from readings_table
        where (year=:year and month=:month)
    """
    )
    suspend fun avgMeanHumForMonth(year: Int, month: Int): Float

}