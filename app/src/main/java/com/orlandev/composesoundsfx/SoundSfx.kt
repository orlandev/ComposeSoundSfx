package com.orlandev.composesoundsfx

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color


@Composable
fun SoundSfx(
    modifier: Modifier,
    size: Size = Size(100f, 200f),
    barCount: Int = 10,
) {
    val listOffset = remember {
        mutableStateOf<List<Float>>(generateList(barCount))
    }
    //Animation - it will be repeated 2 times
    val animatedProgress = remember { Animatable(0.001f) }
    LaunchedEffect(animatedProgress) {

        animatedProgress.animateTo(
            1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
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
            listOffset.value = generateList(barCount)
            //At the end of animation just remove the line
            if (animatedProgress.isRunning) {
                for (i in 0 until barCount) {
                    drawRoundRect(
                        color = Color.Black,
                        topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                        size = Size(barWidth, size.width * listOffset.value[i]),
                        cornerRadius = CornerRadius(0f)
                    )
                    drawRoundRect(
                        color = Color.Black,
                        topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                        size = Size(barWidth, size.width * (-listOffset.value[i])),
                        cornerRadius = CornerRadius(0f)
                    )
                }
            }
        }
    }
}


//Function to generate random float list
fun generateList(size: Int): List<Float> {
    val list = mutableListOf<Float>()
    for (i in 0 until size) {
        list.add(Math.random().toFloat())
    }
    return list
}