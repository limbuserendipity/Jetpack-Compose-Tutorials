package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldDefaults
import androidx.compose.material.BackdropValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.model.places
import com.smarttoolfactory.tutorial1_1basics.ui.components.PlacesToBookVerticalComponent
import kotlinx.coroutines.launch


/*
    Material Design Backdrop. Этот компонент предоставляет API для
    объединения нескольких компонентов Material, чтобы построить интерфейс экрана.

    Похожий компонент, который
    реализует базовую структуру Material Design с AppBar, плавающими
    кнопками действия (FAB) и Navigation Drawer, — это стандартный Scaffold.

    Похожий компонент, который использует нижний лист (Bottom Sheet) в качестве
    центрального элемента экрана, — это BottomSheetScaffold.
 */
/**
 * [Backdrop](https://material.io/components/backdrop#behavior)
 *
 * ```backdropScaffoldState.conceal()``` — используется, чтобы скрыть,
 * а ```backdropScaffoldState.reveal()``` — чтобы показать нижний контент,
 * который является **frontLayerContent**.
 *
 * ```headerHeight``` можно использовать для задания **высоты контента фронт-слоя**,
 * пока он скрыт. ```peekHeight``` задаёт **общую высоту для back layer**, начиная
 * от нижней части appBar.
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun Tutorial2_10Screen5() {
    TutorialContent()
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Preview
@Composable
private fun TutorialContentPreview(
    @PreviewParameter(BackdropValueProvider::class)
    initialBackdropValue: BackdropValue
) {
    TutorialContent(initialBackdropValue)
}

@OptIn(ExperimentalMaterialApi::class)
private class BackdropValueProvider : PreviewParameterProvider<BackdropValue> {
    override val values: Sequence<BackdropValue>
        get() = sequenceOf(
            BackdropValue.Concealed,
            BackdropValue.Revealed
        )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun TutorialContent(initialBackdropValue: BackdropValue = BackdropValue.Revealed) {

    val backdropScaffoldState =
        rememberBackdropScaffoldState(initialValue = initialBackdropValue)
    val coroutineScope = rememberCoroutineScope()

    BackdropScaffold(
        appBar = {
            TopAppBar(
                elevation = 8.dp,
                title = {
                    Text("BackdropScaffold")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (backdropScaffoldState.isRevealed) {
                            coroutineScope.launch { backdropScaffoldState.conceal() }
                        } else if (backdropScaffoldState.isConcealed) {
                            coroutineScope.launch { backdropScaffoldState.reveal() }
                        }
                    }) {
                        Icon(Icons.Default.Menu, null)
                    }
                },
            )
        },
        scaffoldState = backdropScaffoldState,
        // Свойства back layer
        peekHeight = BackdropScaffoldDefaults.PeekHeight,
        persistentAppBar = true,
//        backLayerBackgroundColor = MaterialTheme.colors.primary,
        backLayerContent = {
            BackLayerContent()
        },
        // Свойства front layer
        stickyFrontLayer = true,
        headerHeight = BackdropScaffoldDefaults.HeaderHeight,
        frontLayerShape = BackdropScaffoldDefaults.frontLayerShape,
        frontLayerElevation = BackdropScaffoldDefaults.FrontLayerElevation,
        // 🔥 Удаляет полупрозрачный белый цвет при состоянии backdropScaffoldState = concealed
        frontLayerScrimColor = Color.Unspecified,
        frontLayerContent = {
            FrontLayerContent()
        }
    )
}

@Composable
private fun BackLayerContent() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(16.dp))
        BackLayerTextField("Поиск", "Поиск...", Icons.Default.Search)
        Spacer(Modifier.height(16.dp))
        BackLayerTextField("Дата", "Дата...", Icons.Default.DateRange)
        Spacer(Modifier.height(16.dp))
        BackLayerTextField("Место", "Место...", Icons.Default.Place)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun FrontLayerContent() {
    Column {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "Подзаголовок",
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(Color.LightGray)
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(places) { place ->
                PlacesToBookVerticalComponent(place = place)
            }
        }
    }
}

@Composable
private fun BackLayerTextField(
    label: String,
    placeHolder: String,
    imageVector: ImageVector
) {
    var textFieldValue by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        value = textFieldValue,
        label = { Text(label) },
        placeholder = { Text(placeHolder) },
        onValueChange = { newValue ->
            textFieldValue = newValue
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xffD1C4E9),
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colors.primary
        ),
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = null)
        }
    )
}
