package com.cedarsstudio.internal.schoollogging.admins.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
    }
}