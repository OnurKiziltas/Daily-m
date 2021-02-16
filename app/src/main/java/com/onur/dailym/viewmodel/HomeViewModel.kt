package com.onur.dailym.viewmodel

import android.app.Application
import android.text.Html
import com.onur.dailym.model.WeatherModel
import com.onur.dailym.servies.LocationLiveData
import com.onur.dailym.servies.WeatherAPI
import com.onur.dailym.servies.WeatherApiServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(application : Application) : BaseViewModel(application){


    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData



    private val countryApiServies = WeatherApiServices()
    private val disposable = CompositeDisposable()

    private val BASE_URL = "http://api.openweathermap.org/"
    private val APP_ID = "2f4e7b2a3a9e2dedc626e73de04f1382"
    private val lat = "40.4167"
    private val lon = "-3.7036"

    /* fun loadData(){

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WeatherAPI::class.java)

        compositeDisposable?.add(retrofit.getCurrentWeatherData(lat,lon,APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))
         println("builder")
    }
    fun handleResponse(weatherModel : WeatherModel){

        println(weatherModel.main!!.temp.toString())

    }*/
    fun getDataFromAPI(){

        disposable.add(
                countryApiServies.getData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                            override fun onError(e: Throwable) {
                                println(e)
                                e.printStackTrace()
                            }

                            override fun onSuccess(t: WeatherModel) {
                                println(t.main?.temp.toString())

                            }

                        })
        )
    }
  /* fun getCurrentData() {

       val retrofit = Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       val service = retrofit.create(WeatherAPI::class.java)
       val call = service.getCurrentWeatherData(lat, lon, APP_ID)

       call.enqueue(object : Callback<WeatherModel> {
           override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
               if (response.code() == 200) {
                   val weatherResponse = response.body()!!

                   val stringBuilder = Html.fromHtml("<b>País:</b> " +
                           weatherResponse.sys!!.country +
                           "<br>" +
                           "<b>Temperatura:</b> " +
                           (weatherResponse.main!!.temp - 273).toString().substring(0,3) + " ºC" +
                           "<br>" +
                           "<b>Temperatura(Min):</b> " +
                           (weatherResponse.main!!.temp_min - 273).toString().substring(0,3) + " ºC" +
                           "<br>" +
                           "<b>Temperatura(Max):</b> " +
                           (weatherResponse.main!!.temp_max - 273).toString().substring(0,3) + " ºC" +
                           "<br>" +
                           "<b>Humedad:</b> " +
                           weatherResponse.main!!.humidity +
                           "<br>" +
                           "<b>Presión:</b> " +
                           weatherResponse.main!!.pressure)

                   println(stringBuilder)
               }
           }

           override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
              println(t.message)
           }
       })
   }*/


}