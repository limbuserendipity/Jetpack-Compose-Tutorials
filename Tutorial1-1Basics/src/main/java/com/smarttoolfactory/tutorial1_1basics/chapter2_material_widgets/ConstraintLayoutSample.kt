package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId

@Preview(showBackground = true)
@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (minWidth < 600.dp) {
            decoupledConstraints(margin = 16.dp) // Ограничения для портретной ориентации
        } else {
            decoupledConstraints(margin = 32.dp) // Ограничения для ландшафтной ориентации
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Выполнить действие */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConstraintLayoutGuidlineSample() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, Color.Red)
    ) {
        // Создаём направляющую от начала родителя на 40% ширины
        val startGuideline = createGuidelineFromStart(0.4f)
        // Создаём направляющую от конца родителя на 10% ширины
        val endGuideline = createGuidelineFromEnd(0.1f)
        // Создаём направляющую от верхней части родителя на 16 dp
        val topGuideline = createGuidelineFromTop(16.dp)
        // Создаём направляющую от нижней части родителя на 16 dp
        val bottomGuideline = createGuidelineFromBottom(16.dp)

        val button = createRef()
        val text = createRef()

        Button(
            onClick = { /* Выполнить действие */ },
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(startGuideline)
                }
        ) {
            Text("Button")
        }

        Text(
            text = "Text",
            modifier = Modifier
                .background(Color.Yellow)
                .constrainAs(text) {
                    start.linkTo(button.end, 10.dp)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutDemo() {
    ConstraintLayout(modifier = Modifier.size(200.dp)) {

        val (redBox, blueBox, yellowBox, text) = createRefs()

        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Red)
            .constrainAs(redBox) {})

        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Blue)
            .constrainAs(blueBox) {
                top.linkTo(redBox.bottom)
                start.linkTo(redBox.end)
            })

        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Yellow)
            .constrainAs(yellowBox) {
                bottom.linkTo(blueBox.bottom)
                start.linkTo(blueBox.end, 20.dp)
            }
        )

        Text("Hello World", modifier = Modifier
            .constrainAs(text) {
                top.linkTo(parent.top)
                start.linkTo(yellowBox.start)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun ConstraintLayoutAnimationTest() {

    /*
        height = Dimension.value(10.dp) // Высота фиксирована на 10 dp
        width = Dimension.ratio("4:1") // Ширина будет в 4 раза больше высоты
        width = Dimension.wrapContent // Ширина подстраивается под содержимое
        height = Dimension.ratio("1:0.25") // Высота составляет четверть ширины
     */

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
                // Изменяем ширину
                width = Dimension.value(100.dp)
                start.linkTo(button.end)
                top.linkTo(parent.top)
            }
        }
    }

    var show by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .animateContentSize()
                .then(
                    if (show) Modifier.height(300.dp) else Modifier.height(150.dp)
                ),
            constraintSet = if (show) constraintSet2 else constraintSet1,
            animateChanges = true
        ) {

            Button(
                onClick = { /* Выполнить действие */ },
                modifier = Modifier.layoutId(buttonId)
            ) {
                Text("Button")
            }

            Text(
                text = "Text", Modifier.layoutId(textId).background(Color.Red)
            )
        }

        Button(
            onClick = {
                show = show.not()
            }
        ) {
            Text("Show $show")
        }
    }
}
