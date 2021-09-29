package com.example.ghiblinote.view.note

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
class MemoListViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    /**
     * メモした映画一覧
     */
    val filmList = MutableLiveData<List<Film>>()

    /**
     * メモした映画一覧を取得
     */
    fun getMemoList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                filmList.postValue(repository.getMemoFilmList())
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