package com.orlandev.composesoundsfx

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun SoundSfx(
    modifier: Modifier,
    size: Size = Size(100f, 200f),
    barCount: Int = 10,
) {

    //Animation - it will be repeated 2 times
    val animatedProgress = remember { Animatable(0.001f) }
    LaunchedEffect(animatedProgress) {
        animatedProgress.animateTo(
            1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        )
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val startingPoint = Offset(0f, size.height / 2f)
            val endingPoint = Offset(size.width * animatedProgress.value, size.height / 2f)

            //At the end of animation just remove the line
            if (animatedProgress.isRunning) {
               /* drawLine(
                    strokeWidth = 8.dp.toPx(),
                    color = Color.Black,
                    start = startingPoint,
                    end = endingPoint
                )*/

                for (i in 0 until barCount) {
                    drawRoundRect(
                        color = Color.Black,
                        topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                        size = Size(barWidth, endingPoint.x.toFloat()),
                        cornerRadius = CornerRadius(0f)
                    )
                }

            }

            ///---------


        }
    }

}
