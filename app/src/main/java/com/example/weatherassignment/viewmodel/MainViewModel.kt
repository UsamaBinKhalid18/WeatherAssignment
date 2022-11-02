package com.example.weatherassignment.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherassignment.data.Repository
import com.example.weatherassignment.data.model.ResultDateValue
import com.example.weatherassignment.data.model.YearMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _yearList = mutableListOf(2004)
    private val _monthList = mutableListOf(YearMonth(2004, 7))
    private val _yearResult = mutableListOf<List<ResultDateValue>>()
    private val _monthResult = mutableListOf<List<Float?>>()
    private val _reportGenerated = MutableLiveData(0)

    val yearList: List<Int>
        get() = _yearList
    val monthList: List<YearMonth>
        get() = _monthList
    val yearResult: List<List<ResultDateValue>>
        get() = _yearResult
    val monthResult: List<List<Float?>>
        get() = _monthResult
    val reportGenerated: LiveData<Int>
        get() = _reportGenerated

    fun updateYearList(year: Int, position: Int? = null) {
        if (position != null) {
            _yearList[position] = year
        } else {
            _yearList.add(year)
        }
    }

    fun updateMonthList(year: Int, month: Int, position: Int? = null) {
        if (position != null) {
            _monthList[position] = YearMonth(year, month)
        } else {
            _monthList.add(YearMonth(year, month))
        }
    }

    fun generateYearResult() {
        _yearResult.clear()
        viewModelScope.launch(Dispatchers.IO) {
            for (year in yearList.distinct()) {
                val maxTemp = repository.maxTempForYear(year)
                val minTemp = repository.minTempForYear(year)
                val maxHum = repository.maxHumForYear(year)
                _yearResult.add(listOf(maxTemp, minTemp, maxHum))
                Log.d("TAG", "generateYearResult: $yearResult")
            }
            withContext(Dispatchers.Main) { _reportGenerated.value = 1 }
        }
    }

    fun generateMonthResult() {
        _monthResult.clear()

        viewModelScope.launch(Dispatchers.IO) {
            for (yearMonth in monthList.distinct()) {
                    val avgMaxTemp = repository.avgMaxTempForMonth(yearMonth.year, yearMonth.month)
                    val avgMinTemp = repository.avgMinTempForMonth(yearMonth.year, yearMonth.month)
                    val avgMeanHum = repository.avgMeanHumForMonth(yearMonth.year, yearMonth.month)
                    _monthResult.add(listOf(avgMaxTemp, avgMinTemp, avgMeanHum))
            }
            withContext(Dispatchers.Main) { _reportGenerated.value = 2 }
        }
    }


    fun deleteYearList() {
        _yearList.clear()
    }

    fun deleteMonthList(){
        _monthList.clear()
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}