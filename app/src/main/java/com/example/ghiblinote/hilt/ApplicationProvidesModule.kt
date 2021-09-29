package com.example.ghiblinote.hilt

import android.content.Context
import androidx.room.Room
import com.example.ghiblinote.api.GhibliApiService
import com.example.ghiblinote.db.FilmDatabase
import com.example.ghiblinote.db.FilmDao
import com.example.ghiblinote.repository.FilmRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * DI用ProvidesModule
 * @Inject が付いたプロパティや引数に提供する値の実体を定義
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationProvidesModule {

    /**
     * OkHttpClientの提供
     * @return OkHttpClientのインスタンス
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    /**
     * [GhibliApiService]の提供
     * @param okHttpClient
     * @return [GhibliApiService]のインスタンス
     */
    @Singleton
    @Provides
    fun provideGhibliApiService(okHttpClient: OkHttpClient): GhibliApiService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GhibliApiService::class.java)
    }

    /**
     * [GhibliApiService]の提供
     * @param ghibliApiService
     * @return [FilmRepository]のインスタンス
     */
    @Provides
    fun provideFilmRepository(
        ghibliApiService: GhibliApiService,
        filmDao: FilmDao
    ): FilmRepository {
        return FilmRepository(ghibliApiService, filmDao)
    }

    /**
     * [FilmDatabase]の提供
     * @param context コンテキスト
     * @return [FilmDatabase]のインスタンス
     */
    @Singleton
    @Provides
    fun provideFilmDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FilmDatabase::class.java, "film_db").build()

    /**
     * [FilmDao]の提供
     * @param db [FilmDatabase]
     * @return [FilmDao]のインスタンス
     */
    @Singleton
    @Provides
    fun provideFilmDao(db: FilmDatabase) = db.filmDao()

    companion object {
        // ベースURL
        const val BASE_URL = "https://ghibliapi.herokuapp.com/"
    }
}