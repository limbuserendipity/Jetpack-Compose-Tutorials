package com.smarttoolfactory.tutorial1_1basics.chapter1_basics

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader

@Preview(showBackground = true)
@Composable
fun Tutorial1_2Screen() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    LazyColumn(Modifier.fillMaxSize()) {

        item {
            TutorialHeader(text = "Нажатия (Clickable)")
            StyleableTutorialText(
                text = "1-) Добавление свойства clickable к Modifier делает компонент кликабельным." +
                        "\nPadding перед clickable делает **зону клика меньше общей области компонента**."
            )
            ClickableModifierExample()
        }
        item {
            TutorialHeader(text = "Surface (поверхность)")
            StyleableTutorialText(
                text = "2-) Surface позволяет обрезать дочерние элементы в соответствии с выбранной формой."
            )
            SurfaceShapeExample()
        }

        item {
            StyleableTutorialText(
                text = "3-) Surface позволяет задать Z-индекс и рамку для дочерних элементов."
            )
            SurfaceZIndexExample()
        }
        item {
            StyleableTutorialText(
                text = "4-) Surface позволяет задать цвет содержимого для текста и изображений."
            )
            SurfaceContentColorExample()
        }
        item {
            StyleableTutorialText(
                text = "5-) Компоненты могут быть смещены по осям x и y. Surface внутри другой Surface " +
                        "будет обрезан, если выходит за пределы родителя."
            )
            SurfaceClickPropagationExample()
        }
    }
}


/**
 * Добавление модификатора **clickable** к компонентам.
 *
 * * Модификатор weight для [Row] определяет, сколько места займёт дочерний элемент.
 * Это аналогично весу (weight) в **LinearLayout**.
 *
 * * **Padding** после clickable убирает зону padding из кликабельной области. Нажмите на синий
 * прямоугольник, чтобы увидеть зону клика.
 */
@Composable
fun ClickableModifierExample() {

    // Предоставляет контекст для работы в Android
    val context = LocalContext.current

    // Weight в Row работает как вес в LinearLayout с горизонтальной ориентацией
    Row(Modifier.height(120.dp)) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF388E3C))
                .clickable(onClick = {
                    Toast
                        .makeText(context, "Нажато", Toast.LENGTH_SHORT)
                        .show()
                }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "Нажми на меня"
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF1E88E5))
                .padding(15.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(context, "Нажато", Toast.LENGTH_SHORT)
                        .show()
                }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "Нажми на меня"
            )
        }
    }
}


/**
 * Surface (поверхность)
 *
 * Материальная поверхность является центральным элементом в Material Design. Каждая поверхность имеет
 * определённую высоту, которая влияет на её визуальное восприятие относительно других поверхностей и
 * на то, как она отбрасывает тени.
 *
 * [Surface] отвечает за:
 *
 * 1) Обрезку: Surface обрезает дочерние элементы в соответствии с формой, заданной параметром [shape].
 *
 * 2) Высоту (Elevation): Surface поднимает дочерние элементы по оси Z на заданное количество пикселей [elevation],
 * и отрисовывает соответствующую тень.
 *
 * 3) Рамки: Если у формы [shape] есть рамка, она также будет отрисована.
 *
 * 4) Фон: Surface заполняет форму, заданную параметром [shape], цветом [color]. Если [color] равен [Colors.surface],
 * то в тёмной теме будет применено наложение [ElevationOverlay]. Это наложение зависит от [elevation] данной
 * поверхности и [LocalAbsoluteElevation], заданной любыми родительскими поверхностями.
 *
 * 5) Цвет содержимого: Surface использует [contentColor] для задания предпочтительного цвета содержимого
 * данной поверхности — это используется компонентами [Text] и [Icon] в качестве цвета по умолчанию.
 */
