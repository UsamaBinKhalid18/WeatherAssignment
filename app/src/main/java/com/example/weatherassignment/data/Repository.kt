package com.example.weatherassignment.data

class Repository(val dataSource: MyDataSource) {


    suspend fun maxTempForYear(year: Int) = dataSource.maxTempForYear(year)

    suspend fun minTempForYear(year: Int) = dataSource.minTempForYear(year)

    suspend fun maxHumForYear(year: Int) = dataSource.maxHumForYear(year)

    suspend fun avgMaxTempForMonth(year: Int, month: Int) =
        dataSource.avgMaxTempForMonth(year, month)

    suspend fun avgMinTempForMonth(year: Int, month: Int) =
        dataSource.avgMinTempForMonth(year, month)

    suspend fun avgMeanHumForMonth(year: Int, month: Int) =
        dataSource.avgMeanHumForMonth(year, month)

}