package com.onur.dailym.servies

import com.onur.dailym.model.MoneyModel
import com.onur.dailym.model.QuotesModel
import io.reactivex.Single
import retrofit2.http.GET

interface MoneyAPI {
    @GET("embed/para-birimleri.json")
    fun getMoney(): Single<MoneyModel>
}