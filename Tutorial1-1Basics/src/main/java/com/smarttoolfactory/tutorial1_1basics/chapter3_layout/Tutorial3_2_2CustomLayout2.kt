package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.Brown400
import com.smarttoolfactory.tutorial1_1basics.ui.Green400
import com.smarttoolfactory.tutorial1_1basics.ui.Yellow400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen2() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        TutorialHeader(text = "Пользовательские Layout'ы 2")
        StyleableTutorialText(
            text = "1) Пользовательские лейауты могут использовать объект, который " +
                    "реализует интерфейс **MeasurePolicy**. В этом примере мы рассмотрим такой подход."
        )

        TutorialText2(text = "Пользовательский лейаут с fillMaxWidth")

        // Этот лейаут занимает в два раза больше места, чем суммарные габариты
        // его дочерних элементов, и выравнивает их по нижнему правому углу.
        CustomLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.DarkGray)
        ) {
            Column(modifier = Modifier.background(Color.LightGray)) {
                Text(
                    "Первый текст",
                    modifier = Modifier
                        .background(Color(0xffF44336)),
                    color = Color.White
                )
                Text(
                    "Второй текст",
                    modifier = Modifier
                        .background(Color(0xff9C27B0)),
                    color = Color.White
                )
                Text(
                    "Третий текст",
                    modifier = Modifier
                        .background(Color(0xff2196F3)),
                    color = Color.White
                )
            }
        }

        TutorialText2(text = "Пользовательский лейаут без модификатора ширины")
        CustomLayout(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight()
                .background(Color.DarkGray)
        ) {
            Column(modifier = Modifier.background(Color.LightGray)) {
                Text(
                    "Первый текст",
                    modifier = Modifier
                        .background(Color(0xffF44336)),
                    color = Color.White
                )
                Text(
                    "Второй текст",
                    modifier = Modifier
                        .background(Color(0xff9C27B0)),
                    color = Color.White
                )
                Text(
                    "Третий текст",
                    modifier = Modifier
                        .background(Color(0xff2196F3)),
                    color = Color.White
                )
            }
        }

        StyleableTutorialText(
            text = "2) Вложенные размеры (intrinsic dimensions) можно использовать, чтобы " +
                    "установить габариты наподобие placeholder'ов. Компонент рекурсивно " +
                    "опрашивает дочерние элементы, чтобы найти подходящее значение. Даже " +
                    "если это Column, выложенный по сумме высот дочерних элементов, " +
                    "установка (для демонстрации) фиксированных значений в min/max intrinsic " +
                    "height приведёт к тому, что свободное пространство будет подгоняться " +
                    "под эти значения, а не под реальную высоту."
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            TutorialText2(text = "Без модификатора высоты")
            CustomColumnWithIntrinsicDimensions(
                modifier = Modifier
                    .width(100.dp)
                    .background(Green400)
                    .padding(4.dp)
            ) {
                Text(
                    "Первый текст",
                    modifier = Modifier
                        .background(Color(0xffF44336)),
                    color = Color.White
                )
                Text(
                    "Второй текст",
                    modifier = Modifier
                        .background(Color(0xff9C27B0)),
                    color = Color.White
                )
            }

            TutorialText2(text = "height(IntrinsicSize.Min)")
            CustomColumnWithIntrinsicDimensions(
                modifier = Modifier
                    .width(100.dp)
                    .height(IntrinsicSize.Min)
                    .background(Yellow400)
                    .padding(4.dp)
            ) {
                Text(
                    "Первый текст",
                    modifier = Modifier
                        .background(Color(0xffF44336)),
                    color = Color.White
                )
                Text(
                    "Второй текст",
                    modifier = Modifier
                        .background(Color(0xff9C27B0)),
                    color = Color.White
                )
            }

            TutorialText2(text = "height(IntrinsicSize.Max)")
            CustomColumnWithIntrinsicDimensions(
                modifier = Modifier
                    .width(100.dp)
                    .height(IntrinsicSize.Max)
                    .background(Blue400)
                    .padding(4.dp)
            ) {
                Text(
                    "Первый текст",
                    modifier = Modifier
                        .background(Color(0xffF44336)),
                    color = Color.White
                )
                Text(
                    "Второй текст",
                    modifier = Modifier
                        .background(Color(0xff9C27B0)),
                    color = Color.White
                )
            }

            TutorialText2(text = "height(IntrinsicSize.Min) у братьев (siblings)")

            // 🔥🔥 Высота определяется наибольшим IntrinsicSize.Min, который вернули Layout'ы
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .border(1.dp, Color.Red)
            ) {

                CustomColumnWithIntrinsicDimensions(
                    modifier = Modifier
                        // Это влияет на высоту этого компонента
                        // Родительская высота определяется сравнением с другими,
                        // чтобы определить наибольший IntrinsicSize
                        .height(IntrinsicSize.Min)
                        .width(100.dp)
                        .background(Blue400)
                        .padding(4.dp)
                ) {
                    Text(
                        "Первый текст",
                        modifier = Modifier
                            .background(Color(0xffF44336)),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                CustomColumnWithIntrinsicDimensions2(
                    modifier = Modifier
                        // Аналогично
                        .height(IntrinsicSize.Min)
                        .width(100.dp)
                        .background(Yellow400)
                        .padding(4.dp)
                ) {
                    Text(
                        "Первый текст",
                        modifier = Modifier
                            .background(Color(0xffF44336)),
                        color = Color.White
                    )
                }
            }

            // 🔥🔥 Высота определяется наибольшим IntrinsicSize.Max, который вернули Layout'ы
            TutorialText2(text = "height(IntrinsicSize.Max) у братьев (siblings)")
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Red)
                    .height(IntrinsicSize.Max)
            ) {

                CustomColumnWithIntrinsicDimensions(
                    modifier = Modifier
                        // Это влияет на высоту этого компонента
                        .height(IntrinsicSize.Max)
                        .width(100.dp)
                        .background(Blue400)
                        .padding(4.dp)
                ) {
                    Text(
                        "Первый текст",
                        modifier = Modifier
                            .border(2.dp, Green400)
                            .background(Color(0xffF44336)),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                CustomColumnWithIntrinsicDimensions2(
                    modifier = Modifier
                        // Аналогично
                        .height(IntrinsicSize.Max)
                        .width(100.dp)
                        .background(Yellow400)
                        .padding(4.dp)
                ) {
                    Text(
                        "Первый текст",
                        modifier = Modifier
                            .border(2.dp, Brown400)
                            .background(Color(0xffF44336)),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    // Построим лейаут, занимающий вдвое больше места, чем суммарные габариты его дочерних элементов,
    // и выравнивающий их по правому нижнему углу.
    val measurePolicy = MeasurePolicy { measurables, constraints ->

        println(
            "🔥 CustomLayout Constraints\n" +
                    "minWidth: ${constraints.minWidth}, " +
                    "maxWidth: ${constraints.maxWidth}, " +
                    "boundedWidth: ${constraints.hasBoundedWidth}, " +
                    "fixedWidth: ${constraints.hasFixedWidth}\n" +
                    "minHeight: ${constraints.minHeight}, " +
                    "maxHeight: ${constraints.maxHeight}, " +
                    "hasBoundedHeight: ${constraints.hasBoundedHeight}, " +
                    "hasFixedHeight: ${constraints.hasFixedHeight}\n"
        )

        // measurables — это список элементов (каждый дочерний элемент) для нашего лейаута.
        val childConstraints = Constraints(
            minWidth = constraints.minWidth,
            minHeight = constraints.minHeight
        )
        // Измеряем детей с childConstraints
        val placeables = measurables.map { it.measure(childConstraints) }

        // Определяем размеры текущего лейаута: двойная ширина и высота
        // самого широкого и самого высокого дочернего элемента
        val layoutWidth = (placeables.maxByOrNull { it.width }?.width ?: 0) * 2
        val layoutHeight = (placeables.maxByOrNull { it.height }?.height ?: 0) * 2

        layout(layoutWidth, layoutHeight) {
            // Размещаем детей в правом нижнем углу
            placeables.forEach {
                it.placeRelative(
                    x = layoutWidth - it.width,
                    y = layoutHeight - it.height
                )
            }
        }
    }

    Layout(modifier = modifier, content = content, measurePolicy = measurePolicy)
}

/**
 * CustomColumn из предыдущего урока, но с Intrinsic Height,
 * где minIntrinsicHeight и maxIntrinsicHeight возвращают
 * некоторый статический размер (для демонстрации).
 */
@Composable
fun CustomColumnWithIntrinsicDimensions(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val measurePolicy = object : MeasurePolicy {

        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {

            val looseConstraints = constraints.copy(minHeight = 0)
            val placeables = measurables.map { measurable ->
                measurable.measure(looseConstraints)
            }

            var yPosition = 0

            val totalHeight: Int = placeables.sumOf {
                it.height
            }

            // 🔥 Можно также взять максимум ширины Composable или maxWidth Constraints
            val maxWidth: Int = placeables.maxOf {
                it.width
            }

            return layout(maxWidth, totalHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ): Int {

            println("🍏 minIntrinsicHeight() width: $width, measurables: ${measurables.size}")
            // 🔥 Для примера возвращаем статическое значение
            return 200
        }

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ): Int {

            println("🍎 maxIntrinsicHeight() width: $width, measurables: ${measurables.size}")
            // 🔥 Для примера возвращаем статическое значение
            return 400
        }
    }

    Layout(modifier = modifier, content = content, measurePolicy = measurePolicy)
}


/**
 * CustomColumn, где minIntrinsicHeight возвращает меньшее значение,
 * а maxIntrinsicHeight — большее.
 */
@Composable
fun CustomColumnWithIntrinsicDimensions2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val measurePolicy = object : MeasurePolicy {

        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {

            val looseConstraints = constraints.copy(minHeight = 0)
            val placeables = measurables.map { measurable ->
                measurable.measure(looseConstraints)
            }

            var yPosition = 0

            val totalHeight: Int = placeables.sumOf {
                it.height
            }

            val maxWidth: Int = placeables.maxOf {
                it.width
            }

            return layout(maxWidth, totalHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ): Int {

            println("🚙 minIntrinsicHeight() width: $width, measurables: ${measurables.size}")
            // 🔥 Для примера возвращаем статическое значение (не делать так в реальном коде)
            return 80
        }

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ): Int {

            println("🚗 maxIntrinsicHeight() width: $width, measurables: ${measurables.size}")
            // 🔥 Для примера возвращаем статическое значение (не делать так в реальном коде)
            return 500
        }
    }

    Layout(modifier = modifier, content = content, measurePolicy = measurePolicy)
}
