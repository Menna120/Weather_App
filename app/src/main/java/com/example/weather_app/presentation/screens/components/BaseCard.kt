package com.example.weather_app.presentation.screens.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BaseCard(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    shapes: CornerBasedShape = MaterialTheme.shapes.large,
    alpha: Float = .3f,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = backgroundColor.copy(alpha)),
        shape = shapes,
        modifier = modifier,
        content = content
    )
}