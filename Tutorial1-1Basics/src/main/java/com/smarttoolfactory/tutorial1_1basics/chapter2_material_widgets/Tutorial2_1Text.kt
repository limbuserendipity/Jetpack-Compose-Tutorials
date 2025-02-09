package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

@Preview(showBackground = true)
@Composable
fun Tutorial2_1Screen() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    LazyColumn(Modifier.fillMaxSize()) {

        item {
            TutorialHeader(text = "Текст")

            TutorialText2(text = "Цвет шрифта")
            TextSampleRow {
                CustomText(text = "Красный 700", color = Color(0xffd32f2f))
                CustomText(text = "Фиолетовый 700", color = Color(0xff7B1FA2))
                CustomText(text = "Зелёный 700", color = Color(0xff1976D2))
                CustomText(text = "Бирюзовый 700", color = Color(0xff00796B))
            }

            // Примеры свойств, связанных с шрифтом
            TextFontExample()

            TutorialText2(text = "Межбуквенное расстояние")
            TextSampleRow {
                CustomText(text = " LS:0.4sp", letterSpacing = 0.4.sp)
                CustomText(text = "LS:1sp", letterSpacing = 1.sp)
                CustomText(text = "LS:2sp", letterSpacing = 2.sp)
                CustomText(text = "LS:4sp", letterSpacing = 4.sp)
            }

            TutorialText2(text = "Украшение текста")
            TextSampleRow {
                CustomText(text = "Подчёркнутый", textDecoration = TextDecoration.Underline)
                CustomText(text = "Зачёркнутый", textDecoration = TextDecoration.LineThrough)
                CustomText(
                    text = "Подчёркнутый+Зачёркнутый", textDecoration = TextDecoration.combine(
                        listOf(
                            TextDecoration.Underline,
                            TextDecoration.LineThrough
                        )
                    )
                )
            }

            TutorialText2(text = "Высота строки")
            CustomText(
                text = "Этот текст имеет высоту строки 15 sp. Высота строки задаётся в единицах TextUnit, например SP или EM.",
                lineHeight = 15.sp
            )

            Divider(modifier = Modifier.padding(4.dp))
            CustomText(
                text = "Этот текст имеет высоту строки 20 sp. Высота строки задаётся в единицах TextUnit, например SP или EM.",
                lineHeight = 20.sp
            )
            Divider(modifier = Modifier.padding(4.dp))
            CustomText(
                text = "Этот текст имеет высоту строки 25 sp. Высота строки задаётся в единицах TextUnit, например SP или EM.",
                lineHeight = 25.sp
            )
            Divider(modifier = Modifier.padding(4.dp))
            CustomText(
                text = "Этот текст имеет высоту строки 30 sp. Высота строки задаётся в единицах TextUnit, например SP или EM.",
                lineHeight = 30.sp
            )
            Divider(modifier = Modifier.padding(4.dp))

            TutorialText2(text = "Переполнение")
            CustomText(
                text = "Обрезать текст, выходящий за пределы контейнера. " +
                        "Если текст превышает указанное количество строк, он будет усечён в соответствии с " +
                        " overflow и softWrap.",
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            CustomText(
                text = "Использовать троеточие для обозначения переполнения текста. " +
                        "Если текст превышает указанное количество строк, он будет усечён в соответствии с " +
                        " overflow и softWrap.",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            // Примеры с фоном и рамкой текста
            TextBackgroundAndBorderExample()

            // Пример тени текста
            TextShadowExample()

            // Пример Spannable текста
            SpannableTextExample()

            // Пример подстрочного и надстрочного текста
            SubscriptSuperscriptTextExample()

            SelectableTextExample()
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}

// Пример настройки шрифта текста
@Composable
private fun TextFontExample() {
    TutorialText2(text = "Размер шрифта")
    TextSampleRow {
        CustomText(text = "14sp", fontSize = 14.sp)
        CustomText(text = "18sp", fontSize = 18.sp)
        CustomText(text = "30sp", fontSize = 30.sp)
        CustomText(text = "40sp", fontSize = 40.sp)
    }

    TutorialText2(text = "Стиль шрифта")
    TextSampleRow {
        CustomText(text = "Обычный", fontStyle = FontStyle.Normal)
        CustomText(text = "Курсив", fontStyle = FontStyle.Italic)
    }

    TutorialText2(text = "Толщина шрифта")
    TextSampleRow {
        CustomText(text = "Тонкий", fontWeight = FontWeight.Thin)
        CustomText(text = "Очень лёгкий", fontWeight = FontWeight.ExtraLight)
        CustomText(text = "Лёгкий", fontWeight = FontWeight.Light)
        CustomText(text = "Нормальный", fontWeight = FontWeight.Normal)
        CustomText(text = "Средний", fontWeight = FontWeight.Medium)
    }
    TextSampleRow {
        CustomText(text = "Полужирный", fontWeight = FontWeight.SemiBold)
        CustomText(text = "Жирный", fontWeight = FontWeight.Bold)
        CustomText(text = "Очень жирный", fontWeight = FontWeight.ExtraBold)
        CustomText(text = "Чёрный", fontWeight = FontWeight.Black)
    }

    TutorialText2(text = "Семейство шрифтов")
    TextSampleRow {
        CustomText(text = "По умолчанию", fontFamily = FontFamily.Default)
        CustomText(text = "Курсив", fontFamily = FontFamily.Cursive)
        CustomText(text = "Моноширинный", fontFamily = FontFamily.Monospace)
        CustomText(text = "Без засечек", fontFamily = FontFamily.SansSerif)
        CustomText(text = "С засечками", fontFamily = FontFamily.Serif)
    }
}

/**
 * Отображение [Text] с фоном или рамкой, выполненными сплошным или градиентным цветом
 */
@Composable
private fun TextBackgroundAndBorderExample() {
    TutorialText2(text = "Фон")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        val horizontalGradientBrush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xffF57F17),
                Color(0xffFFEE58),
                Color(0xffFFF9C4)
            )
        )

        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "Оранжевый 500",
            modifier = Modifier
                .background(Color(0xffFF9800), shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
        )
        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "Бирюзовый 500",
            modifier = Modifier
                .background(Color(0xff00BCD4), shape = CutCornerShape(topStartPercent = 25))
                .padding(20.dp)
        )
        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "Градиент",
            modifier = Modifier
                .background(brush = horizontalGradientBrush)
                .padding(20.dp)
        )
    }

    TutorialText2(text = "Рамка")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        val verticalGradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xff4E342E),
                Color(0xff8D6E63),
                Color(0xffD7CCC8)
            )
        )

        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "Оранжевый 500",
            modifier = Modifier
                .border(width = 4.dp, Color(0xffFF9800), shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
        )
        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "Бирюзовый 500",
            modifier = Modifier
                .border(
                    width = 4.dp,
                    Color(0xff00BCD4),
                    shape = CutCornerShape(topStartPercent = 25)
                )
                .padding(20.dp)
        )
        CustomText(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "Градиент",
            modifier = Modifier
                .border(width = 4.dp, brush = verticalGradientBrush, shape = RectangleShape)
                .padding(20.dp)
        )
    }
}

