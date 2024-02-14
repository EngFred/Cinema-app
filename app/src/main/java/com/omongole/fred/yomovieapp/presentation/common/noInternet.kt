package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun NoInternetComponent(
    modifier: Modifier,
    error: String,
    refresh: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(64.dp),
            imageVector = Icons.Rounded.Warning, contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = error,
            textAlign = TextAlign.Center,
        )
        OutlinedButton(
            onClick = {
                refresh()
            },
            content = {
                Text(text = "Retry")
            }
        )
    }
}