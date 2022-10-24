package com.example.weatherassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherassignment.database.Repository
import com.example.weatherassignment.database.model.ResultDateAndValue
import com.example.weatherassignment.database.model.YearAndMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {

    private val _yearList= mutableListOf(2004)
    private val _monthList= mutableListOf(YearAndMonth(2006,3))
    private val _yearResult= mutableListOf<List<ResultDateAndValue>>()
    private val _monthResult= mutableListOf<List<Float>>()

    val yearList:List<Int>
        get()=_yearList
    val monthList:List<YearAndMonth>
        get()=_monthList
    val yearResult:List<List<ResultDateAndValue>>
        get()=_yearResult
    val monthResult:List<List<Float>>
        get()=_monthResult

    fun updateYearList(year:Int,position:Int?=null){
        if(position!=null){
            _yearList[position]=year
        }else{
            _yearList.add(year)
        }
    }

    fun updateMonthList(year:Int,month:Int,position:Int?=null){
        if(position!=null){
            _monthList[position]=YearAndMonth(year,month)
        }else{
            _monthList.add(YearAndMonth(year,month))
        }
    }

    fun generateYearResult(){
        _yearResult.clear()
        viewModelScope.launch(Dispatchers.IO) {
            for (year in yearList){
                val maxTemp=repository.maxTempForYear(year)
                val minTemp=repository.minTempForYear(year)
                val maxHum=repository.maxHumForYear(year)
                _yearResult.add(listOf(maxTemp,minTemp,maxHum))
            }
        }
    }

    fun generateMonthResult(){
        _monthResult.clear()
        viewModelScope.launch(Dispatchers.IO) {
            for (yearMonth in monthList){
                val avgMaxTemp=repository.avgMaxTempForMonth(yearMonth.year,yearMonth.month)
                val avgMinTemp=repository.avgMinTempForMonth(yearMonth.year,yearMonth.month)
                val avgMeanHum=repository.avgMeanHumForMonth(yearMonth.year,yearMonth.month)
                _monthResult.add(listOf(avgMaxTemp,avgMinTemp,avgMeanHum))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: Repository):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}