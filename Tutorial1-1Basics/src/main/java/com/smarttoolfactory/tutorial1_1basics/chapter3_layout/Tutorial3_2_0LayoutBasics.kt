package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.getRandomColor
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen0() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(modifier = Modifier.fillMaxSize()) {

        TutorialHeader(text = "Основы Layout")

        StyleableTutorialText(
            text = "Пользовательский лейаут создаётся при помощи **Layout**. " +
                    "Для него назначается **MeasurePolicy**, который определяет " +
                    "логику измерения (measure) и лейаута (layout) Layout-компонента.\n" +
                    "Именно с помощью Layout и MeasurePolicy " +
                    "построены базовые компоненты Compose (такие, как `Box`, `Column`, и т.д.), " +
                    "и также их можно использовать для создания своих собственных лейаутов.\n\n" +
                    "Во время фазы Layout дерево обрабатывается с помощью следующего алгоритма из 3 шагов:\n" +
                    "\n" +
                    "1) Измерить детей (Measure children): если у узла есть дочерние элементы, он их измеряет.\n" +
                    "2) Решить собственный размер (Decide own size): на основании результатов измерений узел определяет свой размер.\n" +
                    "3) Разместить детей (Place children): каждый дочерний узел размещается относительно позиции самого узла.",
            bullets = false
        )

        CustomLayoutSample1()

        StyleableTutorialText(
            text = "В этом примере переопределяются Constraints, с которыми измеряется контент. " +
                    "Composable, выходящий за границы min=150.dp, max=300.dp, " +
                    "измеряется в рамках min или max из этого диапазона.",
            bullets = false
        )
        CustomLayoutSample2()
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomLayoutSample1() {

    /*
        Вывод в лог (примерное содержание):
        🔥🔥 Depth-First Tree Traversal

        // COMPOSITION Phase
        I  Parent Scope

        I  Child1 Scope
        I  Box Scope

        I  Child2 Outer Scope
        I  Child2 Inner Scope

        // LAYOUT MeasureScope
        I  🍏 Child1 MeasureScope minWidth: 392.72726.dp, maxWidth: 392.72726.dp,
        minHeight: 50.18182.dp, maxHeight: 50.18182.dp
        I  contentHeight: 50.18182.dp, layoutHeight: 50.18182.dp

        I  🍏 Child2 Inner MeasureScope minWidth: 0.0.dp, maxWidth: 392.72726.dp,
        minHeight: 0.0.dp, maxHeight: 750.1818.dp
        I  contentHeight: 18.90909.dp, layoutHeight: 18.90909.dp
        I  🍏 Child2 Outer MeasureScope minWidth: 0.0.dp, maxWidth: 392.72726.dp,
        minHeight: 0.0.dp, maxHeight: 750.1818.dp
        I  contentHeight: 18.90909.dp, layoutHeight: 18.90909.dp

        I  🍏 Parent MeasureScope minWidth: 392.72726.dp, maxWidth: 392.72726.dp,
        minHeight: 0.0.dp, maxHeight: 750.1818.dp
        I  contentHeight: 69.09091.dp, layoutHeight: 69.09091.dp

        // LAYOUT PlacementScope
        I  🍏🍏 Parent PlacementScope
        I  🍏🍏 Child1 PlacementScope
        I  🍏🍏 Child2 Outer PlacementScope
        I  🍏🍏 Child2 Inner PlacementScope
     */

    // label используется для логгирования, не часть реального пользовательского лейаута
    MyLayout(
        modifier = Modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(getRandomColor())
            .fillMaxWidth(),
        label = "Parent"
    ) {
        println("Parent Scope")
        MyLayout(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(getRandomColor())
                .fillMaxWidth()
                .size(50.dp),
            label = "Child1"
        ) {
            println("Child1 Scope")

            // Этот Box измеряется с min=50.dp, max=50.dp
            // из-за родительской size(50.dp)
            Box(
                modifier = Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(getRandomColor())
                    .size(100.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                println("Box Scope")
                Text(text = "Box Content", color = Color.White)
            }
        }

        MyLayout(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(getRandomColor()),
            label = "Child2 Outer"
        ) {
            println("Child2 Outer Scope")

            MyLayout(
                modifier = Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(getRandomColor()),
                label = "Child2 Inner"
            ) {
                println("Child2 Inner Scope")
                Text("Child2 Bottom Content")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomLayoutSample2() {
    /*
        Вывод в лог (примерное содержание):
        I  CustomConstrainLayout Scope
        I  Top BoxWithConstraints Scope
        I  Middle BoxWithConstraints Scope
        I  Bottom BoxWithConstraints Scope
        I  🚗 CustomConstrainLayout MeasureScope minWidth: 392.72726.dp, maxWidth: 392.72726.dp,
        minHeight: 750.1818.dp, maxHeight: 750.1818.dp
        I  contentHeight: 73.09091.dp, layoutHeight: 750.1818.dp
        I  🚗🚗 CustomConstrainLayout PlacementScope
     */
    CustomConstrainLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        println("CustomConstrainLayout Scope")
        BoxWithConstraints(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(getRandomColor())
                .width(50.dp)
        ) {
            println("Top BoxWithConstraints Scope")
            Text(text = "min: $minWidth, max: $maxWidth")
        }
        BoxWithConstraints(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(getRandomColor())
                .width(250.dp)
        ) {
            println("Middle BoxWithConstraints Scope")
            Text(text = "min: $minWidth, max: $maxWidth")
        }

        BoxWithConstraints(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(getRandomColor())
                .width(350.dp)
        ) {
            println("Bottom BoxWithConstraints Scope")
            Text(text = "min: $minWidth, max: $maxWidth")
        }
    }
}

/**
 * Пример пользовательского лейаута.
 * Выводит вложенный контент, измеряя его с Constraints, но при этом
 * ставит minWidth=0, minHeight=0, чтобы контент мог занять свой "естественный" размер.
 *
 * label используется для целей логирования.
 */
@Composable
private fun MyLayout(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable () -> Unit
) {
    // Создаём пользовательский лейаут при помощи Layout
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        /*
            Во время фазы Layout дерево обрабатывается так:

            1) Измерение детей (Measure children): если у узла есть дочерние элементы, он их измеряет.
            2) Определение собственного размера (Decide own size): на основании измерений узел решает, какой у него будет размер.
            3) Размещение детей (Place children): каждый дочерний узел размещается относительно позиции самого узла.
         */

        // 1) Измеряем Measurables (вложенный контент) с учётом Constraints
        val placeables = measurables.map { measurable ->
            measurable.measure(
                // Изменяем минимальные Constraints на 0
                constraints.copy(minWidth = 0, minHeight = 0)
            )
        }

        // 2) Определяем размер нашего Layout
        // Допустим, мы хотим расположить контент в колонку, высота = сумма высот, ширина = макс. ширина
        val contentWidth = placeables.maxOf { it.width }
        val contentHeight = placeables.sumOf { it.height }

        // Если есть Constraints типа fillMaxSize, берём макс. значения
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
            "🍏 $label MeasureScope " +
                    "minWidth: ${constraints.minWidth.toDp()}, " +
                    "maxWidth: ${constraints.maxWidth.toDp()}, " +
                    "minHeight: ${constraints.minHeight.toDp()}, " +
                    "maxHeight: ${constraints.maxHeight.toDp()}\n" +
                    "contentHeight: ${contentHeight.toDp()}, " +
                    "layoutHeight: ${layoutHeight.toDp()}\n"
        )

        // 3) Layout-габариты должны попадать в Constraints
        layout(layoutWidth, layoutHeight) {

            // Размещаем дочерние Placeable (например, в виде колонки)
            var y = 0

            println("🍏🍏 $label PlacementScope")

            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, y)
                y += placeable.height
            }
        }
    }
}

/**
 * Пользовательский лейаут с частично переопределёнными Constraints:
 * minWidth=150.dp, maxWidth=300.dp,
 * контент, выходящий за эти границы, приводится к min/max.
 */
@Composable
private fun CustomConstrainLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(
                // Переопределяем Constraints для дочерних Composables
                constraints.copy(
                    minWidth = 150.dp.roundToPx(),
                    maxWidth = 300.dp.roundToPx(),
                    minHeight = 0
                )
            )
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
            "🚗 CustomConstrainLayout MeasureScope " +
                    "minWidth: ${constraints.minWidth.toDp()}, " +
                    "maxWidth: ${constraints.maxWidth.toDp()}, " +
                    "minHeight: ${constraints.minHeight.toDp()}, " +
                    "maxHeight: ${constraints.maxHeight.toDp()}\n" +
                    "contentHeight: ${contentHeight.toDp()}, " +
                    "layoutHeight: ${layoutHeight.toDp()}\n"
        )

        layout(layoutWidth, layoutHeight) {

            var y = 0

            println("🚗🚗 CustomConstrainLayout PlacementScope")

            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, y)
                y += placeable.height
            }
        }
    }
}
