package com.smarttoolfactory.tutorial1_1basics.chapter2_material_widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun Tutorial2_5Screen7() {
    TutorialContent()
}

@ExperimentalMaterialApi
@Composable
private fun TutorialContent() {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TutorialHeader(text = "ListItem")
            TutorialText2(text = "one-line")
            OneLineListItemExample()
        }

        item {
            TutorialText2(text = "two-line")
            TwoLineListItemExample()
        }

        item {
            TutorialText2(text = "three-line")
            ThreeLineListItemExample()
        }

        item {
            TutorialText2(text = "combined")
            CombinedListItemExample()
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun OneLineListItemExample() {
    Column {
        ListItem(text = { Text("Однострочный элемент списка без иконки") })
        Divider()
        ListItem(
            text = { Text("Однострочный элемент списка с иконкой 24x24") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Однострочный элемент списка с иконкой 40x40") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Однострочный элемент списка с иконкой 56x56") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Однострочный кликабельный элемент списка") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )
            },
            modifier = Modifier.clickable { }
        )
        Divider()
        ListItem(
            text = { Text("Однострочный элемент списка с иконкой в конце") },
            trailing = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized Description"
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Однострочный элемент списка") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            },
            trailing = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        )
        Divider()
    }
}

@ExperimentalMaterialApi
@Composable
private fun TwoLineListItemExample() {
    Column {
        ListItem(
            text = { Text("Двухстрочный элемент списка") },
            secondaryText = { Text("Вторичный текст") }
        )
        Divider()
        ListItem(
            text = { Text("Двухстрочный элемент списка") },
            overlineText = { Text("НАДПИСЬ") }
        )
        Divider()
        ListItem(
            text = { Text("Двухстрочный элемент списка с иконкой 24x24") },
            secondaryText = { Text("Вторичный текст") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Двухстрочный элемент списка с иконкой 40x40") },
            secondaryText = { Text("Вторичный текст") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Двухстрочный элемент списка с иконкой 40x40") },
            secondaryText = { Text("Вторичный текст") },
            trailing = { Text("мета") },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        Divider()
    }
}

@ExperimentalMaterialApi
@Composable
private fun ThreeLineListItemExample() {
    Column {
        ListItem(
            text = { Text("Трехстрочный элемент списка") },
            secondaryText = {
                Text(
                    "Это длинный вторичный текст для текущего элемента списка, " +
                            "отображается в две строки"
                )
            },
            singleLineSecondaryText = false,
            trailing = { Text("мета") }
        )
        Divider()
        ListItem(
            text = { Text("Трехстрочный элемент списка") },
            overlineText = { Text("НАДПИСЬ") },
            secondaryText = { Text("Вторичный текст") }
        )
        Divider()
        ListItem(
            text = { Text("Трехстрочный элемент списка с иконкой 24x24") },
            secondaryText = {
                Text(
                    "Это длинный вторичный текст для текущего элемента списка " +
                            "отображается в две строки"
                )
            },
            singleLineSecondaryText = false,
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Трехстрочный элемент списка с иконкой в конце") },
            secondaryText = {
                Text(
                    "Это длинный вторичный текст для текущего элемента списка," +
                            " отображается в две строки"
                )
            },
            singleLineSecondaryText = false,
            trailing = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        )
        Divider()
        ListItem(
            text = { Text("Трехстрочный элемент списка") },
            overlineText = { Text("НАДПИСЬ") },
            secondaryText = { Text("Вторичный текст") },
            trailing = { Text("мета") }
        )
        Divider()
    }
}

@ExperimentalMaterialApi
@Composable
private fun CombinedListItemExample() {
    Column {
        var switched by remember { mutableStateOf(false) }
        val onSwitchedChange: (Boolean) -> Unit = { switched = it }
        ListItem(
            text = { Text("Переключатель (Switch) в списке") },
            trailing = {
                Switch(
                    checked = switched,
                    onCheckedChange = null
                )
            },
            modifier = Modifier.toggleable(
                value = switched,
                onValueChange = onSwitchedChange
            )
        )
        Divider()
        var checked by remember { mutableStateOf(true) }
        val onCheckedChange: (Boolean) -> Unit = { checked = it }
        ListItem(
            text = { Text("Флажок (Checkbox) в списке") },
            trailing = {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null
                )
            },
            modifier = Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange
            )
        )
        Divider()
    }
}