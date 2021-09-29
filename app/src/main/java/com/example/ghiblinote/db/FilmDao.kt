package com.example.ghiblinote.db

import androidx.room.*
import com.example.ghiblinote.data.Film

@Dao
interface FilmDao {
    @Insert
    suspend fun insert(films: List<Film>)

    @Query("DELETE FROM film_table")
    suspend fun clear()

    @Transaction
    suspend fun clearAndInsert(films: List<Film>) {
        clear()
        insert(films)
    }

    /** 映画一覧を全て取得 */
    @Query("SELECT * FROM film_table")
    fun findAll(): List<Film>

    /** 検索ワードから映画一覧を取得 */
    @Query("SELECT * FROM film_table WHERE title LIKE '%' || :search || '%' OR originalTitle LIKE '%' || :search || '%' ")
    fun findAllFromSearch(search: String): List<Film>

    /** 映画IDから映画情報を取得 */
    @Query("SELECT * FROM film_table WHERE film_id == :id")
    fun findFilmFromId(id: String): Film

    /** 「ユーザースコア」「メモ」の情報を更新 */
    @Query("UPDATE film_table SET user_score = :userScore, memo = :memo WHERE film_id = :id")
    fun updateCustomInfo(id: String, userScore: Int, memo: String)

    /** 「お気に入り」の情報を更新 */
    @Query("UPDATE film_table SET is_favorite = :isFavorite WHERE film_id = :id")
    fun updateFavoriteStateFromId(id: String, isFavorite: Boolean)

    /** メモのある映画リストを取得 */
    @Query("SELECT * FROM film_table WHERE memo IS NOT NULL AND memo != ''")
    fun findMemoExistenceList(): List<Film>

    /** 引数で渡された映画IDのお気に入り状態を登録 */
    @Query("UPDATE film_table SET is_favorite = 1 WHERE film_id in (:isFavoriteList)")
    fun updateFavoriteStateFromList(isFavoriteList: List<String>)

    /** 引数で渡された映画IDのお気に入り状態を解除 */
    @Query("UPDATE film_table SET is_favorite = 0 WHERE film_id in (:nonFavoriteList)")
    fun updateNonFavoriteStateFromList(nonFavoriteList: List<String>)

    /** お気に入りリストを取得 */
    @Query("SELECT * FROM film_table WHERE is_favorite == 1")
    fun findFavoriteList(): List<Film>
}