package com.example.photodemoapplication.presentation.image_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TagsListItem(
    tag: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(70.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = tag,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
    Spacer(modifier = Modifier.width(5.dp))
}