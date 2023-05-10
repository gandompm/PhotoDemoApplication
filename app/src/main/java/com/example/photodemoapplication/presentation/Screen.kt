package com.example.photodemoapplication.presentation

sealed class Screen(
    val route: String
)
{
    object ImageListScreen : Screen("image_list_screen")
    object ImageDetailScreen: Screen("image_detail_screen")
}