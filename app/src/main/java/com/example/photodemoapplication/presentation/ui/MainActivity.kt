package com.example.photodemoapplication.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photodemoapplication.R
import com.example.photodemoapplication.presentation.NavGraphs
import com.example.photodemoapplication.presentation.Screen
import com.example.photodemoapplication.presentation.image_list.components.ImageListScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            DestinationsNavHost(navGraph = NavGraphs.root)


        }



    }
}