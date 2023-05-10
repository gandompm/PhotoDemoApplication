package com.example.photodemoapplication.presentation.image_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.photodemoapplication.presentation.Screen
import com.example.photodemoapplication.presentation.destinations.ImageDetailsScreenDestination
import com.example.photodemoapplication.presentation.image_list.ImageListEvents
import com.example.photodemoapplication.presentation.image_list.ImageListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination(start = true)
fun ImageListScreen(
    navigator: DestinationsNavigator,
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Column(modifier = Modifier.fillMaxSize()){

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    ImageListEvents.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Results",
                modifier = Modifier.padding(15.dp)
            )
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(state.images){
                        image ->
                    ImageListItem(image = image,
                        onItemClick = {
                            viewModel.onImageItemClick(image.id)
                        }
                    )
                }
            }
        }

        if(state.error.isNotBlank()){
            Text(text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .align(CenterHorizontally))
        }
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier
                .progressSemantics()
                .size(12.dp))
        }
    }

    if(viewModel.isDialogShown){
        OpenDetailScreenDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                navigator.navigate(
                    ImageDetailsScreenDestination(
                        imageId = state.selectedImageId
                    )
                )
                viewModel.onDismissDialog()
            }
        )
    }
}