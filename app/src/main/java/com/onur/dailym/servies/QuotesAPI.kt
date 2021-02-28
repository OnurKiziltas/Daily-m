package com.onur.dailym.servies

import com.onur.dailym.model.QuotesModel
import io.reactivex.Single
import retrofit2.http.GET

interface QuotesAPI {
    @GET("api/random")
    fun getQuotes(): Single<List<QuotesModel>>
}