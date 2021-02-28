package com.onur.dailym.servies

import com.onur.dailym.model.MoneyModel
import com.onur.dailym.model.QuotesModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoneyApiServices {
    private val BASE_URL = "https://api.genelpara.com/"

    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MoneyAPI::class.java)
    fun getMoney(): Single<MoneyModel> {
        return api.getMoney()
    }
}