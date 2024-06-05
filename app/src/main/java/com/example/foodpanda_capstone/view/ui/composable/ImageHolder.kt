package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodpanda_capstone.model.api.BaseUrl

@Composable
fun ImageHolder(imageUrl: String, height: Int?, description: String?) {

    val firstCharOfImgUrl = imageUrl.first()
    val finalImageUrl = if (firstCharOfImgUrl != 'h') BaseUrl.BASE_URL + imageUrl else imageUrl

    var modifier = if (height != null) Modifier.fillMaxWidth().height(height.dp).aspectRatio(1f, true)
    else Modifier.fillMaxWidth().aspectRatio(1f, true)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        AsyncImage(
            model = finalImageUrl,
            contentDescription = description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }

}