@Composable
private fun TextShadowExample() {

    TutorialText2(text = "Тень")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = "Тень1",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Gray, blurRadius = 5f,
                    offset = Offset(5f, 5f)
                )
            )
        )
        Text(
            text = "Тень2",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black, blurRadius = 10f,
                    offset = Offset(-5f, -5f)
                )
            )
        )
        Text(
            text = "Тень3",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Red, blurRadius = 20f,
                    offset = Offset(5f, 5f)
                )
            )
        )
    }
}

@Composable
private fun SpannableTextExample(modifier: Modifier = Modifier) {

    TutorialText2(text = "Многоцветный текст")

    val annotatedColorString = buildAnnotatedString {
        append("КрасныйЗелёныйСиний")
        addStyle(style = SpanStyle(color = Color.Red, fontSize = 24.sp), start = 0, end = 7)
        addStyle(
            style = SpanStyle(
                color = Color.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ), start = 7, end = 14
        )
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontSize = 26.sp,
                textDecoration = TextDecoration.Underline
            ),
            start = 14,
            end = this.length
        )
    }

    Text(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = annotatedColorString
    )

    TutorialText2(text = "Кликабельный текст")

    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        val str = "Нажмите на эту ссылку, чтобы перейти на сайт"
        val startIndex = str.indexOf("ссылку")
        val endIndex = startIndex + 6
        append(str)

        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        // Привязать аннотацию URL к тексту "ссылка".
        addStringAnnotation(
            tag = "URL",
            annotation = "https://github.com/SmartToolFactory",
            start = startIndex,
            end = endIndex
        )

    }

    // UriHandler парсит и открывает URI внутри AnnotatedString
    val uriHandler: UriHandler = LocalUriHandler.current

    // 🔥 Кликабельный текст возвращает позицию, на которую кликнули, в обратном вызове onClick
    ClickableText(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations(it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    println("🔥 Кликнули: $it, элемент: ${stringAnnotation.item}")
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}
@Composable
private fun SubscriptSuperscriptTextExample() {

    TutorialText2(text = "Подстрочный и надстрочный текст")
    // создаём переменную superScript
    // задаём baselineShift как
    // BaselineShift.Superscript для надстрочного текста
    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 14.sp, // размер шрифта для надстрочного текста
        color = Color.Red // цвет текста
    )

    // создаём переменную subScript
    // задаём baselineShift как
    // BaselineShift.Subscript для подстрочного текста
    val subscript = SpanStyle(
        baselineShift = BaselineShift.Subscript,
        fontSize = 14.sp, // размер шрифта для подстрочного текста
        color = Color.Blue // цвет текста
    )

    // создаём первый текст
    Text(
        modifier = Modifier.padding(16.dp),
        fontSize = 20.sp,
        text = buildAnnotatedString {
            // вместо прямой передачи строкового значения
            // используем метод append
            append("E = mc")
            withStyle(superscript) {
                append("2")
            }
        }
    )

    // создаём второй текст
    Text(
        modifier = Modifier.padding(16.dp),
        fontSize = 20.sp,
        text = buildAnnotatedString {
            // вместо прямой передачи строкового значения
            // используем метод append
            append("CH")
            withStyle(subscript) {
                append("4")
            }
            append(" + H")
            withStyle(subscript) {
                append("2")
            }
            append("O = CO + 3H")
            withStyle(subscript) {
                append("2")
            }
        }
    )
}

@Composable
private fun SelectableTextExample() {
    TutorialText2(text = "Выделяемый текст")
    SelectionContainer {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Lorem Ipsum — это текст-рыба, который используется в печати и веб-дизайне. " +
                    "Этот текст стал стандартной \"рыбой\" ещё в XVI веке, " +
                    "когда неизвестный печатник взял шрифтовую гранку и составил из неё " +
                    "образец шрифта."
        )
    }
}

@Composable
private fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {

    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style
    )
}

/**
 * Пример строки для отображения компонентов [Text] с различными функциями рядом
 */
@Composable
fun TextSampleRow(content: @Composable () -> Unit) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)

    Row(
        modifier = rowModifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        content()
    }
}
