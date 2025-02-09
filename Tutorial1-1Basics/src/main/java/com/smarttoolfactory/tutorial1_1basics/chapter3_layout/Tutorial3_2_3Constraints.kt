package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Pink400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen3() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            // 🔥🔥 Важно: при использовании вертикального скролла hasBoundedHeight
            // будет возвращать Constraints.Infinity (неограниченную высоту).
            .verticalScroll(rememberScrollState())
    ) {

        TutorialHeader(text = "Constraints")

        StyleableTutorialText(
            text = "Constraints (ограничения) определяют, как дочерние Composable будут измерены. " +
                    "По умолчанию Constraints учитывают minWidth, maxWidth, minHeight, maxHeight " +
                    "лейаута на основе переданных Modifier.\n" +
                    "В примерах ниже мы измеряем дочерние Composable с Constraints с разными " +
                    "значениями minWidth и maxWidth, чтобы показать, как они влияют на измерение.\n" +
                    "Зелёная рамка (border) оборачивает родителя, " +
                    "а ширина лейаута задаётся как **Constraints.maxWidth**. Вы можете изменить " +
                    "ширину лейаута, чтобы посмотреть, как измерение родителя меняется " +
                    "в зависимости от ширины.",
            bullets = false
        )

        /*
            Логика измерения, когда мы измеряем measurable, возвращает Constraints
            с min, max границами. Если мы измеряем что-то с этими границами,
            оно размещается в этом интервале.
         */

        DefaultConstraintsSample()
        CustomConstraintsSample()
        CustomConstraintsSample2()
        CustomConstraintsSample3()
        CustomConstraintsSample4()
    }
}

@Composable
private fun Content() {
    Text(
        "Первый текст",
        modifier = Modifier
            .background(Pink400),
        color = Color.White
    )
    Text(
        "Второй текст",
        modifier = Modifier
            .background(Blue400),
        color = Color.White
    )
}

