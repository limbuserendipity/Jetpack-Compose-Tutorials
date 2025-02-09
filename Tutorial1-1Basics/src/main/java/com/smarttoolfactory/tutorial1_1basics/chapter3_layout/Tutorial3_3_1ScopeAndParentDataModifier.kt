package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

@Preview(showBackground = true)
@Composable
fun Tutorial3_3Screen1() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TutorialHeader(text = "Scope и ParentDataModifier")
        StyleableTutorialText(
            text = "1) Использование собственного scope для Composable даёт возможность " +
                    "добавлять в нём Modifier, доступный только в этом scope. Например, " +
                    "**Modifier.horizontalAlign** доступен только внутри **CustomColumnScope**."
        )

        TutorialText2(text = "Пользовательская Column со Scope")

        CustomColumnWithScope(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {

            Text(
                "Align Start",
                modifier = Modifier
                    .background(Color(0xffF44336))
                    .horizontalAlign(HorizontalAlignment.Start),
                color = Color.White
            )
            Text(
                "Align Center",
                modifier = Modifier
                    .background(Color(0xff9C27B0))
                    .horizontalAlign(HorizontalAlignment.Center),
                color = Color.White
            )
            Text(
                "Align End",
                modifier = Modifier
                    .background(Color(0xff2196F3))
                    .horizontalAlign(HorizontalAlignment.End),
                color = Color.White
            )
            Text(
                "Align Start",
                modifier = Modifier
                    .background(Color(0xff8BC34A))
                    .horizontalAlign(HorizontalAlignment.Start),
                color = Color.White
            )
        }

        CustomColumnWithScope(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.LightGray)
        ) {

            Text(
                "Align Start",
                modifier = Modifier
                    .background(Color(0xffF44336))
                    .horizontalAlign(HorizontalAlignment.Start),
                color = Color.White
            )
            Text(
                "Align Center",
                modifier = Modifier
                    .background(Color(0xff9C27B0))
                    .horizontalAlign(HorizontalAlignment.Center),
                color = Color.White
            )
            Text(
                "Align End",
                modifier = Modifier
                    .background(Color(0xff2196F3))
                    .horizontalAlign(HorizontalAlignment.End),
                color = Color.White
            )
            Text(
                "Align Start",
                modifier = Modifier
                    .background(Color(0xff8BC34A))
                    .horizontalAlign(HorizontalAlignment.Start),
                color = Color.White
            )
        }

        TutorialText2(text = "Пользовательская Row со Scope")

        CustomRowWithScope(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.DarkGray)
        ) {
            Text(
                "Align Top",
                modifier = Modifier
                    .background(Color(0xffF44336))
                    .verticalAlign(VerticalAlignment.Top),
                color = Color.White
            )
            Text(
                "Align Center",
                modifier = Modifier
                    .background(Color(0xff9C27B0))
                    .verticalAlign(VerticalAlignment.Center),
                color = Color.White
            )
            Text(
                "Align Bottom",
                modifier = Modifier
                    .background(Color(0xff2196F3))
                    .verticalAlign(VerticalAlignment.Bottom),
                color = Color.White
            )
        }
    }
}

// ------------------------------------------------------------------------------------------

/*
    ***** Пользовательская Column *****
 */

/*
1) Создаём enum для задания горизонтального выравнивания
 */
enum class HorizontalAlignment {
    Start, Center, End
}

/*
2) Создаём класс, реализующий ParentDataModifier
 */
private class CustomColumnData(
    val alignment: HorizontalAlignment
) : ParentDataModifier {

    override fun Density.modifyParentData(parentData: Any?) = this@CustomColumnData

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? CustomColumnData ?: return false
        return alignment == otherModifier.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }

    override fun toString(): String =
        "CustomColumnData(alignment=$alignment)"
}

/*
3) Интерфейс для Scope, где создаём функцию-расширение
   для добавления ParentDataModifier
 */
interface CustomColumnScope {

    @Stable
    fun Modifier.horizontalAlign(align: HorizontalAlignment) = this.then(
        CustomColumnData(align)
    )

    companion object : CustomColumnScope
}

/*
4) В Custom Layout внутри measurePolicy считываем ParentData
   и используем для размещения дочерних элементов.
 */
private val Measurable.childData: CustomColumnData?
    get() = parentData as? CustomColumnData

private val Measurable.alignment: HorizontalAlignment
    get() = childData?.alignment ?: HorizontalAlignment.Start

