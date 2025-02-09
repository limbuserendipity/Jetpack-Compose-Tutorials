package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.BlueGrey400
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Orange400
import com.smarttoolfactory.tutorial1_1basics.ui.Pink400
import com.smarttoolfactory.tutorial1_1basics.ui.Purple400
import com.smarttoolfactory.tutorial1_1basics.ui.Red400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
@Preview(showBackground = true)
@Composable
fun Tutorial3_1Screen8() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)
    ) {

        TutorialHeader(text = "layout Modifier")

        StyleableTutorialText(
            text = "**Modifier.layout{}** создаёт LayoutModifier, позволяющий " +
                    "изменять процесс измерения (measure) и размещения (layout) вложенного элемента.",
            bullets = false
        )
        // В этом примере мы измеряем placeable с другим Modifier для размера,
        // чтобы повторить поведение Modifier.wrapContent
        LayoutModifierSample()

        StyleableTutorialText(
            text = "С помощью **Modifier.layout{}** можно увеличить размер контента больше, чем у родителя. " +
                    "Красный фон содержит три Box, у второго Box размер увеличен " +
                    "на 40.dp, а его позиция смещена влево на 20.dp",
            bullets = false
        )
        LayoutModifierSample2()

        StyleableTutorialText(
            text = "Порядок layout идёт снизу вверх, но Constraints передаются сверху вниз " +
                    "и либо игнорируются, либо приводятся к min или max существующих Constraints " +
                    "при выходе за их границы.",
            bullets = false
        )
        LayoutModifierOrderSample()
    }
}

@Preview(showBackground = true)
@Composable
private fun LayoutModifierSample() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Modifier.wrapContentSize
        Column(Modifier) {

            Text(
                text = "Modifier.wrapContentSize",
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(Pink400)
                    .size(140.dp)
                    .wrapContentSize()
                    .size(100.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Blue400)
                ) {
                    Text(
                        text = "minWidth: $minWidth, maxWidth: $maxWidth",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Modifier.layout
        Column {
            Text(
                text = "Modifier.layout",
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(Pink400)
                    .size(140.dp)
                    .layout { measurable, constraints ->
                        // Здесь наш контент: @Composable BoxScope.() -> Unit
                        // (BoxWithConstraints в этом примере)
                        val placeable = measurable.measure(
                            constraints.copy(minWidth = 0, minHeight = 0)
                        )

                        layout(constraints.maxWidth, constraints.maxHeight) {
                            val xPos = (constraints.maxWidth - placeable.width) / 2
                            val yPos = (constraints.maxHeight - placeable.height) / 2

                            // Размещаем по центру этого родительского Composable (Box)
                            placeable.placeRelative(xPos, yPos)
                        }
                    }
                    .size(100.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Blue400),
                ) {
                    Text(
                        text = "minWidth: $minWidth, maxWidth: $maxWidth",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LayoutModifierSample2() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Red400)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(BlueGrey400)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Увеличиваем размеры контента на 40.dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .border(2.dp, Color.Yellow)
                .layout { measurable: Measurable, constraints: Constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = constraints.maxWidth + 40.dp.roundToPx(),
                            maxWidth = constraints.maxWidth + 40.dp.roundToPx()
                        )
                    )

                    val layoutWidth =
                        placeable.width.coerceIn(constraints.maxWidth, constraints.maxWidth)
                    val layoutHeight =
                        placeable.height.coerceIn(constraints.minHeight, constraints.maxHeight)

                    layout(layoutWidth, layoutHeight) {
                        val xPos = (layoutWidth - placeable.width) / 2
                        placeable.placeRelative(xPos, 0)
                    }
                }
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Green400)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(BlueGrey400)
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LayoutModifierOrderSample() {
    // Также можно менять положение, чтобы увидеть, как это влияет на модификаторы
    // или Constraints после Modifier.layout

    /*
        Вывод в лог:
        I  🍎 Bottom Measurement phase  minWidth: 180.0.dp, maxWidth: 180.0.dp, placeable width: 180.0.dp
        I  🍏 Middle Measurement phase minWidth: 100.0.dp, maxWidth: 300.0.dp, placeable width: 180.0.dp
        I  🌻Top Measurement phase minWidth: 0.0.dp, maxWidth: 392.72726.dp, placeable width: 300.0.dp
        I  🌻🌻 Top Placement Phase
        I  🍏🍏 Middle Placement Phase
        I  🍎🍎 Bottom Placement Phase
     */
    BoxWithConstraints(
        modifier = Modifier
            .height(300.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Red400)
            // Constraints для этого layout приходят от родителя (ширина = 0..parentWidth, высота = 0..parentHeight)
            .layout { measurable, constraints ->

                val placeable = measurable.measure(constraints)
                println(
                    "🌻Top Measurement phase " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )

                layout(constraints.maxWidth, constraints.maxHeight) {
                    println("🌻🌻 Top Placement Phase")
                    placeable.placeRelative(50, 0)
                }
            }
            // 🔥 Это sizeIn-range передаётся нижнему Modifier.layout
            .widthIn(min = 100.dp, max = 300.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Green400)
            .layout { measurable, constraints ->

                // 🔥 Измеряем Measurable с этими Constraints
                // чтобы передать их следующему LayoutModifier
                val placeable = measurable.measure(
                    constraints
                        .copy(
                            minWidth = 180.dp.roundToPx(),
                            maxWidth = 250.dp.roundToPx(),
                            minHeight = 180.dp.roundToPx(),
                            maxHeight = 250.dp.roundToPx()
                        )
                )
                println(
                    "🍏 Middle Measurement phase " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )

                layout(constraints.maxWidth, constraints.maxHeight) {
                    println("🍏🍏 Middle Placement Phase")
                    placeable.placeRelative(0, 50)
                }
            }

//             Раскомментируйте эти модификаторы size, чтобы посмотреть,
//             как меняются Constraints
//            .width(100.dp)
//            .size(240.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Orange400)
            .layout { measurable, constraints ->

                val placeable = measurable.measure(constraints)
                println(
                    "🍎 Bottom Measurement phase  " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )
                layout(placeable.width, placeable.height) {
                    println("🍎🍎 Bottom Placement Phase")
                    placeable.placeRelative(150, 150)
                }
            }
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Purple400)
        // 🔥 Этот Modifier.width(...) тоже может сузить диапазон Constraints,
//            .width(50.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Min width: $minWidth\n" +
                    "maxWidth: $maxWidth",
            modifier = Modifier
                .border(2.dp, Color.Red)
                .padding(5.dp),
            color = Color.White
        )
    }
}
