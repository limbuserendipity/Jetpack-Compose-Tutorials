package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.tutorial1_1basics.isInPreview
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun Tutorial2_11Screen() {
    TutorialContent()
}

@ExperimentalMaterialApi
@Composable
private fun TutorialContent() {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
    ) {
        SnackBarExample()
        ProgressIndicatorExample()
        CheckboxExample()
        SwitchExample()
        RadioButtonExample()
        SliderExample()
    }
}

@Composable
private fun SnackBarExample() {
    TutorialHeader(text = "SnackBar")
    StyleableTutorialText(
        text = "1-) **Snackbar** предоставляет краткие сообщения о процессах приложения в нижней части экрана."
    )

    TutorialText2(text = "Обычный SnackBar")
    Snackbar(modifier = Modifier.padding(4.dp)) {
        Text("Простой Snackbar")
    }

    TutorialText2(text = "Action SnackBar")
    val context = LocalContext.current
    val isInPreview = isInPreview
    Snackbar(
        modifier = Modifier.padding(4.dp),
        action = {
            Text(
                text = "Действие",
                modifier = Modifier.clickable {
                    if (!isInPreview) {
                        Toast.makeText(context, "Action is clicked", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    ) {
        Text("Action Snackbar")
    }

    TutorialText2(text = "actionOnNewLine SnackBar")
    Snackbar(
        modifier = Modifier.padding(4.dp),
        actionOnNewLine = true,
        action = {
            Text(
                text = "Действие",
                color = Color(0xffCE93D8),
                modifier = Modifier.clickable {
                    if (!isInPreview) {
                        Toast.makeText(context, "Action is clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    ) {
        Text("Action on new line Snackbar")
    }

    TutorialText2(text = "Snackbar Cтиль")
    Snackbar(
        modifier = Modifier.padding(4.dp),
        shape = CutCornerShape(topStart = 8.dp),
        elevation = 2.dp,
//            backgroundColor = SnackbarDefaults.backgroundColor,
        backgroundColor = Color(0xffFFC107),
//            contentColor = MaterialTheme.colors.surface,
        contentColor = Color(0xffEC407A),
        action = {
            Text(
                text = "Действие",
                modifier = Modifier.clickable {
                    if (!isInPreview) {
                        Toast.makeText(context, "Action is clicked", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    ) {
        Text("Snackbar с пользовательской формой и цветами")
    }

    Snackbar(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        elevation = 1.dp,
        backgroundColor = Color(0xff4CAF50),
        contentColor = Color(0xffFFFF00),
        action = {
            Text(
                text = "Действие",
                color = Color(0xffD32F2F),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    if (!isInPreview) {
                        Toast.makeText(context, "Action is clicked", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    ) {
        Text("Snackbar с пользовательской формой и цветами")
    }
}

@Composable
private fun ProgressIndicatorExample() {
    TutorialHeader(text = "ProgressIndicator")
    StyleableTutorialText(
        text = "2-) Индикаторы прогресса отображают неопределённое время ожидания или показывают, сколько осталось до завершения процесса."
    )

    TutorialText2("Неопределённый прогресс")
    CircularProgressIndicator()
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator()
    Spacer(modifier = Modifier.height(8.dp))

    TutorialText2("Определённый прогресс")
    val progress: Int by progressFlow.collectAsState(initial = 0)
    CircularProgressIndicator(
        progress = progress / 100f,
        strokeWidth = 4.dp,
        color = Color(0xffF44336)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        progress = progress / 100f,
        backgroundColor = Color(0xff2196F3)
    )

    TutorialText2("Анимированный прогресс")
    var progressAnimated by remember { mutableStateOf(0.1f) }

    val animatedProgress by animateFloatAsState(
        targetValue = progressAnimated,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            onClick = {
                if (progressAnimated < 1f) progressAnimated += 0.1f
            }
        ) {
            Text("Увеличить")
        }
        Spacer(Modifier.requiredWidth(30.dp))
        LinearProgressIndicator(progress = animatedProgress)
    }
}

@Composable
private fun CheckboxExample() {
    TutorialHeader(text = "Checkbox")
    StyleableTutorialText(
        text = "2-) Индикаторы прогресса отображают неопределённое время ожидания или показывают, " +
                "сколько осталось до завершения процесса. **TriStateCheckbox** можно использовать для управления дочерними checkbox."
    )

    TutorialText2("Checkbox")
    var checkBoxState by remember { mutableStateOf(false) }
    Checkbox(
        modifier = Modifier.padding(8.dp),
        checked = checkBoxState,
        onCheckedChange = {
            checkBoxState = it
        })
    Spacer(modifier = Modifier.height(8.dp))

    var checkBoxState2 by remember { mutableStateOf(false) }

    CheckBoxWithText("Checkbox с текстом", checkBoxState2) {
        checkBoxState2 = it
    }

    var checkBoxState3 by remember { mutableStateOf(false) }

    CheckBoxWithTextRippleFullRow("Checkbox с текстом и ripple", checkBoxState3) {
        checkBoxState3 = it
    }

    TutorialText2("TriStateCheckbox")

    // Родительский и дочерние checkbox с TriStateCheckbox
    Column(modifier = Modifier.padding(8.dp)) {
        // определяем состояния дочерних checkbox
        val (state, onStateChange) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(true) }

        // Состояние TriStateCheckbox отражает состояние дочерних checkbox
        val parentState = remember(state, state2) {
            if (state && state2) ToggleableState.On
            else if (!state && !state2) ToggleableState.Off
            else ToggleableState.Indeterminate
        }
        // Нажатие на TriStateCheckbox может установить состояние для дочерних checkbox
        val onParentClick = {
            val s = parentState != ToggleableState.On
            onStateChange(s)
            onStateChange2(s)
        }

        Spacer(modifier = Modifier.width(16.dp))
        Row {
            // 🔥 TriState
            TriStateCheckbox(
                state = parentState,
                onClick = onParentClick,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary
                )
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Дополнения")
        }
        Spacer(Modifier.height(8.dp))
        Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            CheckBoxWithText(label = "Огурцы", state = state, onStateChange = onStateChange)
            Spacer(Modifier.height(8.dp))
            CheckBoxWithText(label = "Помидор", state = state2, onStateChange = onStateChange2)
        }
    }
}

@Composable
private fun SwitchExample() {

    TutorialHeader(text = "Switch")
    StyleableTutorialText(
        text = "3-) **Switch** переключает состояние одного элемента, включая или выключая его. " +
                "**enabled** устанавливается в false для элементов в правой части."
    )

    val switchColors = SwitchDefaults.colors(
        checkedThumbColor = Color(0xffF44336),
        checkedTrackColor = Color(0xff76FF03),
        checkedTrackAlpha = 0.54f,
        uncheckedThumbColor = Color(0xff9C27B0),
        uncheckedTrackColor = Color(0xff3F51B5),
        uncheckedTrackAlpha = 0.38f,
        disabledCheckedThumbColor = Color(0xff212121),
        disabledCheckedTrackColor = Color(0xff616161),
        disabledUncheckedThumbColor = Color(0xff607D8B),
        disabledUncheckedTrackColor = Color(0xff795548)
    )

    var isSwitched by remember { mutableStateOf(true) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Switch(checked = isSwitched, onCheckedChange = { isSwitched = it })
        Switch(
            checked = isSwitched,
            onCheckedChange = { isSwitched = it },
            colors = switchColors
        )
        Switch(
            enabled = false,
            colors = switchColors,
            checked = false,
            onCheckedChange = { isSwitched = it },
        )

        Switch(
            enabled = false,
            colors = switchColors,
            checked = true,
            onCheckedChange = { isSwitched = it },
        )
    }
}

@Composable
private fun RadioButtonExample() {

    TutorialHeader(text = "RadioButton")
    StyleableTutorialText(
        text = "4-) **RadioButton** позволяет пользователям выбрать один вариант из набора."
    )

    var isRadioSelected by remember { mutableStateOf(true) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        // Включённые RadioButton
        RadioButton(selected = isRadioSelected, onClick = { isRadioSelected = !isRadioSelected })
        RadioButton(
            selected = isRadioSelected,
            onClick = { isRadioSelected = !isRadioSelected },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xffE91E63),
                unselectedColor = Color(0xffFFEB3B),
                disabledColor = Color(0xff607D8B)
            )
        )

        // Выключенные RadioButton
        RadioButton(
            enabled = false,
            selected = false,
            onClick = {},
            colors = RadioButtonDefaults.colors(
                disabledColor = Color(0xff607D8B)
            )
        )

        RadioButton(
            enabled = false,
            selected = true,
            onClick = {},
            colors = RadioButtonDefaults.colors(
                disabledColor = Color(0xff607D8B)
            )
        )
    }

    TutorialText2("Группа RadioButton")

    Spacer(Modifier.height(8.dp))

    // У нас два radio button, и только один может быть выбран
    var state by remember { mutableStateOf(true) }
    // Modifier.selectableGroup() важен для корректной доступности
    Row(
        Modifier
            .selectableGroup()
            .padding(8.dp)
    ) {
        RadioButton(
            selected = state,
            onClick = { state = true }
        )
        Spacer(modifier = Modifier.width(24.dp))
        RadioButton(
            selected = !state,
            onClick = { state = false }
        )
    }

    TutorialText2("Группа RadioButton с текстом")

    val radioOptions = listOf("Звонки", "Пропущенные", "Друзья")

    val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
        mutableStateOf(
            radioOptions[0]
        )
    }
    // Modifier.selectableGroup() важен для корректной доступности
    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SliderExample() {

    TutorialHeader(text = "Slider")
    StyleableTutorialText(
        text = "5-) **Slider** отражает диапазон значений, из которых пользователь может выбрать одно. " +
                "Он идеально подходит для регулировки таких параметров, как громкость, яркость или фильтры изображений."
    )
    TutorialText2("Slider")

    val colors = SliderDefaults.colors(
        thumbColor = Color(0xffF44336),
        disabledThumbColor = Color(0xff795548),
        activeTrackColor = Color(0xff009688),
        inactiveTrackColor = Color(0xffFFEA00),
        disabledActiveTrackColor = Color(0xffFF9800),
        disabledInactiveTrackColor = Color(0xff616161),
        activeTickColor = Color(0xff673AB7),
        inactiveTickColor = Color(0xff2196F3),
        disabledActiveTickColor = Color(0xffE0E0E0),
        disabledInactiveTickColor = Color(0xff607D8B)
    )

    var sliderPosition by remember { mutableStateOf(0f) }
    Spacer(Modifier.height(8.dp))
    Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
    Spacer(Modifier.height(8.dp))

    var sliderPosition2 by remember { mutableStateOf(.3f) }
    Slider(value = sliderPosition2, onValueChange = { sliderPosition2 = it }, colors = colors)
    Spacer(Modifier.height(8.dp))

    var sliderPosition3 by remember { mutableStateOf(.4f) }
    Slider(
        value = sliderPosition3,
        onValueChange = { sliderPosition3 = it },
        enabled = false,
        colors = colors
    )
    Spacer(Modifier.height(8.dp))

    var sliderPosition4 by remember { mutableStateOf(26f) }
    Text(text = sliderPosition4.toString())
    Slider(
        value = sliderPosition4,
        onValueChange = { sliderPosition4 = it },
        valueRange = 0f..100f,
        onValueChangeFinished = {

        },
        steps = 10,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.secondary,
            activeTrackColor = MaterialTheme.colors.secondary
        )
    )

    TutorialText2("RangeSlider")
    var sliderPosition5 by remember { mutableStateOf(.1f..(.3f)) }

    RangeSlider(
        value = sliderPosition5,
        onValueChange = {
            sliderPosition5 = it
        },
        colors = colors
    )
}

/**
 * Компонент, показывающий заголовок в виде первой буквы, цвет заголовка и Slider для выбора цвета
 */
@Composable
fun ColorSlider(
    modifier: Modifier,
    title: String,
    titleColor: Color,
    valueRange: ClosedFloatingPointRange<Float> = 0f..255f,
    rgb: Float,
    onColorChanged: (Float) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {

        Text(text = title.substring(0,1), color = titleColor, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Slider(
            modifier = Modifier.weight(1f),
            value = rgb,
            onValueChange = { onColorChanged(it) },
            valueRange = valueRange,
            onValueChangeFinished = {}
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = rgb.toInt().toString(),
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.width(30.dp)
        )
    }
}

@Composable
private fun CheckBoxWithText(label: String, state: Boolean, onStateChange: (Boolean) -> Unit) {

    // Checkbox с текстом справа
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                interactionSource = interactionSource,
                // Убираем ripple при клике на Row
                indication = null,
                role = Role.Checkbox,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = state,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = label)
    }
}

@Composable
fun CheckBoxWithTextRippleFullRow(
    label: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit
) {

    // Checkbox с текстом справа
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                role = Role.Checkbox,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = state,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

private val progressFlow by lazy {
    flow {
        repeat(100) {
            emit(it + 1)
            delay(50)
        }
    }
}
