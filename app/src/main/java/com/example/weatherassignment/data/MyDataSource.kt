package com.example.weatherassignment.data

import android.content.Context
import com.example.weatherassignment.R
import com.example.weatherassignment.data.model.Reading
import com.example.weatherassignment.data.model.ResultDateValue

class MyDataSource(private val context: Context) {
    fun maxTempForYear(year: Int): ResultDateValue {
        val listOfReadings = getList(year)
        var maxTemp = 0
        var month = 0
        var day = 0
        for (reading in listOfReadings) {
            if (reading.maxTemp != null && reading.maxTemp > maxTemp) {
                maxTemp = reading.maxTemp
                month = reading.month
                day = reading.day
            }
        }
        return ResultDateValue(year, month, day, maxTemp)
    }

    fun minTempForYear(year: Int): ResultDateValue {
        val listOfReadings = getList(year)
        var minTemp = Int.MAX_VALUE
        var month = 0
        var day = 0
        for (reading in listOfReadings) {
            if (reading.minTemp != null && reading.minTemp < minTemp) {
                minTemp = reading.minTemp
                month = reading.month
                day = reading.day
            }
        }
        return ResultDateValue(year, month, day, minTemp)
    }

    fun maxHumForYear(year: Int): ResultDateValue {
        val listOfReadings = getList(year)
        var maxHum = 0
        var month = 0
        var day = 0
        for (reading in listOfReadings) {
            if (reading.maxHum != null && reading.maxHum > maxHum) {
                maxHum = reading.maxHum
                month = reading.month
                day = reading.day
            }
        }
        return ResultDateValue(year, month, day, maxHum)
    }

    fun avgMaxTempForMonth(year: Int, month: Int): Float? {
        val listOfReadings =
            getList(year, context.resources.getStringArray(R.array.months)[month - 1])
                ?: return null
        val listOfMaxTemp= mutableListOf<Int>()
            for (reading in listOfReadings){
                if(reading.maxTemp!=null){
                    listOfMaxTemp.add(reading.maxTemp)
                }
            }
        return listOfMaxTemp.average().toFloat()
    }

    fun avgMinTempForMonth(year: Int, month: Int): Float? {
        val listOfReadings =
            getList(year, context.resources.getStringArray(R.array.months)[month - 1])
                ?: return null
        val listOfMinTemp= mutableListOf<Int>()
        for (reading in listOfReadings){
            if(reading.minTemp!=null){
                listOfMinTemp.add(reading.minTemp)
            }
        }
        return listOfMinTemp.average().toFloat()
    }

    fun avgMeanHumForMonth(year: Int, month: Int): Float? {
        val listOfReadings =
            getList(year, context.resources.getStringArray(R.array.months)[month - 1])
                ?: return null
        val listOfMeanHum= mutableListOf<Int>()
        for (reading in listOfReadings){
            if(reading.meanHum!=null){
                listOfMeanHum.add(reading.meanHum)
            }
        }
        return listOfMeanHum.average().toFloat()
    }

    private fun getList(year: Int): List<Reading> {
        val list = mutableListOf<Reading>()
        for (month in context.resources.getStringArray(R.array.months)) {
            try {
                val readings = context.assets.open(
                    context.resources.getString(R.string.file_name, year, month)
                ).reader().readLines()
                list.addAll(Reading.listFromString(readings))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return list
    }

    private fun getList(year: Int, month: String): List<Reading>? {
        val list = mutableListOf<Reading>()

        try {
            val readings = context.assets.open(
                context.resources.getString(R.string.file_name, year, month)
            ).reader().readLines()
            list.addAll(Reading.listFromString(readings))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return list
    }
}
