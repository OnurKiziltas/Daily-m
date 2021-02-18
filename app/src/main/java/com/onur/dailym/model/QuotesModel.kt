package com.onur.dailym.model

import com.google.gson.annotations.SerializedName

class QuotesModel {
    @SerializedName("text")
    var text : String ?= null
    @SerializedName("author")
    var author : String ?= null
}