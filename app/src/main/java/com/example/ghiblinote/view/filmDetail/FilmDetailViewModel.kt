package com.example.ghiblinote.view.filmDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    /** 映画詳細 */
    val film = MutableLiveData<Film>()

    /** お気に入り状態 */
    private var isFavorite = false

    /** スコア状態（5段階評価） */
    private var score = 0

    /** メモ */
    private var memo = ""

    /**
     * 映画IDからDBに保存してある映画情報を取得
     *
     * @param id 映画ID
     */
    fun getFilm(id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.findFilmFromId(id)
            }
            // お気に入り状態を設定
            result.isFavorite?.let { setFavoriteState(it) }
            // ユーザースコア状態を設定
            result.userScore?.let { setScore(it) }
            // メモを設定
            result.memo?.let { setMemo(it) }
            // フィールド変数の映画詳細に、取得した情報をセット
            film.value = result
        }
    }

    /**
     * 入力された情報をDBに保存
     */
    fun saveInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 映画IDがnull出なければ、DBの映画情報を更新
                film.value?.filmId?.let { filmId ->
                    repository.updateCustomInfo(filmId, getScore(), getMemo())
                }
            }
        }
    }

    /**
     * お気に入り状態をDBに保存
     */
    fun saveFavoriteState() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 映画IDがnull出なければ、該当映画情報のお気に入りを更新
                film.value?.filmId?.let { filmId ->
                    repository.updateFavoriteStateFromId(filmId, getFavoriteState())
                }
            }
        }
    }

    /**
     * お気に入りの状態を取得
     *
     * @return true：お気に入り状態, false：お気に入り解除状態
     */
    fun getFavoriteState(): Boolean {
        return isFavorite
    }

    /**
     * お気に入りの状態を変更
     *
     * @param boolean true：お気に入り状態に変更, false：お気に入り解除状態に変更
     */
    fun setFavoriteState(boolean: Boolean) {
        isFavorite = boolean
    }

    /**
     * スコアの状態を取得
     *
     * @return スコア
     */
    fun getScore(): Int {
        return score
    }

    /**
     * スコアの状態を変更
     *
     * @param scoreNum ５段階の評価
     */
    fun setScore(scoreNum: Int) {
        score = scoreNum
    }

    /**
     * メモを取得
     *
     * @return メモ
     */
    fun getMemo(): String {
        return memo
    }

    /**
     * メモを変更
     *
     * @param text 入力されたメモ
     */
    fun setMemo(text: String) {
        memo = text
    }
}