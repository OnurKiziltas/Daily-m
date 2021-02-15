package com.onur.dailym.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onur.dailym.servies.LocationLiveData

class DashboardViewModel(application : Application) : BaseViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}