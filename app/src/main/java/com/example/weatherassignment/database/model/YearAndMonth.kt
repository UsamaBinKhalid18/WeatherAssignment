package com.example.weatherassignment.database.model

data class YearAndMonth(val year: Int, val month: Int) {
    override operator fun equals(other: Any?): Boolean {
        if (other is YearAndMonth) {
            return other.month == month && other.year == year
        }
        return false
    }

}