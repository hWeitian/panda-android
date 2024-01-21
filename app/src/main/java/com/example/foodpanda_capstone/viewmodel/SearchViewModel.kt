package com.example.foodpanda_capstone.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.foodpanda_capstone.model.PlaylistRepository
//import com.example.foodpanda_capstone.model.RecentSearch
//import com.example.foodpanda_capstone.model.SearchRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class SearchViewModel(private val repository: SearchRepository): ViewModel() {
//
//    private val _searchText = MutableLiveData("")
//    val searchText: LiveData<String> = _searchText
//
//    private val _recentSearch = MutableStateFlow<List<RecentSearch>>(emptyList())
//    val recentSearch: StateFlow<List<RecentSearch>> = _recentSearch
//
//    init {
//        getRecentSearch()
//    }
//
//    private fun getRecentSearch() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                repository.fetchRecentSearch(1).collect {
//                    _recentSearch.value = it
//                }
//            } catch (e: Exception) {
//                logErrorMsg("getRecentSearch", e)
//            }
//        }
//    }
//
//    fun updateSearchText(inputText: String) {
//        _searchText.value = inputText
//    }
//
//    fun search() {
//        Log.i("WT", "Keyboard Search Clicked: ${searchText.value.toString()}")
//    }
//}
