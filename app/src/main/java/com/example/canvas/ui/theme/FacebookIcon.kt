package com.example.canvas.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun FaceBookIcon() {

    val backgroundColor = listOf(Color(0xFF2078EE), Color(0xFF74E6FE), Color(0xFF74E6FE))
    val sunColor = listOf(Color(0xFFFFC200), Color(0xFFFFE100))

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .padding(16.dp),
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width.times(.50f), height.times(.70f))
            cubicTo(
                width.times(.60f),
                height.times(.32f),
                width.times(.98f),
                height.times(.41f),
                width.times(.76f),
                height.times(.40f)
            )
            cubicTo(
                width.times(.75f),
                height.times(.21f),
                width.times(.35f),
                height.times(.21f),
                width.times(.38f),
                height.times(.50f)
            )
            cubicTo(
                width.times(.25f),
                height.times(.50f),
                width.times(.20f),
                height.times(.69f),
                width.times(.41f),
                height.times(.72f)
            )
            close()
        }

        drawPath(path = path, brush = Brush.verticalGradient(backgroundColor))
    }
}