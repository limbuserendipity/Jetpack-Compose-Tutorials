package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
@Preview(showBackground = true)
@Composable
fun Tutorial3_2Screen7() {
    TutorialContent()
}

@Preview(showBackground = true)
@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TutorialHeader(text = "Бесконечные Constraints (Infinite Constraints)")

        StyleableTutorialText(
            text = "Бесконечные Constraints, или **Constraints.Infinity**, возникают, " +
                    "когда для лейаута используется **Modifier.scroll** или же, " +
                    "когда родитель явно передаёт неограниченные размеры. " +
                    "Однако при работе с **Constraints.Infinity** есть ограничения: " +
                    "любые математические операции с **Constraints.Infinity** могут вызвать " +
                    "исключение, если, например, нужно посчитать половину или сумму " +
                    "бесконечного Constraints."
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .border(2.dp, Color.Green)
        ) {
            CustomLayout(
                modifier = Modifier
                    .border(4.dp, Color.Red)
            ) {
                Text("Привет, Мир!", modifier = Modifier.border(5.dp, Color.Blue))
            }
        }

        // 🔥 Модификатор Intrinsic (например, height(IntrinsicSize.Min)) может вызвать
        // дополнительный проход измерения (Layout), сначала с 0 и Constraints.Infinity,
        // а затем повторно с вычисленной шириной.
//        CustomLayout(
//            modifier = Modifier
//                .border(2.dp, Color.Green)
//                .height(IntrinsicSize.Min)
//        ) {
//            Text("Привет, Мир!", modifier = Modifier.border(2.dp, Color.Blue))
//            Box(
//                modifier = Modifier
//                    .width(100.dp)
//                    .height(40.dp)
//                    .background(Color.Red)
//            )
//        }
    }
}

@Composable
private fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Пример модификации Constraints
        val wrappedConstraints = constraints.copy(
            // 🔥 1) minHeight не может быть больше maxHeight,
            // и нельзя устанавливать их обоих в Constraints.Infinity
            // поскольку Placeable не может иметь бесконечный размер
            /*
               Бросает исключение: "Can't represent a size of 2147483647 in Constraints"
             */
            // Для демонстрации. Если раскомментировать, увидим ошибку:
//            minHeight = Constraints.Infinity,
//            maxHeight = Constraints.Infinity

            // 🔥 2) Математические операции с Constraints.Infinity недопустимы
            // Если constraints.maxHeight равно Infinity, делить на 2 нельзя.
            /*
              Бросает ошибку: "Can't represent a size of 1073741823 in Constraints"
            */
//            maxHeight = constraints.maxHeight / 2
        )

        val placeables = measurables.map {
            // Измеряем каждый дочерний элемент
            it.measure(wrappedConstraints)
        }

        var y = 0

        // Вычисляем ширину лейаута
        val layoutWidth = placeables.maxOf { it.width }

        // 🔥 3) Если maxHeight = Constraints.Infinity и используется
        // IntrinsicSize.Min или IntrinsicSize.Max, при измерении может быть исключение.
        val layoutHeight = constraints.maxHeight

        println(
            "🍏 CustomLayout MeasureScope layoutHeight: $layoutHeight\n" +
                    "constraints(): $constraints\n" +
                    "wrappedConstraints: $wrappedConstraints"
        )

        layout(layoutWidth, layoutHeight) {
            println("🍏🍏 CustomLayout Placement Scope")

            placeables.forEach { placeable ->
                placeable.placeRelative(0, y)
                y += placeable.height
            }
        }
    }
}
