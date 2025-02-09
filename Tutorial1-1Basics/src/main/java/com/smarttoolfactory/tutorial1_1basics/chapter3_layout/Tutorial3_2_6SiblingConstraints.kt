package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Orange400
import com.smarttoolfactory.tutorial1_1basics.ui.Pink400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen6() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        ConstraintsAndSiblingsSample()
    }
}

@Composable
private fun ConstraintsAndSiblingsSample() {

    var layoutWidth by remember { mutableFloatStateOf(700f) }

    TutorialHeader(text = "Constraints между соседями (Siblings)")

    StyleableTutorialText(
        text = "В этом примере мы задаём ширину лейаута (layoutWidth) через слайдер, " +
                "но если **layoutWidth** выходит за диапазон min-max ширины **Constraints**, " +
                "приходящих, например, из **Modifier**, то содержимое (оранжевый блок) " +
                "будет смещено (как в предыдущих уроках). " +
                "Оранжевый контент измеряется в диапазоне от 0 до (min(layoutWidth, Constraints.maxWidth))."
    )

    TutorialText2(
        text = "Без модификатора размера (размер 0..ширина родителя)"
    )
    Row(modifier = Modifier.fillMaxWidth()) {

        CustomLayout(
            modifier = Modifier.border(2.dp, Green400),
            layoutWidth = layoutWidth.toInt()
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    color = Color.White,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(Orange400)
                        .clickable {}
                )
            }
        }
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(2.dp, Pink400)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    TutorialText2(
        text = "Modifier.width(200.dp)"
    )
    Row(modifier = Modifier.fillMaxWidth()) {

        // 🔥 Когда layoutWidth не совпадает с 200.dp в пикселях, контент будет смещён
        CustomLayout(
            modifier = Modifier
                .border(2.dp, Green400)
                .width(200.dp),
            layoutWidth = layoutWidth.toInt()
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    color = Color.White,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(Orange400)
                        .clickable {}
                )
            }
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(2.dp, Pink400)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    TutorialText2(
        text = "Modifier.widthIn(min = 100.dp, max = 200.dp)"
    )
    // 🔥 Когда layoutWidth выходит за диапазон 100..200dp, контент будет смещён
    Row(modifier = Modifier.fillMaxWidth()) {

        CustomLayout(
            modifier = Modifier
                .border(2.dp, Green400)
                .widthIn(min = 100.dp, max = 200.dp),
            layoutWidth = layoutWidth.toInt()
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    color = Color.White,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(Orange400)
                        .clickable {}
                )
            }
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(2.dp, Pink400)
        )
    }

    TutorialText2(
        text = "Ширина Composable (layoutWidth). Если ширина выходит " +
                "за исходные Constraints, контент будет смещён на разницу " +
                "между layoutWidth и (minWidth или maxWidth) Constraints."
    )

    SliderWithLabel(
        label = "Layout Width: ${layoutWidth.toInt()}",
        value = layoutWidth
    ) {
        layoutWidth = it
    }
}

@Composable
private fun CustomLayout(
    modifier: Modifier = Modifier,
    layoutWidth: Int,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(
                constraints.copy(
                    minWidth = 0,
                    maxWidth = layoutWidth,
                    // Для наглядности фиксируем высоту в 200px
                    minHeight = 200,
                    maxHeight = 200
                )
            )
        }

        val totalHeight = placeables.sumOf { it.height }
        var posY = 0

        // 🔥 Меняем ширину (layoutWidth) — если она выходит за Constraints, элемент смещается.
        // Например, если layoutWidth=600, а maxWidth=550 => смещение на (550 - 600)/2 = -25px
        layout(width = layoutWidth, height = totalHeight) {
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, posY)
                posY += placeable.height
            }
        }
    }
}
