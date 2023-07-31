package com.example.canvas.ui.theme

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PainterCanvas() {

    val backgroundColor = listOf(Color(0xFFD30848), Color(0xFF9C27B0), Color(0xFF74E6FE))


    var lastTouchX = remember { mutableStateOf(0f) }
    var lastTouchY = remember { mutableStateOf(0f) }
    var path = remember { mutableStateOf<Path?>(Path()) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .pointerInteropFilter { event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                path.value?.moveTo(event.x, event.y)

                                lastTouchX.value = event.x
                                lastTouchY.value = event.y
                            }

                            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                                val historySize = event.historySize

                                for (index in 0 until historySize) {
                                    val historicalX = event.getHistoricalX(index)
                                    val historicalY = event.getHistoricalY(index)

                                    path.value?.lineTo(historicalX, historicalY)
                                }

                                path.value?.lineTo(event.x, event.y)
                                lastTouchX.value = event.x
                                lastTouchY.value = event.y
                            }
                        }

                        lastTouchX.value = event.x
                        lastTouchY.value = event.y

                        val tempPath = path.value
                        path.value = null
                        path.value = tempPath


                        true
                    },
                onDraw = {
                    path.value?.let {
                        drawPath(
                            path = it,
                            brush = Brush.verticalGradient(backgroundColor),
                            style = Stroke(
                                width = 4.dp.toPx(),
                            )
                        )
                    }
                },
            )
        }

    }
}