@Composable
fun SurfaceShapeExample() {

    Row {

        // Устанавливаем соотношение сторон 1:1, чтобы ширина и высота были равны
        val modifier = Modifier
            .aspectRatio(1f)
            // Weight распределяет ширину между элементами, за исключением области, отведённой под отступы
            .weight(1f)
            .padding(12.dp)

        // Прямоугольная форма
        Surface(
            shape = RectangleShape,
            modifier = modifier,
            color = (Color(0xFFFDD835))
        ) {}

        // Форма с закруглёнными углами
        Surface(
            shape = RoundedCornerShape(5.dp),
            modifier = modifier,
            color = (Color(0xFFF4511E))
        ) {}

        // Круглая форма
        Surface(
            shape = CircleShape,
            modifier = modifier,
            color = (Color(0xFF26C6DA))
        ) {}

        // Форма с обрезанными углами
        Surface(
            shape = CutCornerShape(10.dp),
            modifier = modifier,
            color = (Color(0xFF7E57C2))
        ) {}
    }
}

@Composable
fun SurfaceZIndexExample() {

    Row {

        // Устанавливаем соотношение сторон 1:1, чтобы ширина и высота были равны
        val modifier = Modifier
            .aspectRatio(1f)
            // Weight распределяет ширину между элементами, за исключением области, отведённой под отступы
            .weight(1f)
            .padding(12.dp)

        // Прямоугольная форма
        Surface(
            shape = RectangleShape,
            modifier = modifier,
            color = (Color(0xFFFDD835)),
            elevation = 5.dp,
            border = BorderStroke(5.dp, color = Color(0xFFFF6F00))
        ) {}

        // Форма с закруглёнными углами
        Surface(
            shape = RoundedCornerShape(5.dp),
            modifier = modifier,
            color = (Color(0xFFF4511E)),
            elevation = 8.dp,
            border = BorderStroke(3.dp, color = Color(0xFF6D4C41))
        ) {}

        // Круглая форма
        Surface(
            shape = CircleShape,
            modifier = modifier,
            color = (Color(0xFF26C6DA)),
            elevation = 11.dp,
            border = BorderStroke(2.dp, color = Color(0xFF004D40))
        ) {}

        // Прямоугольная форма с обрезанным верхним левым углом
        Surface(
            shape = CutCornerShape(topStartPercent = 20),
            modifier = modifier,
            color = (Color(0xFFB2FF59)),
            elevation = 15.dp,
            border = BorderStroke(2.dp, color = Color(0xFFd50000))
        ) {}
    }
}

@Composable
fun SurfaceContentColorExample() {
    // Padding на Surface — это отступ для фона и текста. Padding для Text — это отступ между текстом и Surface
    Surface(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(10.dp),
        color = (Color(0xFFFDD835)),
        contentColor = (Color(0xFF26C6DA))
    ) {
        Text(
            text = "Текст внутри Surface использует цвет содержимого Surface по умолчанию.",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun SurfaceClickPropagationExample() {

    // Предоставляет контекст для использования в Android
    val context = LocalContext.current

    // 🔥 Смещение перемещает компонент по осям x и y, значения могут быть положительными или отрицательными
    // 🔥🔥 Если компонент внутри Surface смещён, он будет обрезан.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                Toast
                    .makeText(context, "Нажат Box", Toast.LENGTH_SHORT)
                    .show()
            })
    ) {

        Surface(
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp),
            color = (Color(0xFFFDD835)),
            modifier = Modifier
                .size(150.dp)
                .padding(12.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(
                            context,
                            "Нажат Surface (слева) внутри Box!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                })
        ) {

            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = 50.dp, y = (-20).dp)
                    .clickable(onClick = {
                        Toast
                            .makeText(
                                context,
                                "Нажат Surface внутри Surface!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }),
                elevation = 12.dp,
                color = (Color(0xFF26C6DA))
            ) {}

        }

        Surface(
            shape = CutCornerShape(10.dp),
            modifier = Modifier
                .size(110.dp)
                .padding(12.dp)
                .offset(x = 110.dp, y = 20.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(
                            context,
                            "Нажат Surface (справа) внутри Box!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }),
            color = (Color(0xFFF4511E)),
            elevation = 8.dp
        ) {}
    }
}

@Preview(showBackground = true)
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C, showBackground = true)
@Composable
private fun Tutorial1_2Preview() {
    TutorialContent()
}
