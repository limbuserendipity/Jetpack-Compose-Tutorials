package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun Tutorial3_1Screen1() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TutorialHeader(text = "Пользовательский Modifier")
        StyleableTutorialText(
            text = "1-) Чтобы создать собственный modifier, используйте функцию расширения " +
                    "layout у Modifier, которая возвращает Modifier. " +
                    "С её помощью можно получить измеряемый (measurable), измерить " +
                    "его (получить placeable), а затем определить ширину, высоту " +
                    "и вызвать обобщённую функцию layout, которая возвращает **MeasureResult**."
        )

        TutorialText2(text = "customAlign Modifier")

        val modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.LightGray)

        Column(modifier.wrapContentHeight()) {
            Text(
                text = "Align Start with space",
                modifier = Modifier
                    .background(Color(0xFF8BC34A))
                    .customAlign(align = HorizontalAlign.START)
            )

            Text(
                text = "Align Center with space",
                modifier = Modifier
                    .background(Color(0xFF8BC34A))
                    .customAlign(align = HorizontalAlign.CENTER)
            )

            Text(
                text = "Align End with space",
                modifier = Modifier
                    .background(Color(0xFF8BC34A))
                    .customAlign(align = HorizontalAlign.END)
            )
        }

        TutorialText2(text = "firstBaselineToTop Modifier")
        Row(modifier.wrapContentHeight()) {
            Text(
                text = "Padding 32dp",
                modifier = Modifier
                    .background(Color(0xFF8BC34A))
                    .padding(top = 32.dp)
            )

            Text(
                text = "Baseline 32dp",
                modifier = Modifier
                    .background(Color(0xFF8BC34A))
                    .firstBaselineToTop(32.dp)
            )
        }

        StyleableTutorialText(
            text = "2-) **LayoutModifier** и его функция **MeasureScope.measure** " +
                    "могут использоваться для измерения (measurable), " +
                    "получения placeable и размещения его (place) с добавлением отступа (padding)."
        )
        TutorialText2(text = "Custom Padding Modifier")
        Text(
            text = "Custom Padding",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .paddingNoOffsetNoConstrain(all = 4.dp)
        )

        StyleableTutorialText(
            text = "3-) **Modifier.composed** позволяет modifier иметь " +
                    "remember или SideEffects, чтобы хранить в памяти или состояние " +
                    "для каждого элемента, к которому он применяется."
        )

        // 🔥 composedBackground использует remember, чтобы сохранять первоначальный расчёт цвета,
        // используя key вместе с remember

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            var counter by remember { mutableIntStateOf(0) }

            Button(
                onClick = { counter++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Увеличить $counter")
            }

            TutorialText2(text = "Modifier.composed")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(
                    modifier = Modifier
                        .composedBackground(150.dp, 20.dp, 0)
                        .width(150.dp)
                ) {
                    Text(text = "Пересоздано $counter")
                }

                Box(
                    modifier = Modifier
                        .composedBackground(150.dp, 20.dp, 1)
                        .width(150.dp)
                ) {
                    Text(text = "Пересоздано $counter")
                }
            }

            TutorialText2(text = "Modifier без composed")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(
                    modifier = Modifier
                        .nonComposedBackground(150.dp, 20.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Пересоздано $counter")
                }

                Box(
                    modifier = Modifier
                        .nonComposedBackground(150.dp, 20.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Пересоздано $counter")
                }
            }
        }
    }
}

/**
 * Фиктивный modifier, который добавляет отступы по обе стороны [Measurable] на заданное количество dp
 * и выравнивает [Measurable] по заданному горизонтальному выравниванию
 */
fun Modifier.customAlign(
    space: Int = 60,
    align: HorizontalAlign = HorizontalAlign.CENTER
) = this.then(
    layout { measurable: Measurable, constraints: Constraints ->

        val placeable = measurable.measure(constraints)
        val width = placeable.measuredWidth + 2 * space

        layout(width, placeable.measuredHeight) {
            when (align) {
                HorizontalAlign.START -> {
                    placeable.placeRelative(0, 0)
                }

                HorizontalAlign.CENTER -> {
                    placeable.placeRelative(space, 0)
                }

                HorizontalAlign.END -> {
                    placeable.placeRelative(2 * space, 0)
                }
            }
        }
    }
)

enum class HorizontalAlign {
    START, CENTER, END
}

/**
 * Допустим, нужно отобразить Text на экране и управлять расстоянием от верха до
 * базовой линии (baseline) первой строки. Чтобы это сделать, нужно вручную разместить
 * composable с помощью layout модификатора.
 */
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->

        val placeable = measurable.measure(constraints)

        // Проверяем, что composable имеет первую базовую линию
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Высота composable с учётом отступа - первая базовая линия
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Размещение composable
            placeable.placeRelative(0, placeableY)
        }
    }
)

// Создаёт stateful modifier с несколькими аргументами
fun Modifier.composedBackground(width: Dp, height: Dp, index: Int) = composed(
    // Передаём информацию для отладки
    inspectorInfo = debugInspectorInfo {
        name = "myModifier"
        properties["width"] = width
        properties["height"] = height
        properties["index"] = index
    },
    // Реализация модификатора, вычисляемая для каждого элемента
    factory = {
        val density = LocalDensity.current

        val color: Color = remember(index) {
            Color(
                red = Random.nextInt(256),
                green = Random.nextInt(256),
                blue = Random.nextInt(256),
                alpha = 255
            )
        }

        // 🔥 Без remember этот цвет будет заново создаваться каждый раз, когда элемент с этим модификатором будет пересоздаваться
//        val color: Color = Color(
//            red = Random.nextInt(256),
//            green = Random.nextInt(256),
//            blue = Random.nextInt(256),
//            alpha = 255
//        )

        Modifier.drawBehind {
            val widthInPx = with(density) { width.toPx() }
            val heightInPx = with(density) { height.toPx() }

            drawRect(color = color, topLeft = Offset.Zero, size = Size(widthInPx, heightInPx))
        }
    }
)

fun Modifier.nonComposedBackground(width: Dp, height: Dp) = this.then(
    Modifier.drawBehind {
        // 🔥 Без remember этот цвет будет создаваться заново каждый раз, когда элемент с этим модификатором пересоздаётся
        val color: Color = Color(
            red = Random.nextInt(256),
            green = Random.nextInt(256),
            blue = Random.nextInt(256),
            alpha = 255
        )

        val widthInPx = width.toPx()
        val heightInPx = height.toPx()

        drawRect(color = color, topLeft = Offset.Zero, size = Size(widthInPx, heightInPx))
    }
)

/**
 * Modifier для применения режима смешивания (Porter-Duff mode)
 */
fun Modifier.drawOffscreen(): Modifier = this.drawWithContent {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        drawContent()
        restoreToCount(checkPoint)
    }
}