@Composable
fun CustomColumnWithScope(
    modifier: Modifier = Modifier,
    content: @Composable CustomColumnScope.() -> Unit
) {

    Layout(
        modifier = modifier,
        content = { CustomColumnScope.content() },
    ) { measurables: List<Measurable>, constraints: Constraints ->

        // Устанавливаем minWidth=0, чтобы Composable имели фактическую ширину контента
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0
        )

        // Измеряем детей
        val placeables = measurables.map { measurable ->
            measurable.measure(looseConstraints)
        }

        val measurableAlignment: List<HorizontalAlignment> = measurables.map { measurable ->
            measurable.alignment
        }

        var yPosition = 0

        val totalHeight: Int = placeables.sumOf { it.height }
            .coerceAtLeast(constraints.minHeight)
        val maxWidth = constraints.maxWidth

        println(
            "🤯 Constraints minWidth: ${constraints.minWidth}, " +
                    "minHeight: ${constraints.minHeight}, " +
                    "maxWidth: ${constraints.maxWidth}, " +
                    "maxHeight: ${constraints.maxHeight}, " +
                    "totalHeight: $totalHeight"
        )

        // Задаём размеры лейаута
        layout(maxWidth, totalHeight) {
            // Размещаем дочерние элементы
            placeables.forEachIndexed { index, placeable ->
                val x = when (measurableAlignment[index]) {
                    HorizontalAlignment.Start -> 0
                    HorizontalAlignment.Center -> (maxWidth - placeable.width) / 2
                    HorizontalAlignment.End -> maxWidth - placeable.width
                }

                placeable.placeRelative(x = x, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

// ------------------------------------------------------------------------------------------

/*
    ***** Пользовательская Row *****
 */

/*
1) Создаём enum для вертикального выравнивания
 */
enum class VerticalAlignment {
    Top, Center, Bottom
}

/*
2) Создаём класс, реализующий ParentDataModifier
 */
private class CustomRowData(
    val alignment: VerticalAlignment
) : ParentDataModifier {

    override fun Density.modifyParentData(parentData: Any?) = this@CustomRowData

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? CustomRowData ?: return false
        return alignment == otherModifier.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }

    override fun toString(): String =
        "CustomRowData(alignment=$alignment)"
}

/*
3) Интерфейс для Scope, где создаём функцию-расширение
   для добавления ParentDataModifier
 */
interface CustomRowScope {

    @Stable
    fun Modifier.verticalAlign(align: VerticalAlignment) = this.then(
        CustomRowData(align)
    )

    companion object : CustomRowScope
}

/*
4) Считываем ParentData в Custom Layout при размещении
 */
private val Measurable.data: CustomRowData?
    get() = parentData as? CustomRowData

private val Measurable.verticalAlignment: VerticalAlignment
    get() = data?.alignment ?: VerticalAlignment.Center

@Composable
fun CustomRowWithScope(
    modifier: Modifier = Modifier,
    content: @Composable CustomRowScope.() -> Unit
) {

    Layout(
        modifier = modifier,
        content = { CustomRowScope.content() },
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0
        )

        val placeables = measurables.map { measurable ->
            measurable.measure(looseConstraints)
        }

        val measurableAlignment: List<VerticalAlignment> = measurables.map { measurable ->
            measurable.verticalAlignment
        }

        val totalWidth: Int = placeables.sumOf { it.width }
            .coerceAtLeast(constraints.minWidth)

        var maxHeight: Int = if (constraints.hasBoundedHeight) constraints.maxHeight
        else placeables.maxOf { it.height }.coerceAtLeast(constraints.minHeight)

        println(
            "🧨 Constraints minWidth: ${constraints.minWidth}, " +
                    "minHeight: ${constraints.minHeight}, " +
                    "maxWidth: ${constraints.maxWidth}, " +
                    "maxHeight: ${constraints.maxHeight}, " +
                    "totalWidth: ${totalWidth}, " +
                    "height: $maxHeight"
        )

        var xPosition = 0

        layout(totalWidth, maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val y = when (measurableAlignment[index]) {
                    VerticalAlignment.Top -> 0
                    VerticalAlignment.Center -> (maxHeight - placeable.height) / 2
                    VerticalAlignment.Bottom -> maxHeight - placeable.height
                }
                placeable.placeRelative(x = xPosition, y = y)
                xPosition += placeable.width
            }
        }
    }
}