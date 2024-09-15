package com.ranga.todo.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ranga.todo.R
import com.ranga.todo.ui.theme.TodoTheme

@Composable
fun FloatingRoundButton(
    modifier: Modifier,
    iconImageVector: ImageVector = Icons.Filled.Add,
    onButtonClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = onButtonClick,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = dimensionResource(id = R.dimen.margin_small)),
            imageVector = iconImageVector,
            contentDescription = stringResource(id = R.string.add_todo)
        )
    }
}

@Preview
@Composable
private fun FloatingRoundButtonPreview() {
    TodoTheme {
        FloatingRoundButton(
            modifier = Modifier.size(dimensionResource(id = R.dimen.floating_button_size)),
            onButtonClick = {}
        )
    }
}