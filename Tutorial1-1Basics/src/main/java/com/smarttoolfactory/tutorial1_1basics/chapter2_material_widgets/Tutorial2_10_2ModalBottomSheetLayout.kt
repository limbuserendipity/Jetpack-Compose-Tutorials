package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.tutorial1_1basics.R
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun Tutorial2_10Screen2() {
    TutorialContent()
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Preview
@Composable
private fun TutorialContentPreview(
    @PreviewParameter(ModalBottomSheetValueProvider::class)
    initialModalBottomSheetValue: ModalBottomSheetValue
) {
    TutorialContent(initialModalBottomSheetValue)
}

@OptIn(ExperimentalMaterialApi::class)
class ModalBottomSheetValueProvider : PreviewParameterProvider<ModalBottomSheetValue> {
    override val values: Sequence<ModalBottomSheetValue>
        get() = sequenceOf(
            ModalBottomSheetValue.Hidden,
            ModalBottomSheetValue.HalfExpanded,
            ModalBottomSheetValue.Expanded
        )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun TutorialContent(initialModalBottomSheetValue: ModalBottomSheetValue = ModalBottomSheetValue.HalfExpanded) {

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = initialModalBottomSheetValue,
        skipHalfExpanded = false,
        confirmValueChange = { _: ModalBottomSheetValue ->
            true
        }
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 8.dp,
                title = {
                    Text("Модальное BottomSheet")
                },
                actions = {
                    IconButton(onClick = {
                        if (modalBottomSheetState.isVisible) {
                            coroutineScope.launch { modalBottomSheetState.hide() }
                        } else {
                            coroutineScope.launch { modalBottomSheetState.show() }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Expand, contentDescription = null)
                    }
                }
            )
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetElevation = 8.dp,
            // scrimColor = Color(0xccAAABBB),
            sheetContent = {
                // 🔥 Раскомментировать, чтобы увидеть состояние в контенте модального bottomSheet
                // MainContent(modalBottomSheetState, Color(0xff4CAF50))
                SheetContent()
            },
            content = {
                MainContent(modalBottomSheetState)
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainContent(
    modalBottomSheetState: ModalBottomSheetState,
    color: Color = Color(0xffE91E63)
) {

    // 🔥🔥 Не следует напрямую считывать состояние при каждой перекомпозиции, используйте derivedStateOf
    // Это сделано для демонстрации свойств modalBottomSheetState
    // Подробности — в Tutorial 4-5-2 про derivedStateOf
    val currentValue: ModalBottomSheetValue = modalBottomSheetState.currentValue
    val targetValue: ModalBottomSheetValue = modalBottomSheetState.targetValue

    modalBottomSheetState.isVisible

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(top = 16.dp)
    ) {
        Text(
            color = Color.White,
            text = "currentValue: $currentValue\n" +
                    "targetValue: $targetValue\n" +
                    "isExpanded: ${modalBottomSheetState.isVisible}"
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun SheetContent() {
    Column {
        LazyColumn {
            items(userList) { item: String ->
                ListItem(
                    icon = {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.avatar_1_raster),
                            contentDescription = null
                        )
                    },
                    secondaryText = {
                        Text(text = "Вторичный текст")
                    }
                ) {
                    Text(text = item, fontSize = 18.sp)
                }
            }
        }
    }
}

val userList = listOf(
    "User1",
    "User2",
    "User3",
    "User4",
    "User5",
    "User6",
    "User7",
    "User8",
    "User9",
    "User10",
    "User11",
    "User12",
    "User13",
    "User14",
    "User15",
)
