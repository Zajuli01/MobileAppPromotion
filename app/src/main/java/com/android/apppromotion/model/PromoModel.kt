package com.android.apppromotion.model

import com.google.gson.annotations.SerializedName

data class PromoModel(
    @SerializedName("nama") var nama: String? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("img") var img: Img = Img()
)

data class Img(
    @SerializedName("formats") var formats: Formats? = Formats()
)

data class Medium(
    @SerializedName("url") var url: String? = null,
    @SerializedName("ext") var ext: String? = null,
)

data class Formats(
    @SerializedName("medium") var medium: Medium? = Medium()
)