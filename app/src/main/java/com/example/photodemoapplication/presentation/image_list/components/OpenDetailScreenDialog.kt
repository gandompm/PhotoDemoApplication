package com.example.photodemoapplication.presentation.image_list.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.photodemoapplication.presentation.destinations.Destination


@Composable
fun OpenDetailScreenDialog(
    onDismiss:()->Unit,
    onConfirm:()->Unit
) {
    MaterialTheme {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        onDismiss()
                    },
                    title = {
                        Text(text = "Image Details?")
                    },
                    text = {
                        Text("Do you want to open detail screen?")
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                onConfirm()
                            }) {
                            Text("Yes, open")
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                onDismiss()
                            }) {
                            Text("Cancel")
                        }
                    }
                )
            }
}