package com.example.canvas

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun SmoothLineGraph() {
    Box(
        modifier = Modifier
            .background(PurpleBackgroundColor)
            .fillMaxSize()
    ) {
        val animationProgress = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = graphData, block = {
            animationProgress.animateTo(1f, tween(8000))
        })

        val coroutineScope = rememberCoroutineScope()
        Spacer(
            modifier = Modifier
                .padding(16.dp)
                .aspectRatio(2 / 2f)
                .fillMaxSize()
                .align(Alignment.Center)
                .clickable {
                    coroutineScope.launch {
                        animationProgress.snapTo(0f)
                        animationProgress.animateTo(1f, tween(8000))
                    }
                }
                .drawWithCache {
                    /**
                     * to draw chart line
                     */
                    val path = generateSmoothPath(graphData, size)
                    val filledPath = Path()
                    filledPath.addPath(path)
                    filledPath.relativeLineTo(0f, size.height)
                    filledPath.lineTo(0f, size.height)
                    filledPath.close()

                    onDrawBehind {
                        /**
                         * to draw horizontal line inside box of chart
                         */
                        val barWidthPx = 1.dp.toPx()
                        drawRect(Color.Transparent, style = Stroke(barWidthPx))

                        val horizontalLines = 5 // count of line inside  box
                        val sectionSize = size.height / (horizontalLines + 1)
                        repeat(horizontalLines) { i ->
                            val startY = sectionSize * (i + 1)
                            drawLine(
                                Color.Red,
                                start = Offset(0f, startY),
                                end = Offset(size.width, startY),
                                strokeWidth = barWidthPx
                            )
                        }

                        // draw line
                        clipRect(right = size.width * animationProgress.value) {
                            drawPath(path, Color.Green, style = Stroke(2.dp.toPx()))

                            drawPath(
                                filledPath,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Green.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                style = Fill
                            )
                        }
                    }
                })
    }
}

fun generatePath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount } // will map to x= 0, y = height
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() *
                        heightPxPerAmount
            )
        }
        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() *
                heightPxPerAmount
        path.lineTo(balanceX, balanceY)
    }
    return path
}

fun generateSmoothPath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount } // will map to x= 0, y = height
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() *
                        heightPxPerAmount
            )

        }

        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() *
                heightPxPerAmount
        // to do smooth curve graph - we use cubicTo, uncomment section below for non-curve
        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
        path.cubicTo(
            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX, balanceY
        )

        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}

// date + balance
// list of date + balanc
@RequiresApi(Build.VERSION_CODES.O)
val graphData = listOf(
    Balance(LocalDate.now(), BigDecimal(65250)),
    Balance(LocalDate.now().plusMonths(1), BigDecimal(65931)),
    Balance(LocalDate.now().plusMonths(2), BigDecimal(65940)),
    Balance(LocalDate.now().plusMonths(3), BigDecimal(65950)),
    Balance(LocalDate.now().plusMonths(4), BigDecimal(66460)),
    Balance(LocalDate.now().plusMonths(5), BigDecimal(67670)),
)

data class Balance(val date: LocalDate, val amount: BigDecimal)

val PurpleBackgroundColor = Color(0xff322049)
val BarColor = Color.White.copy(alpha = 0.3f)


@Preview
@Composable
fun CoordinateSystem(){
    Box(modifier = Modifier.fillMaxSize().drawBehind {
        val barWidthPx = 1.dp.toPx()

        drawRect(Color.White)
        val verticalLines = size.width / 80.dp.toPx()
        val verticalSize = size.width / (verticalLines + 1)
        repeat(verticalLines.roundToInt()) { i ->
            val startX = verticalSize * (i + 1)
            drawLine(
                Color.Gray,
                start = Offset(startX, 0f),
                end = Offset(startX, size.height),
                strokeWidth = barWidthPx
            )
        }

        val horizontalLines = size.height / 80.dp.toPx()
        val sectionSize = size.height / (horizontalLines + 1)
        repeat(horizontalLines.roundToInt()) { i ->
            val startY = sectionSize * (i + 1)
            drawLine(
                BarColor,
                start = Offset(0f, startY),
                end = Offset(size.width, startY),
                strokeWidth = barWidthPx
            )
        }

    })
}