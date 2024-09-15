package com.ranga.todo.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ranga.todo.R

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    description: String = stringResource(id = R.string.error_message),
    onDismissLabel: String = stringResource(id = R.string.dismiss),
    onDismissEnabled: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(all = dimensionResource(id = R.dimen.margin_medium))
                .align(alignment = Alignment.CenterHorizontally),
            text = description,
            color = MaterialTheme.colorScheme.error
        )
        if (onDismissEnabled) {
            Button(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                onClick = onDismiss
            ) {
                Text(onDismissLabel)
            }
        }
    }
}

@Composable
@Preview
private fun ErrorPreview() {
    ErrorDialog {}
}