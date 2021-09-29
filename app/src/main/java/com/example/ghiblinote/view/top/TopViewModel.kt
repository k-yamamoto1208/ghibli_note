package com.example.ghiblinote.view.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    /** 映画一覧 */
    val filmList = MutableLiveData<List<Film>>()

    /**
     * 通信エラーについて、LiveDataのBooleanで管理
     * true = エラー発生, false = 通信成功
     */
    val connectionError = repository.connectionError

    /**
     * 映画一覧取得のコルーチンジョブ
     */
    private var accessFilmJob: Job? = null

    /**
     * 通信中の管理（読み込み画像管理）
     */
    val isLoading = MutableLiveData<Boolean>()

    /**
     * 映画一覧をAPI取得
     */
    fun getFilms() {
        // 通信中に切り替え
        isLoading.value = true
        accessFilmJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // データベースに映画一覧がない場合のみAPI通信し、一覧取得する
                repository.findAllFilms().apply {
                    if (isNullOrEmpty()) {
                        repository.accessFilms()
                        // 通信がキャンセルされている場合はここで処理から抜ける
                        if (accessFilmJob?.isActive == false) return@withContext
                        filmList.postValue(repository.findAllFilms())
                    } else {
                        filmList.postValue(this)
                    }
                }
            }
            isLoading.value = false
        }
    }

    /**
     * 検索ワードから該当する映画一覧を取得
     *
     * @param searchWord SearchViewに入力した検索ワード
     */
    fun findAllFromSearch(searchWord: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                filmList.postValue(repository.findAllFilmsFromSearch(searchWord))
            }
        }
    }

    /**
     * 通信をキャンセルする
     */
    fun cancelCoroutineJob() {
        accessFilmJob?.cancel()
    }

    /**
     * 前回の通信がキャンセルされて終わったかどうかをチェックする
     *
     * @return true：通信がキャンセルされた, false：通信成功 or 通信中 or null
     */
    fun isJobCancelled(): Boolean {
        return accessFilmJob?.let {
            it.isCompleted && it.isCancelled
        } ?: false
    }
}