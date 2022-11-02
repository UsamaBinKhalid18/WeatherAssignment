package com.example.weatherassignment.database.model

data class YearMonth(val year: Int, val month: Int) {
    override operator fun equals(other: Any?): Boolean {
        if (other is YearMonth) {
            return other.month == month && other.year == year
        }
        return false
    }

}