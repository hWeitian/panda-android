package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.playlistList
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.InnerTopAppBar
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun PlaylistScreen() {
    InnerTopAppBar(title = "Food Playlist", backBtnClick = { Log.i("Panda", "Back Btn Clicked") }) {
        PlaylistSection(playlistList)
        PlaylistSection(playlistList)
        PlaylistSection(playlistList)
        PlaylistSection(playlistList)

        Column (modifier = Modifier.fillMaxWidth().padding(top = 25.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Can't decide?", style = Typography.titleMedium)
            Spacer(modifier = Modifier.size(10.dp))
            PrimaryButton(name = "Create your own", null) {
                Log.i("Panda", "Create btn clicked")
            }
        }

    }

}

@Composable
fun PlaylistSection(dataList: List<Playlist>) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 10.dp)
    ) {

        SectionTitleAndBtn(title = "Subscribed", btnTitle = "See all", icon = null) {
            Log.i("Panda", "See all btn clicked")
        }

        LazyRow(modifier = Modifier.wrapContentHeight()) {
            items(dataList) { data ->
                PlaylistCard(data)
            }
        }
    }
}


@Composable
fun PlaylistCard(playlist: Playlist) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 10.dp)
    ) {
        ImageHolder(playlist.imageUrl, 140, "Test")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = playlist.name, style = Typography.headlineLarge)
            Text(
                text = "S$ ${"%.2f".format(playlist.cost)}",
                style = Typography.headlineLarge,
                fontWeight = FontWeight.Normal
            )
        }
        Text(text = "Deliver every ${playlist.deliverDay}", style = Typography.bodyMedium, color = BrandSecondary)
    }
}

