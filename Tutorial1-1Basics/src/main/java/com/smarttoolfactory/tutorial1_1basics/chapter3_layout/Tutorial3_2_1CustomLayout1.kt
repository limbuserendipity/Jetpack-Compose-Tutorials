package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import android.graphics.Point
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen1() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        modifier = Modifier
            .border(3.dp, Color.Red)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        TutorialHeader(text = "Пользовательские лейауты 1")

        ChipStaggeredGrid(
            modifier = Modifier
//                .fillMaxWidth()
                .width(250.dp)
                .border(3.dp, Color.Green)
        ) {
            for (topic in topics) {
                Chip(
                    modifier = Modifier
                        .border(3.dp, Color.Cyan)
                        .padding(8.dp),
                    text = topic
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        ChipStaggeredGrid(
            modifier = Modifier
                .fillMaxWidth()
                .border(3.dp, Color.Green)
        ) {
            for (topic in topics) {
                Chip(
                    modifier = Modifier
                        .border(3.dp, Color.Cyan)
                        .padding(8.dp),
                    text = topic
                )
            }
        }
    }
}

@Composable
private fun CustomColumn(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        // 🔥 Устанавливаем minWidth в 0, чтобы ширина соответствовала только ширине placeable.
        // Иначе, при Modifier.fillMaxWidth/Size каждый Composable получит
        // minWidth = maxWidth.
        val looseConstraints = constraints.copy(minWidth = 0)

        val placeables = measurables.map { measurable ->
            // Измеряем каждый дочерний элемент
            measurable.measure(looseConstraints)
        }

        // Координата по оси Y, до которой мы расположили элементы
        var yPosition = 0

        val totalHeight: Int = placeables.sumOf {
            it.height
        }

        // Размер лейаута будет настолько большим, насколько он может
        layout(constraints.maxWidth, totalHeight) {
            // Размещаем дочерние элементы внутри родительского лейаута
            placeables.forEach { placeable ->

                // Размещаем элемент на экране
                placeable.placeRelative(x = 0, y = yPosition)

                // Запоминаем координату Y, до которой мы расположили элемент
                yPosition += placeable.height
            }
        }
    }
}

@Composable
private fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        val size = Random.nextInt(10, 40)

        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(size.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

/**
 * Этот лейаут создаёт "зависимую" (staggered) сетку, где каждая «фишка» (Chip) в новой строке
 * основывается на максимальной высоте фишки в предыдущей строке.
 */
@Composable
fun ChipStaggeredGrid(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val constraintMaxWidth = constraints.maxWidth

        var maxRowWidth = 0

        var currentWidthOfRow = 0
        var totalHeightOfRows = 0

        var xPos: Int
        var yPos: Int

        val placeableMap = linkedMapOf<Int, Point>()
        val rowHeights = mutableListOf<Int>()

        var maxPlaceableHeight = 0
        var lastRowHeight = 0

        val placeables: List<Placeable> = measurables.mapIndexed { index, measurable ->

            // Измеряем каждый дочерний элемент
            val placeable =
                measurable.measure(
                    constraints.copy(
                        minWidth = 0,
                        minHeight = 0,
//                        maxWidth = Constraints.Infinity
                    )
                )

            val placeableWidth = placeable.width
            val placeableHeight = placeable.height

            // Будем ли мы продолжать ту же строку?
            // Если сумма текущей ширины строки и ширины этого элемента
            // меньше чем constraintMaxWidth (то есть вписывается в ширину лейаута)
            val isSameRow = (currentWidthOfRow + placeableWidth <= constraintMaxWidth)

            if (isSameRow) {
                xPos = currentWidthOfRow
                yPos = totalHeightOfRows

                // Теперь ширина строки — это существующая длина плюс ширина нового элемента
                currentWidthOfRow += placeableWidth

                // Получаем максимальную высоту элемента в строке
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                // Проверяем, не самая ли это длинная строка
                maxRowWidth = maxRowWidth.coerceAtLeast(currentWidthOfRow)

                lastRowHeight = maxPlaceableHeight

            } else {
                // Начинаем новую строку
                currentWidthOfRow = placeableWidth
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                // Добавляем высоту предыдущей строки к общей высоте
                totalHeightOfRows += maxPlaceableHeight

                xPos = 0
                yPos = totalHeightOfRows

                rowHeights.add(maxPlaceableHeight)

                lastRowHeight = maxPlaceableHeight
                maxPlaceableHeight = placeableHeight
            }

            placeableMap[index] = Point(xPos, yPos)
            placeable
        }

        // Высота лейаута будет суммой высоты всех строк плюс высота последней строки
        val finalHeight = (rowHeights.sumOf { it } + lastRowHeight)
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Контейнер может быть больше или меньше, чем maxWidth,
        // поэтому ограничиваем итоговую ширину в min..max
        if (constraints.hasFixedWidth && constraints.hasBoundedWidth) {
            maxRowWidth =
                maxRowWidth.coerceIn(
                    minimumValue = constraints.minWidth,
                    maximumValue = constraints.maxWidth
                )
        }

        // Устанавливаем размер лейаута
        layout(maxRowWidth, finalHeight) {
            // Размещаем дочерние элементы внутри родительского лейаута
            placeables.forEachIndexed { index, placeable ->
                val point = placeableMap[index]
                point?.let {
                    placeable.placeRelative(x = point.x, y = point.y)
                }
            }
        }
    }
}

// Список текстовых тем (слова)
private val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)
