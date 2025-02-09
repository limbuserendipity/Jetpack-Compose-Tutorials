@file:OptIn(ExperimentalMotionApi::class)

package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.DebugFlags
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.smarttoolfactory.tutorial1_1basics.R

@Preview(showBackground = true)
@Composable
fun MotionLayoutSample1() {

    val buttonId = "Button"
    val textId = "Text"

    val constraintSet1 = remember {
        ConstraintSet {
            val button = createRefFor(buttonId)
            val text = createRefFor(textId)

            constrain(button) {
                top.linkTo(parent.top)
            }
            constrain(text) {
                top.linkTo(button.bottom)
            }
        }
    }

    val constraintSet2 = remember {
        ConstraintSet {
            val button = createRefFor(buttonId)
            val text = createRefFor(textId)

            constrain(button) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            constrain(text) {
                start.linkTo(button.end)
                top.linkTo(parent.top)
            }
        }
    }

    var show by remember {
        mutableStateOf(false)
    }

    val progress by animateFloatAsState(if (show) 1f else 0f, label = "")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        MotionLayout(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Yellow)
                .animateContentSize()
                .then(
                    if (show) Modifier.height(300.dp) else Modifier.height(150.dp)
                ),
            start = constraintSet1,
            end = constraintSet2,
            progress = progress,
            debugFlags = DebugFlags.All
        ) {

            Button(
                onClick = { /* Выполнить действие */ },
                modifier = Modifier.layoutId(buttonId)
            ) {
                Text("Кнопка")
            }

            Text("Текст", Modifier.layoutId(textId))
        }

        Button(
            onClick = {
                show = show.not()
            }
        ) {
            Text("Показать $show")
        }
    }
}

// ниже мы создаём метод кнопки для MotionLayout

@Preview(showBackground = true)
@Composable
private fun MotionLayoutButtonTest() {
    // ниже мы создаём контейнер Box
    Box(
        // в этом Box указываем модификатор
        // и задаём максимальный размер
        modifier = Modifier
            .fillMaxSize(),

        // ниже указываем выравнивание по центру
        contentAlignment = Alignment.Center,
    ) {
        // ниже вызываем метод MotionLayoutButton
        MotionLayoutButton()
    }
}

@Composable
fun MotionLayoutButton() {
    // ниже мы задаём метод для анимации кнопки
    var animateButton by remember { mutableStateOf(false) }
    // ниже указываем прогресс анимации кнопки
    val buttonAnimationProgress by animateFloatAsState(

        // указываем целевое значение ниже
        targetValue = if (animateButton) 1f else 0f,

        // ниже указываем длительность анимации 1 секунда
        animationSpec = tween(1000),
        label = ""
    )

    // ниже создаём MotionLayout
    MotionLayout(
        // в MotionLayout указываем два набора ограничений
        // для двух различных положений кнопок.
        // в первом наборе ограничений указываем ширину,
        // высоту, начало, конец и верхнюю позицию кнопок.
        ConstraintSet(
            """ {
                // ниже мы указываем ширину, высоту и отступ
                // от начала, верха и конца для button1
                button1: { 
                  width: "spread",
                  height: 120,
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['parent', 'top', 0]
                },
                // ниже мы указываем ширину, высоту
                // и отступ от начала, верха и конца для button2
                button2: { 
                  width: "spread",
                  height: 120,
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['button1', 'bottom', 16]
                }
            } """
        ),

        // во втором наборе ограничений указываем ширину,
        // высоту, начало, конец и верхнюю позицию кнопок.
        ConstraintSet(
            """ {
                // ниже мы указываем ширину, высоту и отступ
                // от начала, верха и конца для button1
                button1: { 
                  width: 150,
                  height: 120,
                  start: ['parent', 'start', 30],
                  end: ['button2', 'start', 10]
                },
                // ниже мы указываем ширину, высоту
                // и отступ от начала, верха и конца для button2
                button2: { 
                  width: 150,
                  height: 120,
                  start: ['button1', 'end', 10],
                  end: ['parent', 'end', 30]
                }
            } """
        ),
        // ниже указываем прогресс анимации кнопки
        progress = buttonAnimationProgress,
        // ниже указываем модификатор для
        // заполнения всей ширины и высоты по содержимому
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // ниже создаём первую кнопку
        Button(
            // ниже добавляем обработчик нажатия
            onClick = {
                // внутри onClick анимируем кнопку
                // изменяя переменную animateButton
                animateButton = !animateButton
            },
            // ниже указываем ID для кнопки 1
            modifier = Modifier.layoutId("button1")
        ) {
            // ниже добавляем содержимое кнопки в виде столбца
            Column(
                // в этом столбце указываем
                // модификатор с отступами со всех сторон
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                // ниже указываем вертикальное и горизонтальное
                // выравнивание для нашего столбца
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ниже добавляем изображение в столбец
                Image(
                    // указываем drawable для изображения
                    painter = painterResource(id = R.drawable.avatar_1_raster),

                    // указываем описание изображения
                    contentDescription = "Python",

                    // задаём ширину и высоту изображения
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                )
                // ниже добавляем разделитель
                Spacer(modifier = Modifier.height(5.dp))

                // ниже добавляем текст
                Text(
                    text = "Python",
                    color = Color.White,
                    fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
                )
            }
        }

        // ниже создаём вторую кнопку
        Button(
            onClick = {
                animateButton = !animateButton
            },
            // указываем ID для кнопки 2
            modifier = Modifier.layoutId("button2")
        ) {
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_2_raster),
                    contentDescription = "JavaScript",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "JavaScript",
                    color = Color.White,
                    fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
                )
            }
        }
    }
}

@Preview(group = "motion8", showBackground = true)
@Composable
fun AttributesRotationXY() {

    var animateToEnd by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(6000), label = ""
    )

    Column {
        MotionLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.White),
            motionScene = MotionScene(
                """{
                ConstraintSets: {
                  start: {
                    a: {
                      width: 40,
                      height: 40,
                      start: ['parent', 'start', 16],
                      bottom: ['parent', 'bottom', 16]
                    }
                  },
                  end: {
                    a: {
                      width: 40,
                      height: 40,
                      end: ['parent', 'end', 16],
                      top: ['parent', 'top', 16]
                    }
                  }
                },
                Transitions: {
                  default: {
                    from: 'start',
                    to: 'end',
                    pathMotionArc: 'startHorizontal',
                    KeyFrames: {
                      KeyAttributes: [
                        {
                          target: ['a'],
                          frames: [25,50,75],
                          rotationX: [0, 45, 60],
                          rotationY: [60, 45, 0],
                        }
                      ]
                    }
                  }
                }
            }"""
            ),
            debugFlags = DebugFlags.All,
            progress = progress
        ) {
            Box(
                modifier = Modifier
                    .layoutId("a")
                    .background(Color.Red)
            )
        }

        Button(onClick = { animateToEnd = !animateToEnd }) {
            Text(text = "Запустить")
        }
    }
}
