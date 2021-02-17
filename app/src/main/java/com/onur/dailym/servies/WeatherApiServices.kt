package com.onur.dailym.servies

import com.onur.dailym.model.LocationModel
import com.onur.dailym.model.WeatherModel
import com.onur.dailym.viewmodel.HomeViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiServices {


    private val BASE_URL = "http://api.openweathermap.org/"
    private val APP_ID = "2f4e7b2a3a9e2dedc626e73de04f1382"
    //private val lat = "40.4167"
    //private val lon = "-3.7036"



  private val api = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
          .create(WeatherAPI::class.java)
    fun getData(lat:String,lon:String): Single<WeatherModel> {
        return api.getCurrentWeatherData(lat, lon, APP_ID)
    }


}