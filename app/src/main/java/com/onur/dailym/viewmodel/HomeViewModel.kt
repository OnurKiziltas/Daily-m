package com.onur.dailym.viewmodel

import android.app.Application
import android.text.Html
import androidx.lifecycle.MutableLiveData
import com.onur.dailym.model.LocationModel
import com.onur.dailym.model.WeatherModel
import com.onur.dailym.servies.LocationLiveData
import com.onur.dailym.servies.WeatherAPI
import com.onur.dailym.servies.WeatherApiServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
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

    val weather = MutableLiveData<WeatherModel>()




    private val countryApiServies = WeatherApiServices()
    private val disposable = CompositeDisposable()



    fun getDataFromAPI(latitute:String,longitute:String){

        disposable.add(
                countryApiServies.getData(latitute,longitute)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                            override fun onError(e: Throwable) {
                                println(e)
                                e.printStackTrace()
                            }

                            override fun onSuccess(t: WeatherModel) {

                                weather.value =  t


                            }

                        })
        )
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}