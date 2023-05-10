package com.example.photodemoapplication.presentation.image_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.photodemoapplication.R
import com.example.photodemoapplication.presentation.destinations.ImageDetailsScreenDestination
import com.example.photodemoapplication.presentation.image_detail.ImageDetailsViewModel
import com.example.photodemoapplication.presentation.image_list.ImageListEvents
import com.example.photodemoapplication.presentation.image_list.ImageListViewModel
import com.example.photodemoapplication.presentation.image_list.components.ImageListItem
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination
fun ImageDetailsScreen (
    imageId: Int,
    viewModel: ImageDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    state.imageDetail?.let {

        Box(
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Column() {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(height = 400.dp, width = 0.dp),
                    model = it.largeImageURL,
                    loading = {
                        CircularProgressIndicator(modifier = Modifier
                            .progressSemantics()
                            .size(12.dp))                    },
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.weight(1f)){
                            Row{
                                Icon(painter = painterResource(id = R.drawable.baseline_favorite_border_24), contentDescription = "favorite")
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                Text(text = "${it.likes}")
                            }
                           }
                        Box(modifier = Modifier.weight(1f)){
                            Row{
                                Icon(painter = painterResource(id = R.drawable.baseline_comment_24), contentDescription = "comments")
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                Text(text = "${it.comments}")
                            }
                             }
                        Box(modifier = Modifier.weight(1f)){
                            Row{
                                Icon(painter = painterResource(id = R.drawable.baseline_download_24), contentDescription = "downloads")
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                Text(text = "${it.downloads}")
                            }
                           }

                }

                Text(
                    text = it.user,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.primaryVariant,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(10.dp))


                val tags: List<String> = it.tags.split(",").map { it.trim() }
                LazyRow() {
                    items(tags.size) { i ->
                        TagsListItem(tag = tags[i])
                    }
                }

            }
        }
    }


        if(state.error.isNotBlank()){
            Text(text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp))
        }
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier
                .progressSemantics()
                .size(12.dp))        }
}