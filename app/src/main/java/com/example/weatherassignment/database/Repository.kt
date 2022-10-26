package com.example.weatherassignment.database

class Repository (dataBase: ReadingsDataBase) {
    private val readingsDao = dataBase.getReadingDao()

    suspend fun maxTempForYear(year: Int) = readingsDao.maxTempForYear(year)

    suspend fun minTempForYear(year: Int) = readingsDao.minTempForYear(year)

    suspend fun maxHumForYear(year: Int) = readingsDao.maxHumForYear(year)

    suspend fun avgMaxTempForMonth(year: Int, month: Int) =
        readingsDao.avgMaxTempForMonth(year, month)

    suspend fun avgMinTempForMonth(year: Int, month: Int) =
        readingsDao.avgMinTempForMonth(year, month)

    suspend fun avgMeanHumForMonth(year: Int, month: Int) =
        readingsDao.avgMeanHumForMonth(year, month)

}