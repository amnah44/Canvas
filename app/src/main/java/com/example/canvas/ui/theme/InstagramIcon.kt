package com.example.canvas.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun InstagramIcon() {

    val instagramColors = listOf(Color.Red, Color.Black, Color.Green)


    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = instagramColors),
            cornerRadius = CornerRadius(50f, 50f),
            style = Stroke(width = 20f, cap = StrokeCap.Round)
        )

        drawCircle(
            brush = Brush.linearGradient(colors = instagramColors),
            radius = 45f,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawCircle(
            brush = Brush.linearGradient(colors = listOf(Color.Green, Color.Black)),
            radius = 13f,
            center = Offset(this.size.width * .20f, this.size.height * 0.20f)
        )

        drawCircle(
            brush = Brush.linearGradient(colors = instagramColors),
            radius = 13f,
            center = Offset(this.size.width * .50f, this.size.height * 0.50f)
        )

        drawCircle(
            brush = Brush.linearGradient(colors = instagramColors),
            radius = 13f,
            center = Offset(this.size.width * .85f, this.size.height * 0.50f)
        )
    }
}