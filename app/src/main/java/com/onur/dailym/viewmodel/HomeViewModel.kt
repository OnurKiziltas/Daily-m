package com.onur.dailym.viewmodel

import android.app.Application
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.onur.dailym.Utils.CustomSharedPreferences
import com.onur.dailym.model.LocationModel
import com.onur.dailym.model.QuotesModel
import com.onur.dailym.model.WeatherModel
import com.onur.dailym.servies.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
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
    val quotes = MutableLiveData<List<QuotesModel>>()




    private val weatherApiServices = WeatherApiServices()
    private val quotesApiServices = QuotesApiServices()
    private val disposable = CompositeDisposable()

    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 12 * 60 * 60 * 1000 * 1000 * 1000L



    fun getDataFromAPI(latitute:String,longitute:String){

        disposable.add(
                weatherApiServices.getData(latitute,longitute)
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
    fun refreshQuotes(){

        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getQuotesFromSQLite()
        }else{
            getQuotesFromAPI()
        }


    }
    fun getQuotesFromSQLite(){

        launch {
            val quotesSql = QuotesDatabase(getApplication()).QuotesDao().getQuotes()
            showQuotes(quotesSql)
        }

    }

    fun getQuotesFromAPI(){

        disposable.add(
                quotesApiServices.getQuotes()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<QuotesModel>>(){
                            override fun onError(e: Throwable) {
                                e.printStackTrace()

                            }


                            override fun onSuccess(t: List<QuotesModel>) {

                                storeInSQLite(t)
                            }

                        })
        )
    }
    private fun showQuotes(quotesList : List<QuotesModel>){
        quotes.value = quotesList
    }
    private fun storeInSQLite(list : List<QuotesModel>){
        launch {
            val dao = QuotesDatabase(getApplication()).QuotesDao()
            dao.deleteQuotes()
            val listlong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = listlong[i].toInt()
                i = i + 1
            }
            showQuotes(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }


}