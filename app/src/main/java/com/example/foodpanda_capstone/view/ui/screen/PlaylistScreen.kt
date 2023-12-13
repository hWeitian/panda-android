package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.playlistList
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun PlaylistScreen() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp)) {

        SectionTitleAndBtn(title = "Subscribed", btnTitle = "See all", icon = null) {
            Log.i("Panda", "See all btn clicked")
        }

        LazyRow(modifier = Modifier.wrapContentHeight()) {
            items(playlistList) { playlist ->
                PlaylistCard(playlist)
            }
        }
    }
}


@Composable
fun PlaylistCard(playlist: Playlist) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(end = 10.dp)
    ) {
        ImageHolder(playlist.imageUrl, 180,"Test")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = playlist.name, style = Typography.titleSmall)
            Text(text = "S$ ${"%.2f".format(playlist.cost)}", style = Typography.titleSmall, fontWeight = FontWeight.Normal)
        }
        Text(text = "Deliver every ${playlist.deliverDay}", style = Typography.bodyMedium, color = BrandSecondary)
    }
}

