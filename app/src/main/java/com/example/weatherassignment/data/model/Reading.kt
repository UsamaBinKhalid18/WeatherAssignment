package com.example.weatherassignment.data.model

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
        fun listFromString(stringList: List<String>): List<Reading> {
            val columns = stringList[0].split(",")
            val dateIndex =
                if (columns.indexOf("PKT") == -1) columns.indexOf("PKST") else columns.indexOf("PKT")

            val maxTempIndex = columns.indexOf("Max TemperatureC")
            val meanTempIndex = columns.indexOf("Mean TemperatureC")
            val minTempIndex = columns.indexOf("Min TemperatureC")
            val maxHumIndex = columns.indexOf("Max Humidity")
            val meanHumIndex = columns.indexOf(" Mean Humidity")

            val list = mutableListOf<Reading>()

            for (row in stringList.subList(1, stringList.size)) {
                val fields = row.split(",")
                list.add(
                    Reading(
                        fields[dateIndex].split("-")[0].toInt(),
                        fields[dateIndex].split("-")[1].toInt(),
                        fields[dateIndex].split("-")[2].toInt(),
                        fields[maxTempIndex].toIntOrNull(),
                        fields[meanTempIndex].toIntOrNull(),
                        fields[minTempIndex].toIntOrNull(),
                        fields[maxHumIndex].toIntOrNull(),
                        fields[meanHumIndex].toIntOrNull(),
                    )
                )
            }
            return list
        }
    }
}