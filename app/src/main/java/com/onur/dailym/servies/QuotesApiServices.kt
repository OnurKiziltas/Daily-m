package com.onur.dailym.servies

import com.onur.dailym.model.QuotesModel
import com.onur.dailym.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QuotesApiServices{
    private val BASE_URL = "https://zenquotes.io/"

    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(QuotesAPI::class.java)
    fun getQuotes(): Single<List<QuotesModel>> {
        return api.getQuotes()
    }
}