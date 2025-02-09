package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Orange400
import com.smarttoolfactory.tutorial1_1basics.ui.Pink400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen4() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            // 🔥 Обратите внимание: при использовании вертикального скролла
            // hasBoundedHeight может возвращать Constraints.Infinity.
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TutorialHeader(text = "Границы (Bounds) Constraints")
        ConstraintsSample()
    }
}

@Composable
private fun ConstraintsSample() {

    val density = LocalDensity.current
    val containerWidth = with(density) {
        700f.toDp()
    }

    Column(
        modifier = Modifier
            .width(containerWidth)
            .background(Color(0xffFBE9E7))
            .fillMaxHeight()
    ) {

        StyleableTutorialText(
            text = "Когда ширина лейаута выходит за рамки " +
                    "**Constraints.minWidth**..**Constraints.maxWidth**, " +
                    "родительский лейаут помещается в позицию " +
                    "(Constraints.maxWidth - ширина лейаута)/2 или " +
                    "(Constraints.minWidth - ширина лейаута)/2.\n" +
                    "Constraints, используемые для измерения дочерних элементов, " +
                    "определяют их размер. \n" +
                    "Установка ширины лейаута определяет, где родитель будет помещён " +
                    "и какой участок он займёт. В примере выбрана ширина " +
                    "containerWidth = 700px.",
            bullets = false
        )

        StyleableTutorialText(
            text = "1) В этом примере дочерние Composable измеряются с **constraints**, " +
                    "ограничивающими maxWidth = **containerWidth=700**."
        )
        MyLayout(
            modifier = Modifier.border(3.dp, Green400)
        ) { Content() }

        Spacer(modifier = Modifier.height(10.dp))

        StyleableTutorialText(
            text = "2) В этом примере дочерние Composable измеряются с " +
                    "**constraints.copy(minWidth = 750, maxWidth = 900)**.\n" +
                    "Из-за того, что у дочерних Composable ширина больше контейнера, они " +
                    "выходят за границы родительского Composable."
        )

        MyLayout2(
            modifier = Modifier.border(3.dp, Green400)
        ) { Content() }

        Spacer(modifier = Modifier.height(10.dp))

        StyleableTutorialText(
            text = "3) В этом примере MyLayout3 (зелёная рамка) " +
                    "выходит за границы родителя, так как **Constraints.maxWidth = 700**, " +
                    "а ширина лейаута = 900px. Элемент смещается на -100px влево " +
                    "( (700 - 900) / 2 )."
        )

        MyLayout3(modifier = Modifier.border(3.dp, Green400)) {
            Content()
        }

        val minWidth = with(density) {
            600f.toDp()
        }

        StyleableTutorialText(
            text = "4) В этом примере ширина лейаута = 400px, " +
                    "а **Constraints.minWidth = 600px** и **Constraints.maxWidth = 700px**. " +
                    "MyLayout4(зелёная рамка) размещается на " +
                    "**(Constraints.minWidth - ширина лейаута)/2**.\n" +
                    "Также дочерний Composable измеряется с " +
                    "**constraints.copy(minWidth = 100, maxWidth = 500)**."
        )

        // 🔥 Размещается на ((600f - 400f)/2) = 100px от левого края родительского Composable
        MyLayout4(
            modifier = Modifier
                .widthIn(min = minWidth)
                .border(3.dp, Green400)
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    color = Color.White,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(Pink400)
                )
            }
        }

        StyleableTutorialText(
            text = "5) В этом примере минимальная/максимальная ширина модификатора (в px), " +
                    "минимальная/максимальная ширина constraints (в px) " +
                    "и ширина лейаута настраиваются слайдерами, чтобы можно было " +
                    "наблюдать, как дочерние и родительские Composable размещаются."
        )

        ConstraintsOffsetAndBoundsSample()
    }
}

