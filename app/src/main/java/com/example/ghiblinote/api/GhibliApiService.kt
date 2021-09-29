package com.example.ghiblinote.api

import com.example.ghiblinote.data.Film
import retrofit2.Response
import retrofit2.http.GET

interface GhibliApiService {

    /**
     * 映画一覧取得
     * レスポンスのJSONがJsonArrayで始まっているので、ここではArray型にする
     *
     * @return 映画一覧
     */
    @GET("films")
    suspend fun getFilms(): Response<Array<Film>>
}