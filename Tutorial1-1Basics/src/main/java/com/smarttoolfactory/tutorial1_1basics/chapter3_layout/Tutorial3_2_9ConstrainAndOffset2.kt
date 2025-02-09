package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
/**
 * Зелёный прямоугольник — вся область нашего Composable. Красный —
 * это **воображаемое пространство**, которое можно зарезервировать, например,
 * под «хвостик» диалогового сообщения (bubble nip).
 * Хотя Composable покрывает и контент, и «хвостик» (так он воспринимается
 * относительно соседей),
 * реальная область для контента располагается справа от «хвостика».
 * То есть это внутренняя область после вычитания зоны «хвостика» и отступов.
 */
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen9() {
    TutorialContent2()
}

@Composable
private fun TutorialContent2() {
    var message by remember { mutableStateOf("Попробуйте набрать текст, чтобы увидеть переполнение") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TutorialHeader(text = "Constraints и Offset 2")

        val density = LocalDensity.current
        val containerWidth = with(density) {
            800f.toDp()
        }

        Column(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .width(containerWidth)
                .fillMaxHeight()
                .background(Color(0xffFBE9E7))

        ) {

            StyleableTutorialText(
                text = "Зелёный прямоугольник — вся область Composable. Красный — " +
                        "воображаемое пространство под «хвостик» сообщения. " +
                        "Хотя реальный Composable включает и контент, и «хвостик», " +
                        "только часть справа от «хвостика» доступна для контента. " +
                        "Это внутренняя область после исключения «хвостика» и отступов.",
                bullets = false
            )

            StyleableTutorialText(
                text = "Здесь мы не используем offset или constrainWidth, только " +
                        "обычный вызов measurable.measure(constraint) " +
                        "для примера отступов (padding).",
                bullets = false
            )

            LayoutOnlySamples(message)

            StyleableTutorialText(
                text = "**Constraints.constrainWidth()** ограничивает максимальную ширину " +
                        "дочерних элементов. Поскольку мы ограничиваем ширину " +
                        "(maxWidth - красная область), элементы могут «расти» больше, чем нужно.",
                bullets = false
            )

            ConstrainWidthSamples(message)

            StyleableTutorialText(
                text = "**Constraints.offset(x,y)** используется для исключения " +
                        "области под «хвостик» и отступы из измерения " +
                        "(placeable не выходит за эти границы).",
                bullets = false
            )
            ConstraintsOffsetSample(message)
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = message,
            label = { Text("Основной ввод") },
            placeholder = { Text("Впишите текст для изменения ширины") },
            onValueChange = { newValue: String ->
                message = newValue
            }
        )
    }
}

