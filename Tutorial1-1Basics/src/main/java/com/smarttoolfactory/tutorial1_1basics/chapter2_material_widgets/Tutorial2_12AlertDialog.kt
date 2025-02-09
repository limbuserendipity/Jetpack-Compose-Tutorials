package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.smarttoolfactory.tutorial1_1basics.R
import com.smarttoolfactory.tutorial1_1basics.chapter6_graphics.ColorWheel
import com.smarttoolfactory.tutorial1_1basics.isInPreview
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

@Preview(showBackground = true)
@Composable
fun Tutorial2_12Screen() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    // Чтобы отобразить диалог, мы устанавливаем один из флагов в true
    // и переключаем его обратно в false в onDismissRequest диалога.
    var showAlertDialog by remember { mutableStateOf(false) }
    var showAlertDialogWithStyle by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isInPreview = isInPreview

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {

            TutorialHeader(text = "AlertDialog")
            StyleableTutorialText(
                text = "1-) Диалоги Alert прерывают работу пользователя срочной информацией, деталями или действиями."
            )

            OutlinedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    showAlertDialog = !showAlertDialog
                }
            ) {
                Text("AlertDialog")

                if (showAlertDialog) {
                    AlertDialogExample {
                        showAlertDialog = !showAlertDialog
                    }
                }
            }
        }

        item {
            OutlinedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    showAlertDialogWithStyle = !showAlertDialogWithStyle
                }
            ) {
                Text("AlertDialog со стилем")

                if (showAlertDialogWithStyle) {
                    AlertDialogExample2 {
                        showAlertDialogWithStyle = !showAlertDialogWithStyle
                    }
                }
            }
        }

        item {

            TutorialHeader(text = "Dialog")
            StyleableTutorialText(
                text = "2-) В отличие от **AlertDialog**, **Dialog** не имеет слотов для " +
                        "**dismissButton, confirmButton, или кнопок**. " +
                        "Это позволяет полностью настраивать содержимое диалога."
            )
            OutlinedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    showDialog = !showDialog
                }
            ) {
                Text("Dialog")

                if (showDialog) {
                    DialogExample {
                        showDialog = !showDialog
                    }
                }
            }

            TutorialText2(text = "Пользовательский диалог")

            OutlinedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    showCustomDialog = !showCustomDialog
                }
            ) {
                Text("Пользовательский диалог")

                if (showCustomDialog) {
                    CustomDialogExample(
                        onDismiss = {
                            showCustomDialog = !showCustomDialog
                            if (!isInPreview) {
                                Toast.makeText(context, "Диалог закрыт!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        onNegativeClick = {
                            showCustomDialog = !showCustomDialog
                            if (!isInPreview) {
                                Toast.makeText(
                                    context,
                                    "Нажата негативная кнопка!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        onPositiveClick = {
                            showCustomDialog = !showCustomDialog
                            if (!isInPreview) {
                                Toast.makeText(
                                    context,
                                    "Нажата позитивная кнопка!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    )
                }
            }
        }

        item {
            TutorialText2(text = "Пользовательский диалог с результатом")
            OutlinedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                }
            ) {
                Text("Пользовательский диалог с результатом")

                if (showCustomDialogWithResult) {
                    CustomDialogWithResultExample(
                        initialColor = Color.Black,
                        onDismiss = {
                            showCustomDialogWithResult = !showCustomDialogWithResult
                            if (!isInPreview) {
                                Toast.makeText(context, "Диалог закрыт!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        onNegativeClick = {
                            showCustomDialogWithResult = !showCustomDialogWithResult
                            if (!isInPreview) {
                                Toast.makeText(
                                    context,
                                    "Нажата негативная кнопка!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        onPositiveClick = { color ->
                            showCustomDialogWithResult = !showCustomDialogWithResult
                            if (!isInPreview) {
                                Toast.makeText(
                                    context,
                                    "Выбран цвет: $color",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AlertDialogExample(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Отмена")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Заголовок AlertDialog", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = dialogText)
        }
    )
}

@Composable
private fun AlertDialogExample2(onDismiss: () -> Unit) {
    // В этом примере используется button Composable для создания кнопок, вместо confirmButton и dismissButton

    AlertDialog(
        onDismissRequest = onDismiss,
        // Настройки, используемые для кастомизации поведения диалога
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            securePolicy = SecureFlagPolicy.Inherit
        ),
        title = {
            Text("AlertDialog со стилем", fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = "У этого диалога кнопки с пользовательским стилем, расположенные вертикально, как в Column. Свойства задают пользовательское поведение диалога, например закрытие при нажатии кнопки назад или вне диалога.")
        },
        buttons = {
            OutlinedButton(
                shape = RoundedCornerShape(percent = 30),
                onClick = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Отмена")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                shape = RoundedCornerShape(percent = 30),
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color(0xff8BC34A),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "OK")
            }
        }
    )
}

@Composable
private fun DialogExample(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) {
            Column(
                modifier = Modifier
                    .width(400.dp)
                    .wrapContentHeight()
                    .background(Color.White)
                    .padding(8.dp)
            ) {

                Text(
                    text = "Заголовок диалога",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(dialogText, modifier = Modifier.padding(8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
                DialogButtons(onDismiss)
            }
        }
    }
}

@Composable
private fun CustomDialogExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        val color = Color(0xff4DB6AC)
        Card(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) {

            Column {

                // Шапка
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color)
                ) {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.ic_baseline_location_on_48),
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer(scaleX = 1.2f, scaleY = 1.2f)
                            .align(Alignment.Center)
                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text("Чтобы отправить место поблизости или свою геолокацию, разрешите доступ к вашим координатам.")
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "НЕ СЕЙЧАС", color = color,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(color = Color.DarkGray),
                                    onClick = onNegativeClick
                                )
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ПРОДОЛЖИТЬ", color = color,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(color = Color.DarkGray),
                                    onClick = onPositiveClick
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDialogWithResultExample(
    initialColor: Color,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit
) {
    var red by remember { mutableStateOf(initialColor.red * 255) }
    var green by remember { mutableStateOf(initialColor.green * 255) }
    var blue by remember { mutableStateOf(initialColor.blue * 255) }

    val color = Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = 255
    )

    Dialog(onDismissRequest = onDismiss) {

        BoxWithConstraints(
            Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {

            val widthInDp = LocalDensity.current.run { maxWidth }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Цвет",
                    color = Blue400,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Начальный и текущий цвета
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                initialColor,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                    )
                }

                ColorWheel(
                    modifier = Modifier
                        .width(widthInDp * .8f)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Слайдеры
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Red",
                    titleColor = Color.Red,
                    rgb = red,
                    onColorChanged = {
                        red = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Green",
                    titleColor = Color.Green,
                    rgb = green,
                    onColorChanged = {
                        green = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Blue",
                    titleColor = Color.Blue,
                    rgb = blue,
                    onColorChanged = {
                        blue = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Кнопки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xffF3E5F5)),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextButton(
                        onClick = onNegativeClick,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(text = "ОТМЕНА")
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onPositiveClick(color)
                        },
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButtons(onDismiss: () -> Unit) {
    Row {
        TextButton(
            onClick = onDismiss,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Отмена")
        }
        TextButton(
            onClick = onDismiss,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "OK")
        }
    }
}

val dialogText = """
    Lorem Ipsum — это текст-«рыба», часто используемый в печати и вэб-дизайне. 
    Lorem Ipsum является стандартной «рыбой» для текстов на латинице с начала XVI века, когда 
    неизвестный печатник взял шрифт и скомбинировал отдельные буквы так, чтобы получить макет текста.
""".trimIndent()

@Preview
@Composable
fun AlertDialogExamplePreview() {
    AlertDialogExample(onDismiss = { })
}

@Preview
@Composable
fun AlertDialogExample2Preview() {
    AlertDialogExample2(onDismiss = { })
}

@Preview
@Composable
fun DialogExamplePreview() {
    DialogExample(onDismiss = { })
}

@Preview
@Composable
fun CustomDialogExamplePreview() {
    CustomDialogExample(onDismiss = {}, onNegativeClick = {}, onPositiveClick = {})
}

@Preview
@Composable
fun CustomDialogWithResultExamplePreview() {
    CustomDialogWithResultExample(
        initialColor = Color.Red,
        onDismiss = {},
        onNegativeClick = {},
        onPositiveClick = {})
}
