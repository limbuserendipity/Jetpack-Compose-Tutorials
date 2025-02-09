package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.tutorial1_1basics.ui.backgroundColor

@Preview
@ExperimentalMaterialApi
@Composable
fun Tutorial2_8Screen(onBack: (() -> Unit)? = null) {
    TutorialContent(onBack)
}

@ExperimentalMaterialApi
@Composable
private fun TutorialContent(onBack: (() -> Unit)? = null) {
    Scaffold(
        modifier = Modifier.background(backgroundColor),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color(0xffFFA000)
            ) {
                Icon(
                    Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBarComponent(onBack)
        }
    ) {
        MainContent(it.calculateBottomPadding())
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainContent(bottomAppBarHeight: Dp) {

    // 🔥 Получаем высоту BottomAppBar, чтобы установить корректный нижний отступ для LazyColumn
    LazyColumn(
        modifier = Modifier.background(backgroundColor),
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = bottomAppBarHeight + 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(userList) { item: String ->
            Card(shape = RoundedCornerShape(8.dp)) {
                ListItem(
                    modifier = Modifier.clickable { },
                    icon = {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            painter = painterResource(
                                id = com.smarttoolfactory.tutorial1_1basics.R.drawable.avatar_1_raster
                            ),
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

@Composable
private fun BottomAppBarComponent(onBack: (() -> Unit)? = null) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 4.dp,
        cutoutShape = CircleShape
    ) {

        // Ведущие иконки (Leading icons) обычно должны иметь высокий контент-альфа
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(onClick = { onBack?.invoke() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        // Действия (actions) располагаются в конце BottomAppBar.
        // Они используют значение content alpha по умолчанию (medium),
        // которое определено в BottomAppBar
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(Icons.Filled.Search, contentDescription = null)
        }

        IconButton(onClick = { }) {
            Icon(Icons.Filled.MoreVert, contentDescription = null)
        }
    }
}
