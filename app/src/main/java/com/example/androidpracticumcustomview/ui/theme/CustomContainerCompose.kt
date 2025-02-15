package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    // Блок создания и инициализации переменных
    // ..
    val firstAlpha = remember { Animatable(0f) }
    val firstOffset = remember { Animatable(0f) }

    val secondAlpha = remember { Animatable(0f) }
    val secondOffset = remember { Animatable(0f) }

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        if (firstChild != null) {
            launch {
                firstAlpha.animateTo(1f, animationSpec = tween(durationMillis = 2000))
            }
            launch {
                firstOffset.animateTo(-250f, animationSpec = tween(durationMillis = 5000))
            }
        }

        if (secondChild != null) {
            launch {
                secondAlpha.animateTo(1f, animationSpec = tween(durationMillis = 2000))
            }
            launch {
                secondOffset.animateTo(250f, animationSpec = tween(durationMillis = 5000))
            }
        }
    }

    // Основной контейнер
    Box(modifier = Modifier.fillMaxSize()) {
        if (firstChild != null) {
            Box(modifier = Modifier.align(Alignment.Center)
                .offset(y = firstOffset.value.dp)
                .alpha(firstAlpha.value)
            ) {
                firstChild()
            }
        }

        if (secondChild != null) {
            Box(modifier = Modifier.align(Alignment.Center)
                .offset(y = secondOffset.value.dp)
                .alpha(secondAlpha.value)
            ) {
                secondChild()
            }
        }
    }
}