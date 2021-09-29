package com.example.ghiblinote.repository

import androidx.lifecycle.MutableLiveData
import com.example.ghiblinote.api.GhibliApiService
import com.example.ghiblinote.db.FilmDao
import com.example.ghiblinote.data.Film
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val ghibliApiService: GhibliApiService,
    private val filmDao: FilmDao
) {
    /**
     * 通信エラーについて、LiveDataのBooleanで管理
     * true = エラー発生, false = 通信成功
     */
    val connectionError = MutableLiveData(false)

    /**
     * 映画一覧の取得を行う
     * 通信成功：データベースに取得した映画一覧を登録
     * 通信失敗：通信失敗ダイアログを表示
     */
    suspend fun accessFilms() {
        runCatching { ghibliApiService.getFilms() }
            .onSuccess { response ->
                response.body()?.let { filmList ->
                    filmDao.clearAndInsert(filmList.toList())
                } ?: setConnectionError()
            }
            .onFailure { setConnectionError() }
    }

    /**
     * データベースから映画一覧を取得
     * @return API通信後にデータベースへ保存した映画一覧
     */
    fun findAllFilms() = filmDao.findAll()

    /**
     * 検索ワードを元に、データベースから映画一覧を取得
     *
     * @param searchWord SearchViewに入力した検索ワード
     * @return 検索ワードを元に、データベースから取得した映画一覧
     */
    fun findAllFilmsFromSearch(searchWord: String) = filmDao.findAllFromSearch(searchWord)

    /**
     * 映画IDから、映画情報をデータベース上から取得
     *
     * @param id 映画ID
     * @return [Film] 映画情報
     */
    fun findFilmFromId(id: String) = filmDao.findFilmFromId(id)

    /**
     * 「お気に入り」「ユーザースコア」「メモ」の情報を更新
     *
     * @param id 映画ID
     * @param userScore ユーザー評価
     * @param memo メモ
     */
    fun updateCustomInfo(id: String, userScore: Int, memo: String) =
        filmDao.updateCustomInfo(id, userScore, memo)

    /**
     * 「お気に入り」の情報を更新
     *
     * @param id 映画ID
     * @param isFavorite お気に入り
     */
    fun updateFavoriteStateFromId(id: String, isFavorite: Boolean) =
        filmDao.updateFavoriteStateFromId(id, isFavorite)

    /**
     * 指定した映画IDのお気に入り状態を、DB上で登録する
     *
     * @param favoriteList お気に入り登録対象の映画IDリスト
     */
    fun updateFavoriteStateFromList(favoriteList: List<String>) =
        filmDao.updateFavoriteStateFromList(favoriteList)

    /**
     * 指定した映画IDのお気に入り状態を、DB上で解除する
     *
     * @param nonFavoriteList お気に入り解除対象の映画IDリスト
     */
    fun updateNonFavoriteStateFromList(nonFavoriteList: List<String>) =
        filmDao.updateNonFavoriteStateFromList(nonFavoriteList)

    /**
     * メモのある映画一覧を取得
     */
    fun getMemoFilmList() = filmDao.findMemoExistenceList()

    /**
     * お気に入りした映画を一覧で取得
     *
     * @return お気に入り映画一覧
     */
    fun getFavoriteFilmList(): List<Film> = filmDao.findFavoriteList()

    /**
     * 通信エラーが発生した場合に、[connectionError]にtrueをセットする
     */
    private fun setConnectionError() {
        connectionError.postValue(true)
    }
}