package com.onur.dailym.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class QuotesModel {
    @ColumnInfo(name = "quotesSql")
    @SerializedName("q")
    var quote : String ?= null
    @ColumnInfo(name = "authorSql")
    @SerializedName("a")
    var author : String ?= null

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}