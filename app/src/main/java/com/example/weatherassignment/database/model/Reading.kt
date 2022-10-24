package com.example.weatherassignment.database.model

import androidx.room.Entity

@Entity(tableName = "readings_table", primaryKeys = ["year","month","day"])
data class Reading(
    val year: Int,
    val month: Int,
    val day: Int,
    val maxTemp: Int?,
    val meanTemp: Int?,
    val minTemp: Int?,
    val maxHum: Int?,
    val meanHum: Int?
) {

    companion object {
        fun fromString(string: String): Reading {
            val splitString = string.split(",")
            val splitDate = splitString[0].split("-")
            return Reading(
                splitDate[0].toInt(),
                splitDate[1].toInt(),
                splitDate[2].toInt(),
                splitString[1].toIntOrNull(),
                splitString[2].toIntOrNull(),
                splitString[3].toIntOrNull(),
                splitString[7].toIntOrNull(),
                splitString[8].toIntOrNull()
            )
        }
    }
}