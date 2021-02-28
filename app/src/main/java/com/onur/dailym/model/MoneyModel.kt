package com.onur.dailym.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


class MoneyModel {

    @SerializedName("USD")
    var usd : SAD  ?= null

    @SerializedName("EUR")
    var eur : SAD  ?= null

    @SerializedName("BTC")
    var btc : SAD  ?= null

    @SerializedName("ETH")
    var eth : SAD  ?= null

    @SerializedName("GA")
    var ga : SAD  ?= null

    @SerializedName("EUR/USD")
    var eurusd : SAD  ?= null

    @SerializedName("XE100")
    var bist : SAD  ?= null


}
class SAD{

    @SerializedName("satis")
    var satis : Double ?= null

    @SerializedName("alis")
    var alis : Double ?= null

    @SerializedName("degisim")
    var degisim : String ?= null


}