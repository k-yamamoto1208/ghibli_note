package com.example.ghiblinote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "film_table")
data class Film(
    // 映画ID
    @ColumnInfo(name = "film_id") @Json(name = "id") val filmId: String,
    // 英語タイトル
    val title: String,
    // 日本語タイトル
    @ColumnInfo @Json(name = "original_title") val originalTitle: String,
    // 映画説明文
    val description: String,
    // 監督
    val director: String,
    // プロデューサー
    val producer: String,
    // 公開年
    @ColumnInfo @Json(name = "release_date") val releaseDate: String,
    // Rotten Tomatoes評価
    @ColumnInfo @Json(name = "rt_score") val rtScore: String,
    // お気に入り
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean? = null,
    // ユーザー評価
    @ColumnInfo(name = "user_score") val userScore: Int? = null,
    // メモ
    val memo: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "db_id") var dbId: Int = 0
}
