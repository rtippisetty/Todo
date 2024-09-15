package com.ranga.todo.ui.common

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.ranga.todo.R

@Composable
fun Divider(modifier: Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = dimensionResource(id = R.dimen.margin_medium),
        color = Color.Transparent
    )
}