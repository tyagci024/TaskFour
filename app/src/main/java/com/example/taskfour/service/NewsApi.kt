package com.example.taskfour.service

import com.example.taskfour.model.NewsItem
import com.example.taskfour.utilies.Constants
import retrofit2.http.GET

interface NewsApi {
    //https://raw.githubusercontent.com/tyagci024/pharmacyapi/main/poke.json
    // https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json

    @GET(Constants.END_POINT_NEWS)
    suspend fun getAllNews(): List<NewsItem>
}