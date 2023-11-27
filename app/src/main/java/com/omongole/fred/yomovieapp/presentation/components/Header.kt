package com.omongole.fred.yomovieapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    onClick: (Boolean) -> Unit,
    themeMode: Boolean,
    infoIconClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp) ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ThemeSwitcher(
            size = 30.dp,
            padding = 5.dp,
            onClick = {
                onClick(it)
            },
            themeMode = themeMode
        )
        IconButton(onClick = {
            infoIconClick()
        }) {
            Icon(
                imageVector =  Icons.Default.Info,
                contentDescription = "Theme Icon",
                Modifier.size(28.dp)
            )
        }
    }
}