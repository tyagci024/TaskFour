package com.example.taskfour.service

import com.example.taskfour.model.NewsItem
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {
    //https://raw.githubusercontent.com/tyagci024/pharmacyapi/main/poke.json
    // https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json

    @GET("tyagci024/pharmcy/main/news.json")
    fun getAllNews(): Single<List<NewsItem>>
}