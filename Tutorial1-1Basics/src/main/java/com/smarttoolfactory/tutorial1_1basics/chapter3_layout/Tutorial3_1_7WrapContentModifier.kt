package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.R
import com.smarttoolfactory.tutorial1_1basics.ui.Pink400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

/*
    Подробнее по теме см. по ссылке:
    https://stackoverflow.com/a/73316247/5457853
 */
@Preview(showBackground = true)
@Composable
fun Tutorial3_1Screen7() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TutorialHeader(text = "wrapContentSize Modifier")

        /**
         * Modifier.wrapContentSize позволяет контенту измеряться в соответствии
         * с его желаемым размером, игнорируя входящие минимальные размеры
         * (Constraints.minWidth, Constraints.minHeight).
         * Если [unbounded] = true, игнорируются также и максимальные ограничения.
         *
         * Если измеренный размер контента меньше минимального,
         * то [align] выравнивает внутри минимально заданного пространства.
         * Если измеренный размер больше максимального (возможен при [unbounded] = true),
         * то контент тоже выравнивается (align) в пределах максимального пространства.
         */
        StyleableTutorialText(
            text = "1-) **Modifier.wrapContentSize** может переопределять минимальные " +
                    "ограничения, приходящие от родителя. " +
                    "В примере **MinimumConstrainedLayout** задаёт " +
                    "минимальную ширину и высоту 500 пикселей. Во втором примере " +
                    "**Modifier.wrapContentSize** заставляет измерять Composable, " +
                    "исходя из размера ребёнка."
        )

        WrapContentSizeSample()

        StyleableTutorialText(
            text = "2-) **Surface** навязывает минимальные Constraints " +
                    "своему прямому потомку. С помощью **Modifier.wrapContentSize** " +
                    "можно задать желаемые размеры."
        )

        WrapWidthInsideSurfaceSample()

        StyleableTutorialText(
            text = "3-) **Modifier.wrapContentSize(unBounded = true)** заставляет " +
                    "учитывать максимальные Constraints по размеру дочернего Composable. " +
                    "Во втором примере **Image** измеряется собственным макс. ограничением."
        )

        // 🔥 Unbounded-контент не изменяет позицию или размеры родителя.
        // Это может привести к некорректному позиционированию, если есть соседние Composable.
        // Родительский Composable размещается, основываясь на своих Constraints, а не на unbounded контенте.
        UnboundedWrapContentSample()

        StyleableTutorialText(
            text = "4-) **Modifier.wrapContentSize(unBounded = true)** можно использовать, " +
                    "чтобы отрисовать изображение целиком, если родитель меньше " +
                    "самого изображения, и при этом не масштабировать (не подгонять) " +
                    "изображение под размер родителя."
        )

        UnBoundedWrapContentImageSample()
    }
}

@Composable
private fun WrapContentSizeSample() {

    TutorialText2(text = "Без wrap Modifier")

    MinimumConstrainedLayout(
        Modifier.border(2.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
        ) {
            Box(modifier = Modifier.size(50.dp))
        }
    }

    Spacer(modifier = Modifier.width(20.dp))

    TutorialText2(text = "Modifier.wrapContentSize()")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MinimumConstrainedLayout(
            Modifier.border(2.dp, Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .background(Color.Red)
            ) {
                Box(modifier = Modifier.size(50.dp))
            }
        }

        MinimumConstrainedLayout(
            Modifier.border(2.dp, Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.BottomStart)
                    .background(Color.Red)
            ) {
                Box(modifier = Modifier.size(50.dp))
            }
        }

        MinimumConstrainedLayout(
            Modifier.border(2.dp, Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.BottomEnd)
                    .background(Color.Red)
            ) {
                Box(modifier = Modifier.size(50.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WrapWidthInsideSurfaceSample() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Surface(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Yellow),
            onClick = {}
        ) {
            Column(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }
        Surface(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Yellow),
            onClick = {}
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }

        Surface(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Yellow),
            onClick = {}
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top)
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }
    }
}

@Composable
private fun UnboundedWrapContentSample() {
    TutorialText2(text = "Modifier.wrapContentSize(unbounded = false)")
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, Color.Green)
    ) {
        Column(
            modifier = Modifier
                .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                .wrapContentSize(unbounded = false)
                .background(Color.Cyan)
                .size(150.dp),
        ) {
            Text(
                text = "Hello world text",
                modifier = Modifier.background(Pink400),
                color = Color.White
            )
        }
    }

    TutorialText2(
        text = "Modifier\n" +
                ".wrapContentSize(unbounded = true)\n" +
                ".size(150.dp)"
    )
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, Color.Green)
    ) {
        Column(
            modifier = Modifier
                .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                .wrapContentSize(unbounded = true)
                .background(Color.Cyan)
                .size(150.dp),
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    modifier = Modifier.background(Pink400),
                    color = Color.White
                )
            }
        }
    }

    TutorialText2(
        text = "Modifier\n" +
                ".size(150.dp)\n" +
                ".wrapContentSize(unbounded = true)"
    )
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, Color.Green)
    ) {
        Column(
            modifier = Modifier
                .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                .size(150.dp)
                .background(Color.Cyan)
                .wrapContentSize(unbounded = true)
        ) {
            BoxWithConstraints {
                Text(
                    text = "Constraints: $constraints",
                    modifier = Modifier.background(Pink400),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun UnBoundedWrapContentImageSample() {

    TutorialText2(text = "Modifier.wrapContentSize(unbounded = false)")
    Box(
        modifier = Modifier
            .size(100.dp)
            .border(2.dp, Color.Green)
    ) {
        Image(
            modifier = Modifier
                .wrapContentSize(unbounded = false)
                .size(150.dp),
            painter = painterResource(id = R.drawable.landscape6),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
    TutorialText2(
        text = "Modifier\n" +
                ".wrapContentSize(unbounded = true)\n" +
                ".size(250.dp)"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .border(2.dp, Color.Green)
    ) {
        Image(
            modifier = Modifier
                .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                .wrapContentSize(unbounded = true)
                .border(2.dp, Color.Cyan)
                .size(250.dp),
            painter = painterResource(id = R.drawable.landscape6),
            contentDescription = null
        )
    }

    TutorialText2(
        text = "Modifier\n" +
                ".size(250.dp)\n" +
                ".wrapContentSize(unbounded = true)"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .border(2.dp, Color.Green)
    ) {
        Image(
            modifier = Modifier
                .size(250.dp)
                .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                .wrapContentSize(unbounded = true)
                .border(2.dp, Color.Cyan),
            painter = painterResource(id = R.drawable.landscape6),
            contentDescription = null
        )
    }
}

@Composable
private fun MinimumConstrainedLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val measurePolicy = MeasurePolicy { measurables, constraints ->
        val placeables = measurables.map { measurable: Measurable ->
            measurable.measure(
                constraints.copy(
                    minWidth = 300,
                    minHeight = 300
                )
            )
        }

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth

        val width = if (hasBoundedWidth && hasFixedWidth) constraints.maxWidth
        else placeables.maxOf { it.width }.coerceIn(constraints.minWidth, constraints.maxWidth)

        val height = placeables.sumOf { it.height }

        var yPos = 0

        layout(width, height) {
            placeables.forEach {
                it.placeRelative(0, yPos)
                yPos += it.height
            }
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = measurePolicy
    )
}