@Composable
private fun LayoutOnlySamples(message: String) {

    ComposableLayoutOnly(
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableLayoutOnly(
        modifier = Modifier.padding(vertical = 4.dp),
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableLayoutOnly(
        modifier = Modifier.padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableLayoutOnly(
        modifier = Modifier.padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }
}

@Composable
private fun ConstrainWidthSamples(message: String) {

    ComposableWithConstrainWidth(
        modifier = Modifier
            .padding(vertical = 4.dp),
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableWithConstrainWidth(
        modifier = Modifier
            .padding(vertical = 4.dp),
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableWithConstrainWidth(
        modifier = Modifier
            .padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableWithConstrainWidth(
        modifier = Modifier.padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }
}

@Composable
private fun ConstraintsOffsetSample(message: String) {

    ComposableConstraintsOffset(
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableConstraintsOffset(
        modifier = Modifier.padding(vertical = 4.dp),
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableConstraintsOffset(
        modifier = Modifier.padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }

    ComposableConstraintsOffset(
        modifier = Modifier.padding(vertical = 4.dp),
        reservedSpaceWidth = 0,
        paddingStart = 20,
        paddingTop = 20,
        paddingBottom = 20,
        paddingEnd = 20,
    ) {
        Text(
            text = message, color = Color.White,
            modifier = Modifier.background(textBackgroundColor)
        )
    }
}

@Composable
private fun ComposableLayoutOnly(
    modifier: Modifier = Modifier,
    reservedSpaceWidth: Int = 70,
    paddingStart: Int = 0,
    paddingTop: Int = 0,
    paddingEnd: Int = 0,
    paddingBottom: Int = 0,
    content: @Composable () -> Unit
) {

    val rect = remember { CustomRect() }
    val contentRect = remember { CustomRect() }

    Layout(
        modifier = modifier.drawBackgroundRectangles(rect, contentRect),
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->
        val offsetX: Int = (paddingStart + paddingEnd) + reservedSpaceWidth
        val offsetY: Int = (paddingTop + paddingBottom)

        val placeables = measurables.map { measurable: Measurable ->
            // Просто обычный measurable.measure(constraints)
            measurable.measure(constraints)
        }

        val desiredWidth: Int = placeables.maxOf { it.width } + offsetX
        val desiredHeight: Int = placeables.sumOf { it.height } + offsetY

        createLayout(
            rect,
            desiredWidth,
            desiredHeight,
            contentRect,
            reservedSpaceWidth,
            paddingStart,
            paddingTop,
            placeables
        )
    }
}

@Composable
private fun ComposableWithConstrainWidth(
    modifier: Modifier = Modifier,
    reservedSpaceWidth: Int = 70,
    paddingStart: Int = 0,
    paddingTop: Int = 0,
    paddingEnd: Int = 0,
    paddingBottom: Int = 0,
    content: @Composable () -> Unit
) {

    val rect = remember { CustomRect() }
    val contentRect = remember { CustomRect() }

    Layout(
        modifier = modifier.drawBackgroundRectangles(rect, contentRect),
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->
        val offsetX: Int = (paddingStart + paddingEnd) + reservedSpaceWidth
        val offsetY: Int = (paddingTop + paddingBottom)

        val placeables = measurables.map { measurable: Measurable ->
            // 🔥🔥 Здесь используем constraints.constrainWidth(...) и constrainHeight(...),
            // чтобы ограничить размеры.
            measurable.measure(constraints)
        }

        val desiredWidth: Int =
            constraints.constrainWidth(placeables.maxOf { it.width } + offsetX)
        val desiredHeight: Int =
            constraints.constrainHeight(placeables.sumOf { it.height } + offsetY)

        createLayout(
            rect,
            desiredWidth,
            desiredHeight,
            contentRect,
            reservedSpaceWidth,
            paddingStart,
            paddingTop,
            placeables
        )
    }
}

@Composable
private fun ComposableConstraintsOffset(
    modifier: Modifier = Modifier,
    reservedSpaceWidth: Int = 70,
    paddingStart: Int = 0,
    paddingTop: Int = 0,
    paddingEnd: Int = 0,
    paddingBottom: Int = 0,
    content: @Composable () -> Unit
) {

    val rect = remember { CustomRect() }
    val contentRect = remember { CustomRect() }

    Layout(
        modifier = modifier.drawBackgroundRectangles(rect, contentRect),
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val offsetX: Int = (paddingStart + paddingEnd) + reservedSpaceWidth
        val offsetY: Int = (paddingTop + paddingBottom)

        val placeables = measurables.map { measurable: Measurable ->
            // 🔥 С помощью constraints.offset мы уменьшаем доступную ширину/высоту
            // на offsetX/Y, исключая зону «хвостика» и отступы из измерений.
            measurable.measure(constraints.offset(-offsetX, -offsetY))
        }

        val desiredWidth: Int = placeables.maxOf { it.width } + offsetX
        val desiredHeight: Int = placeables.sumOf { it.height } + offsetY

        createLayout(
            rect,
            desiredWidth,
            desiredHeight,
            contentRect,
            reservedSpaceWidth,
            paddingStart,
            paddingTop,
            placeables
        )
    }
}

/**
 * Функция, создающая базовый лейаут в виде колонки и рисующая
 * зелёный прямоугольник (общая область) и красный (зона «хвостика»).
 */
private fun MeasureScope.createLayout(
    rect: CustomRect,
    desiredWidth: Int,
    desiredHeight: Int,
    contentRect: CustomRect,
    reservedSpaceWidth: Int,
    paddingStart: Int,
    paddingTop: Int,
    placeables: List<Placeable>
): MeasureResult {
    rect.set(0f, 0f, desiredWidth.toFloat(), desiredHeight.toFloat())
    contentRect.set(
        reservedSpaceWidth.toFloat(),
        0f,
        desiredWidth.toFloat(),
        desiredHeight.toFloat()
    )

    val xPos = paddingStart + reservedSpaceWidth
    val yPos = paddingTop
    var yNext = 0
    return layout(desiredWidth, desiredHeight) {

        placeables.forEach { placeable: Placeable ->
            placeable.placeRelative(xPos, yPos + yNext)
            yNext += placeable.height
        }
    }
}

/**
 * Рисуем зеленый прямоугольник, представляющий полный Composable,
 * и красный — выделяющий «хвостик» (зарезервированное место),
 * чтобы наглядно показать, какая часть действительно отведена под контент.
 */
private fun Modifier.drawBackgroundRectangles(
    rect: CustomRect,
    contentRect: CustomRect
) = this
    .drawBehind {
        // Рисуем белый прямоугольник под контент (можно убрать, оставлено для демонстрации)
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(contentRect.left, contentRect.top),
            size = Size(contentRect.width, contentRect.height),
            cornerRadius = CornerRadius(40f, 40f)
        )

        // Полный прямоугольник (зелёный) — вся область нашего Composable
        drawRect(
            color = Color.Red,
            topLeft = Offset(rect.left, rect.top),
            size = Size(rect.width, rect.height),
            style = Stroke(2f)
        )

        // Прямоугольник для внутренней области (зелёный контур),
        // показывающий, сколько места реально доступно под контент,
        // исключая «хвостик» и/или отступы
        drawRect(
            color = Color.Green,
            topLeft = Offset(contentRect.left, contentRect.top),
            size = Size(contentRect.width, contentRect.height),
            style = Stroke(2f)
        )
    }

/**
 * Вспомогательная модель для хранения координат прямоугольника
 * (для визуализации «хвостика» и реальной области контента).
 */
private data class CustomRect(
    var left: Float = 0f,
    var top: Float = 0f,
    var right: Float = 0f,
    var bottom: Float = 0f
) {

    fun set(left: Float, top: Float, right: Float, bottom: Float) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    val height: Float
        get() = bottom - top

    val width: Float
        get() = right - left

    override fun toString(): String {
        return "left: $left, top: $top, right: $right, bottom: $bottom, " +
                "width: $width, height: $height"
    }
}