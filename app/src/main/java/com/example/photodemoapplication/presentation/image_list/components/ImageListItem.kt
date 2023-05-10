package com.example.photodemoapplication.presentation.image_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.presentation.image_detail.components.TagsListItem

@Composable
fun ImageListItem(
    image: Image,
    onItemClick: (Image) -> Unit
){
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(image) }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(height = 120.dp, width = 0.dp),
                model = image.previewURL,
                loading = {
                    CircularProgressIndicator(Modifier.size(15.dp))
                },
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = image.user,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(10.dp))


            val tags: List<String> = image.tags.split(",").map { it.trim() }
            LazyRow() {
                items(tags.size) { i ->
                    TagsListItem(tag = tags[i])
                }
            }
        }

    }
}