@Composable
private fun ConstraintsOffsetAndBoundsSample() {

    var minWidth by remember { mutableFloatStateOf(100f) }
    var maxWidth by remember { mutableFloatStateOf(700f) }
    var constraintsMinWidth by remember { mutableFloatStateOf(100f) }
    var constraintsMaxWidth by remember { mutableFloatStateOf(700f) }
    var layoutWidth by remember { mutableFloatStateOf(700f) }

    LayoutWithWidthParams(
        minWidth = minWidth.toInt(),
        maxWidth = maxWidth.toInt(),
        constraintsMinWidth = constraintsMinWidth.toInt(),
        constraintsMaxWidth = constraintsMaxWidth.toInt(),
        layoutWidth = layoutWidth.toInt()
    ) {
        BoxWithConstraints {
            Text(
                text = "Constraints: $constraints",
                color = Color.White,
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(8.dp))
                    .background(Orange400)
            )
        }
    }

    TutorialText2(
        text = "minWidth и maxWidth для модификатора, " +
                "изначальные Constraints берутся из этих значений"
    )

    SliderWithLabel(
        label = "MinWidth: ${minWidth.toInt()}",
        value = minWidth
    ) {
        if (it <= maxWidth) {
            minWidth = it
        }
    }

    SliderWithLabel(
        label = "MaxWidth: ${maxWidth.toInt()}",
        value = maxWidth
    ) {
        if (it >= minWidth) {
            maxWidth = it
        }
    }

    TutorialText2(
        text = "Ширина лейаута (parent Composable). Если выходит за рамки " +
                "(minWidth..maxWidth), элемент смещается.\n" +
                "От того, какая ширина выбрана, зависит позиция и покрытие лейаута."
    )

    SliderWithLabel(
        label = "Layout Width: ${layoutWidth.toInt()}",
        value = layoutWidth
    ) {
        layoutWidth = it
    }

    TutorialText2(text = "Дочерний Composable измеряется по значениям (constraintsMinWidth, constraintsMaxWidth)")

    SliderWithLabel(
        label = "Child Constraints MinWidth: ${constraintsMinWidth.toInt()}",
        value = constraintsMinWidth
    ) {
        if (it <= constraintsMaxWidth) {
            constraintsMinWidth = it
        }
    }

    SliderWithLabel(
        label = "Child Constraints MaxWidth: ${constraintsMaxWidth.toInt()}",
        value = constraintsMaxWidth
    ) {
        if (it >= constraintsMinWidth) {
            constraintsMaxWidth = it
        }
    }
}

@Composable
internal fun SliderWithLabel(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Text(label)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1000f
        )
    }
}

@Composable
private fun Content() {

    val density = LocalDensity.current

    val child1Width = with(density) {
        800.toDp()
    }

    val child2Width = with(density) {
        600.toDp()
    }

    BoxWithConstraints(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .width(child1Width)
            .background(Pink400)
            .clickable {}
    ) {
        Text("constraints1: $constraints", color = Color.White)
    }

    BoxWithConstraints(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .width(child2Width)
            .background(Blue400)
            .clickable {}
    ) {
        Text("constraints2: $constraints", color = Color.White)
    }
}

@Composable
private fun MyLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(constraints)
        }

        val totalHeight = placeables.sumOf { it.height }

        var posY = 0
        layout(constraints.maxWidth, totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}

@Composable
private fun MyLayout2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        // Измеряем дочерние элементы Constraints, которые больше, чем у родителя.
        val updatedConstraints = constraints.copy(minWidth = 750, maxWidth = 900)

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(updatedConstraints)
        }

        println(
            "🔥 MyLayout2\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints\n"
        )

        val totalHeight = placeables.sumOf { it.height }

        var posY = 0

        layout(constraints.maxWidth, totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}

@Composable
private fun MyLayout3(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(constraints)
        }

        println(
            "🔥 MyLayout3\n" +
                    "constraints: $constraints"
        )

        val totalHeight = placeables.sumOf { it.height }

        var posY = 0

        // 🔥🔥 Меняем ширину на 900, что больше, чем constraints.maxWidth=700.
        // Из-за этого лейаут выходит за границы родителя.
        layout(width = 900, height = totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}

@Composable
private fun MyLayout4(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints = constraints.copy(minWidth = 100, maxWidth = 500)

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(updatedConstraints)
        }

        println(
            "🔥 MyLayout4\n" +
                    "constraints: $constraints\n" +
                    "updatedConstraints: $updatedConstraints\n"
        )

        val totalHeight = placeables.sumOf { it.height }

        var posY = 0

        // 🔥🔥 Устанавливаем ширину лейаута в 400. Если это меньше Constraints.minWidth,
        // лейаут будет смещён в зависимости от разницы.
        layout(width = 400, height = totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}

@Composable
private fun LayoutWithWidthParams(
    minWidth: Int,
    maxWidth: Int,
    constraintsMinWidth: Int,
    constraintsMaxWidth: Int,
    layoutWidth: Int,
    content: @Composable () -> Unit
) {

    val density = LocalDensity.current
    val minWidthDp = density.run { minWidth.toDp() }
    val maxWidthDp = density.run { maxWidth.toDp() }

    Layout(
        modifier = Modifier
            .widthIn(min = minWidthDp, max = maxWidthDp)
            .border(3.dp, Green400),
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val updatedConstraints = constraints.copy(
            minWidth = constraintsMinWidth,
            maxWidth = constraintsMaxWidth
        )

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(updatedConstraints)
        }

        val totalHeight = placeables.sumOf { it.height }
        var posY = 0

        // 🔥 Меняем фактическую ширину родительского лейаута
        // (если она выходит за рамки, Composable будет смещён)
        layout(width = layoutWidth, height = totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}
