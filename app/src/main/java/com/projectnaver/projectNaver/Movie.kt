package com.projectnaver.projectNaver

import com.google.gson.annotations.SerializedName
//Naver 검색 APi body문 위한 object
class Movie(
    @SerializedName("lastBuildDate") var lastBuildDate: String,
               @SerializedName("total") var total: Int,
               @SerializedName("start") var start: Int,
               @SerializedName("display") var display: Int,
               @SerializedName("items") var items: Array<Items>){
      data class Items(
        @SerializedName("title")
        val title: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("pubDate")
        val pubDate: String,
        @SerializedName("director")
        val director: String,
        @SerializedName("userRating")
        val userRating: Double
    )
}