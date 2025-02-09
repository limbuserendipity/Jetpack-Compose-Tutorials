@file:OptIn(ExperimentalMotionApi::class)

package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.OnSwipe
import androidx.constraintlayout.compose.SwipeDirection
import androidx.constraintlayout.compose.SwipeMode
import androidx.constraintlayout.compose.SwipeSide
import androidx.constraintlayout.compose.SwipeTouchUp

@Preview(showBackground = true)
@Composable
private fun Test() {
    MotionLayout(
        MotionScene { // this: MotionSceneScope
            val textRef = createRefFor("text") // Создаём ссылку для элемента "text"
            defaultTransition(
                from = constraintSet { // this: ConstraintSetScope
                    constrain(textRef) { // this: ConstrainScope
                        bottom.linkTo(parent.bottom) // Нижняя граница связана с нижней границей родителя
                        start.linkTo(parent.start) // Левая граница связана с левой границей родителя
                    }
                },
                to = constraintSet { // this: ConstraintSetScope
                    constrain(textRef) { // this: ConstrainScope
                        top.linkTo(parent.top) // Верхняя граница связана с верхней границей родителя
                        end.linkTo(parent.end) // Правая граница связана с правой границей родителя
                    }
                }
            ) { // this: TransitionScope
                onSwipe = OnSwipe(
                    anchor = textRef, // Якорь для свайпа
                    side = SwipeSide.End, // Сторона, с которой начинается свайп
                    direction = SwipeDirection.End, // Направление свайпа
                    mode = SwipeMode.Spring, // Режим анимации
                    onTouchUp = SwipeTouchUp.AutoComplete // Автоматическое завершение свайпа
                )
            }
        },
        progress = 0f,
        Modifier.fillMaxSize()
    ) {
        Text("Привет, Мир", Modifier.layoutId("text"))
    }
}