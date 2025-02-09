package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Purple400
import com.smarttoolfactory.tutorial1_1basics.ui.Red400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader

@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen5() {
    TutorialContent()
}

@Preview(showBackground = true)
@Composable
private fun TutorialContent() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        TutorialHeader(text = "Внутренние Constraints (Inner Constraints)")

        StyleableTutorialText(
            text = "Когда Constraints (ограничения) для Composable меняются во время измерения, " +
                    "эти Constraints передаются дочерним элементам и могут переопределить те, " +
                    "которые приходят, например, из модификаторов размера. " +
                    "В этом примере Constraints для BoxWithConstraints переопределяются.",
            bullets = false
        )

        InnerConstraintsSample1()
        StyleableTutorialText(
            text = "В следующем примере есть ещё и Modifier.layout, чтобы показать, " +
                    "как работают пользовательский лейаут (Custom Layout), MeasureScope " +
                    "и Modifier.layout вместе.",
            bullets = false
        )
        InnerConstraintsSample2()
    }
}

@Composable
private fun InnerConstraintsSample1() {
    InnerCustomLayout(
        modifier = Modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Green400)
            .size(200.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                // 🔥🔥 Поскольку во внутреннем лейауте minWidth=120.dp и maxWidth=180.dp,
                // здесь размер в 100.dp пройдёт через coerceIn(120.dp, 180.dp) и в итоге
                // будет 120.dp
                .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                .background(Purple400)
                .size(100.dp)
        ) {
            Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
        }
    }
}

@Composable
private fun InnerConstraintsSample2() {

    /*
        Примерные логи:

        I  🍎 InnerCustomLayout MeasureScope
        I  constraints: minWidth: 200.0.dp, maxWidth: 200.0.dp
        I  wrappedConstraints minWidth: 120.0.dp, maxWidth: 180.0.dp
        I  contentWidth: 120.0.dp, layoutWidth: 200.0.dp
        I  🚗 Bottom layout() minWidth: 200.0.dp, maxWith: 200.0.dp
        I  🚙 Top layout() minWidth: 0.0.dp, maxWith: 280.0.dp
        I  🍏 OuterCustomLayout MeasureScope
        I  minWidth: 280.0.dp, maxWidth: 280.0.dp, contentWidth: 200.0.dp, layoutWidth: 280.0.dp
        I  🍏🍏 OuterCustomLayout Placement Scope
        I  🍎🍎 Placement Scope
     */
    OuterCustomLayout(
        modifier = Modifier
            .size(280.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Red400),
    ) {
        InnerCustomLayout(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                .background(Green400)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    println(
                        "🚙 Top layout() " +
                                "minWidth: ${constraints.minWidth.toDp()}, " +
                                "maxWith: ${constraints.maxWidth.toDp()}"
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
                .size(200.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    println(
                        "🚗 Bottom layout() " +
                                "minWidth: ${constraints.minWidth.toDp()}, " +
                                "maxWith: ${constraints.maxWidth.toDp()}"
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    // 🔥🔥 Поскольку во внутреннем лейауте minWidth=120.dp и maxWidth=180.dp,
                    // здесь размер в 100.dp пройдёт через coerceIn(120.dp, 180.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                    .background(Purple400)
                    .size(100.dp)
            ) {
                Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
            }
        }
    }
}

/**
 * Внешний лейаут (OuterCustomLayout),
 * измеряет детей, меняя Constraints или оставляя как есть, а затем размещает их.
 */
@Composable
private fun OuterCustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val measurePolicy = object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {


            val placeables = measurables.map { measurable ->
                // 🔥 Измеряем дочерние элементы с более "мягкими" ограничениями (minWidth=0)
                measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }

            val contentWidth = placeables.maxOf { it.width }
            val contentHeight = placeables.sumOf { it.height }
            val layoutWidth = if (constraints.hasBoundedWidth && constraints.hasFixedWidth) {
                constraints.maxWidth
            } else {
                contentWidth.coerceIn(constraints.minWidth, constraints.maxWidth)
            }
            val layoutHeight = if (constraints.hasBoundedHeight && constraints.hasFixedHeight) {
                constraints.maxHeight
            } else {
                contentHeight.coerceIn(constraints.minHeight, constraints.maxHeight)
            }

            println(
                "🍏 OuterCustomLayout MeasureScope\n" +
                        "minWidth: ${constraints.minWidth.toDp()}, " +
                        "maxWidth: ${constraints.maxWidth.toDp()}, " +
                        "contentWidth: ${contentWidth.toDp()}, " +
                        "layoutWidth: ${layoutWidth.toDp()}\n"
            )

            return layout(layoutWidth, layoutHeight) {
                var y = 0
                println("🍏🍏 OuterCustomLayout Placement Scope")

                placeables.forEach { placeable: Placeable ->
                    placeable.placeRelative(0, y)
                    y += placeable.height
                }
            }
        }
    }
    Layout(
        modifier = modifier,
        measurePolicy = measurePolicy,
        content = content
    )
}

/**
 * Внутренний лейаут (InnerCustomLayout),
 * который задаёт minWidth=120.dp, maxWidth=180.dp
 */
@Composable
private fun InnerCustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val measurePolicy = object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {

            val wrappedConstraints = constraints.copy(
                minWidth = 120.dp.roundToPx(),
                maxWidth = 180.dp.roundToPx(),
                minHeight = 120.dp.roundToPx(),
                maxHeight = 180.dp.roundToPx()
            )
            val placeables = measurables.map { measurable ->
                measurable.measure(wrappedConstraints)
            }

            val contentWidth = placeables.maxOf { it.width }
            val contentHeight = placeables.sumOf { it.height }
            val layoutWidth = if (constraints.hasBoundedWidth && constraints.hasFixedWidth) {
                constraints.maxWidth
            } else {
                contentWidth.coerceIn(constraints.minWidth, constraints.maxWidth)
            }
            val layoutHeight = if (constraints.hasBoundedHeight && constraints.hasFixedHeight) {
                constraints.maxHeight
            } else {
                contentHeight.coerceIn(constraints.minHeight, constraints.maxHeight)
            }

            println(
                "🍎 InnerCustomLayout MeasureScope\n" +
                        "constraints: minWidth: ${constraints.minWidth.toDp()}, " +
                        "maxWidth: ${constraints.maxWidth.toDp()}\n" +
                        "wrappedConstraints minWidth: ${wrappedConstraints.minWidth.toDp()}, " +
                        "maxWidth: ${wrappedConstraints.maxWidth.toDp()}\n" +
                        "contentWidth: ${contentWidth.toDp()}, " +
                        "layoutWidth: ${layoutWidth.toDp()}\n"
            )

            return layout(layoutWidth, layoutHeight) {
                var y = 0
                println("🍎🍎 Placement Scope")

                placeables.forEach { placeable: Placeable ->
                    placeable.placeRelative(0, y)
                    y += placeable.height
                }
            }
        }
    }
    Layout(
        modifier = modifier,
        measurePolicy = measurePolicy,
        content = content
    )
}
