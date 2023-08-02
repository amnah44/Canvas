package com.example.canvas

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.canvas.ui.theme.CanvasTheme
import com.example.canvas.ui.theme.PainterCanvas

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

//                    PainterCanvas()

                    SmoothLineGraph()
//                    Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
////                        InstagramIcon()
//
//                        FaceBookIcon()
                }


//                    val yStep = 100
//                    val points = listOf(150f, 160f, 250f, 170f, 200f, 250f, 300f, 310f, 320f, 331f)
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.DarkGray)
//                    ) {
//                        ChartWithPoints(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(500.dp),
//                            xValues = (0..9).map { it + 1 },
//                            yValues = (0..4).map { (it + 1) * yStep },
//                            points = points,
//                            paddingSpace = 16.dp,
//                            verticalStep = yStep
//                        )
//                    }


            }
        }
    }
}