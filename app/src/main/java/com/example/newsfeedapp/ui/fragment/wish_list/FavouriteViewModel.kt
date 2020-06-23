package com.example.newsfeedapp.ui.fragment.wish_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapp.data.FavRepo
import com.example.newsfeedapp.data.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel @ViewModelInject constructor (private val favRepo: FavRepo) : ViewModel() {




    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        favRepo.insert(article)
    }

    fun getSavedArticles() = favRepo.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        favRepo.deleteArticle(article)
    }

    fun deleteAllArticles() = viewModelScope.launch(Dispatchers.IO) {
        favRepo.deleteAllArticle()
    }

    fun isFavourite(url: String) = favRepo.isFavorite(url)

}