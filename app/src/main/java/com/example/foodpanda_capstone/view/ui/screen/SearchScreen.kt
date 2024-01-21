package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.RecentSearch
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.LightGrey
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel


@Composable
fun SearchScreen(navController: NavController, playlistViewModel: PlaylistViewModel) {

    val searchInput by playlistViewModel.searchText.observeAsState("")
    val recentSearch by playlistViewModel.recentSearch.collectAsState()
    
    LaunchedEffect(true) {
        playlistViewModel.getRecentSearch()
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Spacer(modifier = Modifier.size(10.dp))
            SearchInputButton(
                isEnabled = true,
                isClickable = true,
                placeholderText = "Search for food",
                inputValue = searchInput,
                onSearch = {playlistViewModel.search()},
                updateInput = {input -> playlistViewModel.updateSearchText(input)}
                ) {}
            Spacer(modifier = Modifier.size(20.dp))
            RecentSearch(recentSearch)
        }
    }
}


@Composable
fun RecentSearch(recentSearches: List<RecentSearch>) {
    Box {
        if(recentSearches.isNotEmpty()) {
            Column {
                SectionTitleAndBtn(title = "Recent Search", btnTitle = "Clear all", icon = null) {}
                LazyColumn {
                    items(recentSearches) { keyword ->
                        SearchKeyword(keyword = keyword)
                    }
                }
            }
        } else {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "No recent search")
            }
        }
    }
}

@Composable
fun SearchKeyword(keyword: RecentSearch){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = keyword.keyword)
        IconButton(onClick = { /*TODO Add delete this recent search function*/ }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Icon",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun SearchInputButton(
    isEnabled: Boolean,
    isClickable: Boolean,
    inputValue: String,
    placeholderText: String,
    onSearch: (KeyboardActionScope.()  -> Unit)?,
    updateInput: (input: String) -> Unit,
    navigateToSearchScreen: () -> Unit
)
{
    TextField(
        placeholder = { Text(text = placeholderText) },
        enabled = isEnabled,
        value = inputValue,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, "Search Icon") },
        onValueChange = { updateInput(it) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { if (!isClickable) navigateToSearchScreen() }),
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
        ),
        keyboardActions = KeyboardActions(
            onSearch = onSearch
        )
    )

}
