package com.example.ghiblinote.view.favorite

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
class FavoriteViewModel @Inject constructor(private val repository: FilmRepository) : ViewModel() {

    /** お気に入り映画一覧 */
    val favoriteFilmList = MutableLiveData<List<Film>>()

    /**
     * お気に入り映画一覧をDBから取得
     */
    fun getFavoriteFilmList() {
        viewModelScope.launch {
            favoriteFilmList.value = withContext(Dispatchers.IO) {
                repository.getFavoriteFilmList()
            }
        }
    }

    /**
     * お気に入り状態を更新
     */
    fun updateFavoriteStateFromList(favoriteList: List<String>, nonFavoriteList: List<String>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateFavoriteStateFromList(favoriteList)
                repository.updateNonFavoriteStateFromList(nonFavoriteList)
            }
        }
    }
}