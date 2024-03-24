package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.RecentSearch
import com.example.foodpanda_capstone.view.ui.composable.ErrorScreen
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.LightGrey
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel


@Composable
fun SearchScreen(navController: NavController, viewModel: PlaylistViewModel) {

    val searchInput by viewModel.searchText.observeAsState("")
    val recentSearch by viewModel.recentSearch.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isInputOnFocus by viewModel.isInputOnFocus.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()

    LaunchedEffect(searchResults) {
        viewModel.getRecentSearch()
    }

    LaunchedEffect(isInputOnFocus) {
        if (isInputOnFocus) {
            viewModel.resetErrorState()
            viewModel.restLoadingState()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSearchText()
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column {
            Spacer(modifier = Modifier.size(10.dp))
            SearchInput(
                isInputOnFocus = isInputOnFocus,
                isEnabled = true,
                isClickable = true,
                placeholderText = "Search for food",
                inputValue = searchInput,
                onSearch = { viewModel.getSearchResult() },
                updateInput = { input -> viewModel.updateSearchText(input) },
                updateIsInputOnFocus = { isFocus -> viewModel.updateIsInputOnFocusState(isFocus) }
            ) {}
            Spacer(modifier = Modifier.size(20.dp))

            if ((searchResults.isEmpty() || isInputOnFocus) && !isError && !isLoading) {
                RecentSearch(
                    recentSearches = recentSearch,
                    search = viewModel::searchRecentSearchKeyword,
                    deleteKeyword = viewModel::deleteRecentSearchKeyword,
                    updateIsInputOnFocus = { isFocus -> viewModel.updateIsInputOnFocusState(isFocus) }
                )
            } else {
                when {
                    isError -> {
                        ErrorScreen(
                            errorTitle = "Oops..No results for $searchInput"
                        )
                    }
                    isLoading -> {
                        LoadingScreen()
                    }
                    searchResults.isNotEmpty() -> {
                        SearchResults(searchResults = searchResults, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResults(searchResults: List<FoodItem>, viewModel: PlaylistViewModel) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSearchResult()
        }
    }
    LazyColumn {
        itemsIndexed(searchResults) { index, result ->
            EditableFoodItemContainer(
                foodItem = result,
                addQuantity = { viewModel.onSearchResultDishAddBtnClicked(result.id, result.quantity, index) })
            { viewModel.onSearchResultDishMinusBtnClicked(result.id, index) }
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.search_food_item_container_space)))
        }
    }
}


@Composable
fun RecentSearch(
    recentSearches: List<RecentSearch>,
    search: (keyword: String) -> Unit,
    deleteKeyword: (keyword: String) -> Unit,
    updateIsInputOnFocus: (isFocused: Boolean) -> Unit,
) {
    Box {
        if (recentSearches.isNotEmpty()) {
            Column {
                Text(text = "Recent Search", style = Typography.titleSmall)
//                SectionTitleAndBtn(title = "Recent Search", btnTitle = "Clear all", icon = null) {}
                LazyColumn {
                    items(recentSearches) { keyword ->
                        SearchKeyword(
                            keyword = keyword,
                            search = search,
                            deleteKeyword = deleteKeyword,
                            updateIsInputOnFocus = updateIsInputOnFocus
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "No recent search")
            }
        }
    }
}

@Composable
fun SearchKeyword(
    keyword: RecentSearch,
    search: (keyword: String) -> Unit,
    deleteKeyword: (keyword: String) -> Unit,
    updateIsInputOnFocus: (isFocused: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxHeight()
                .clickable {
                    search(keyword.keyword)
                    updateIsInputOnFocus(false)
                },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = keyword.keyword,
                modifier = Modifier.fillMaxWidth()
            )
        }

        IconButton(onClick = { deleteKeyword(keyword.keyword) }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Icon",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInput(
    isInputOnFocus: Boolean?,
    isEnabled: Boolean,
    isClickable: Boolean,
    inputValue: String,
    placeholderText: String,
    onSearch: (KeyboardActionScope.() -> Unit)?,
    updateInput: (input: String) -> Unit,
    updateIsInputOnFocus: (isFocused: Boolean) -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(isInputOnFocus) {
        if (isInputOnFocus !== null && !isInputOnFocus) {
            keyboardController?.hide()
            focusManager.clearFocus(force = true)
        }
    }

    TextField(
        placeholder = { Text(text = placeholderText) },
        singleLine = true,
        enabled = isEnabled,
        value = inputValue,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, "Search Icon") },
        onValueChange = { updateInput(it) },
        keyboardActions = KeyboardActions(
            onSearch = {
                if (onSearch != null) {
                    onSearch()
                }
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .focusRequester(focusRequester)
            .clickable(onClick = { if (!isClickable) navigateToSearchScreen() })
            .onFocusChanged { updateIsInputOnFocus(it.isFocused) },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightGrey,
            unfocusedContainerColor = LightGrey,
            disabledContainerColor = LightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        )
    )
}