@Composable
private fun DefaultConstraintsSample() {
    StyleableTutorialText(text = "1-) 🍉 Создаём CustomColumn с Default Constraints.")

    TutorialText2(text = "Modifier.fillMaxWidth()")
    // 🔥🔥🔥 В отличие от стандартной Column, здесь у каждой дочерней View
    // ширина будет maxWidth, когда установлено fillMaxWidth(), потому что
    // minWidth = maxWidth. Это наглядно демонстрирует, как Constraints влияют
    // на размеры родителя и детей.

    CustomColumnWithDefaultConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.width(200.dp)")
    // Аналогичный пример, когда явное значение ширины 200.dp.

    CustomColumnWithDefaultConstraints(
        modifier = Modifier
            .width(200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(min=200.dp)")
    CustomColumnWithDefaultConstraints(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(max= 200.dp)")
    CustomColumnWithDefaultConstraints(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.wrapContentSize()")
    CustomColumnWithDefaultConstraints(
        modifier = Modifier
            .wrapContentSize()
            .border(2.dp, Green400)
    ) { Content() }
}

@Composable
private fun CustomConstraintsSample() {
    StyleableTutorialText(
        text = "2-) 🎃 Создаём CustomColumn, где Constraints приводятся к " +
                "**minWidth = constraints.maxWidth** и **maxWidth = constraints.maxWidth**." +
                "\nЭто заставляет дочерние элементы измеряться строго на всю " +
                "максимальную ширину Constraints."
    )

    TutorialText2(text = "Modifier.fillMaxWidth()")

    CustomColumnWithCustomConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.width(200.dp)")
    CustomColumnWithCustomConstraints(
        modifier = Modifier
            .width(200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(min=200.dp)")
    CustomColumnWithCustomConstraints(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(max= 200.dp)")
    CustomColumnWithCustomConstraints(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.wrapContentSize()")
    CustomColumnWithCustomConstraints(
        modifier = Modifier
            .wrapContentSize()
            .border(2.dp, Green400)
    ) { Content() }
}

@Composable
private fun CustomConstraintsSample2() {
    StyleableTutorialText(
        text = "3-) 🍋 Создаём CustomColumn, в котором Constraints " +
                "заставляют ширину быть равной 250.dp (через **Constraints.fixedWidth(250)**)."
    )

    TutorialText2(text = "Modifier.fillMaxWidth()")
    CustomColumnWithCustomConstraints2(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.width(200.dp)")
    CustomColumnWithCustomConstraints2(
        modifier = Modifier
            .width(200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(min= 200.dp)")
    CustomColumnWithCustomConstraints2(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(max= 200.dp)")
    CustomColumnWithCustomConstraints2(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.wrapContentSize()")
    CustomColumnWithCustomConstraints2(
        modifier = Modifier
            .wrapContentSize()
            .border(2.dp, Green400)
    ) { Content() }
}

@Composable
private fun CustomConstraintsSample3() {
    StyleableTutorialText(
        text = "4-) 🍏 Создаём CustomColumn, где **minWidth = 0**."
    )

    TutorialText2(text = "Modifier.fillMaxWidth()")
    CustomColumnWithCustomConstraints3(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.width(200.dp)")
    CustomColumnWithCustomConstraints3(
        modifier = Modifier
            .width(200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(min= 200.dp)")
    CustomColumnWithCustomConstraints3(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(max= 200.dp)")
    CustomColumnWithCustomConstraints3(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.wrapContentSize()")
    CustomColumnWithCustomConstraints3(
        modifier = Modifier
            .wrapContentSize()
            .border(2.dp, Green400)
    ) { Content() }
}

@Composable
private fun CustomConstraintsSample4() {
    StyleableTutorialText(
        text = "5-) 🌽 Создаём CustomColumn, в котором **Constraints**: " +
                "** minWidth = 150.dp**, **maxWidth=250.dp**. " +
                "Дочерние элементы будут иметь ширину не меньше 150.dp, но не превышающую 250.dp."
    )

    TutorialText2(text = "Modifier.fillMaxWidth()")
    CustomColumnWithCustomConstraints4(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.width(200.dp)")
    CustomColumnWithCustomConstraints4(
        modifier = Modifier
            .width(200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(min= 200.dp)")
    CustomColumnWithCustomConstraints4(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.widthIn(max= 200.dp)")
    CustomColumnWithCustomConstraints4(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .border(2.dp, Green400)
    ) { Content() }

    TutorialText2(text = "Modifier.wrapContentSize()")
    CustomColumnWithCustomConstraints4(
        modifier = Modifier
            .wrapContentSize()
            .border(2.dp, Green400)
    ) { Content() }
}

@Composable
private fun CustomColumnWithDefaultConstraints(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->
        println("🍉 CustomColumnWithDefaultConstraints() constraints: $constraints")
        createCustomColumnLayout(measurables, constraints, constraints)
    }
}

/**
 * Измерение с minWidth = constraints.maxWidth, и maxWidth = constraints.maxWidth
 */
@Composable
private fun CustomColumnWithCustomConstraints(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints =
            constraints.copy(
                minWidth = constraints.maxWidth,
                maxWidth = constraints.maxWidth
            )

        println(
            "🎃 CustomColumnWithCustomConstraints()\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints"
        )

        createCustomColumnLayout(measurables, constraints, updatedConstraints)
    }
}

/**
 * Измерение через Constraints.fixedWidth(250.dp)
 */
@Composable
private fun CustomColumnWithCustomConstraints2(
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    val density = LocalDensity.current
    val widthInPx = with(density) {
        250.dp.roundToPx()
    }
    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints = Constraints.fixedWidth(widthInPx)

        println(
            "🍋 CustomColumnWithCustomConstraints2()\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints"
        )
        createCustomColumnLayout(measurables, constraints, updatedConstraints)
    }
}

/**
 * Измерение с minWidth = 0
 */
@Composable
private fun CustomColumnWithCustomConstraints3(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints = constraints.copy(minWidth = 0)

        println(
            "🍏 CustomColumnWithCustomConstraints3()\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints"
        )
        createCustomColumnLayout(measurables, constraints, updatedConstraints)
    }
}

/**
 * Измерение с minWidth = 150.dp и maxWidth = 250.dp
 */
@Composable
private fun CustomColumnWithCustomConstraints4(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    val minWidthPx = with(density) {
        150.dp.roundToPx()
    }

    val maxWidthPx = with(density) {
        250.dp.roundToPx()
    }

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints =
            constraints.copy(
                minWidth = minWidthPx,
                maxWidth = maxWidthPx
            )

        println(
            "🌽 CustomColumnWithCustomConstraints4()\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints"
        )
        createCustomColumnLayout(measurables, constraints, updatedConstraints)
    }
}

/**
 * Вспомогательная функция для формирования лейаута типа Column,
 * чтобы не дублировать код в каждом примере с разными Constraints.
 */
private fun MeasureScope.createCustomColumnLayout(
    measurables: List<Measurable>,
    constraints: Constraints,
    updatedConstraints: Constraints
): MeasureResult {
    val placeables = measurables.map { measurable ->
        // Измеряем каждый дочерний элемент с учётом updatedConstraints
        measurable.measure(updatedConstraints)
    }

    // Положение по оси Y, до которого мы размещали элементы
    var yPosition = 0

    val totalHeight: Int = placeables.sumOf { it.height }

    // 🔥 Можно взять макс. ширину дочерних элементов или maxWidth из Constraints
//    val contentWidth: Int = placeables.maxOf { it.width }
//    val contentWidth: Int = placeables.sumOf { it.width }
    // 🔥 В данном примере берём width = constraints.maxWidth
    val contentWidth = constraints.maxWidth

    println("🔥 CustomColumn Constraints ACTUAL WIDTH $contentWidth\n")
    println(
        "⚠️ ORIGINAL CONSTRAINTS: " +
                "minWidth ${constraints.minWidth}, " +
                "maxWidth: ${constraints.maxWidth}, " +
                "boundedWidth: ${constraints.hasBoundedWidth}, " +
                "fixedWidth: ${constraints.hasFixedWidth}\n" +
                "minHeight: ${constraints.minHeight}, " +
                "maxHeight: ${constraints.maxHeight}, " +
                "hasBoundedHeight: ${constraints.hasBoundedHeight}, " +
                "hasFixedHeight: ${constraints.hasFixedHeight}\n"
    )
    println(
        "⚠️⚠️️️ Updated CONSTRAINTS: " +
                "minWidth ${updatedConstraints.minWidth}, " +
                "maxWidth: ${updatedConstraints.maxWidth}, " +
                "boundedWidth: ${updatedConstraints.hasBoundedWidth}, " +
                "fixedWidth: ${updatedConstraints.hasFixedWidth}\n" +
                "minHeight: ${updatedConstraints.minHeight}, " +
                "maxHeight: ${updatedConstraints.maxHeight}, " +
                "hasBoundedHeight: ${updatedConstraints.hasBoundedHeight}, " +
                "hasFixedHeight: ${updatedConstraints.hasFixedHeight}\n"
    )

    // Устанавливаем размер лейаута (ширину) в contentWidth, а высоту — сумма высот children
    return layout(contentWidth, totalHeight) {
        // Размещаем дочерние элементы вертикально (как в Column)
        placeables.forEach { placeable ->
            placeable.placeRelative(x = 0, y = yPosition)
            yPosition += placeable.height
        }
    }
}
