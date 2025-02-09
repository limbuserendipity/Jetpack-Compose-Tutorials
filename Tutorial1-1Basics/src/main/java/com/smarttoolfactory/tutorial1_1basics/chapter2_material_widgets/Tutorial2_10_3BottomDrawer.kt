import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun Tutorial2_10Screen3() {
    TutorialContent()
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun TutorialContentPreview(
    @PreviewParameter(BottomDrawerValueProvider::class)
    initialBottomDrawerValue: BottomDrawerValue
) {
    TutorialContent(initialBottomDrawerValue)
}

@OptIn(ExperimentalMaterialApi::class)
class BottomDrawerValueProvider : PreviewParameterProvider<BottomDrawerValue> {
    override val values: Sequence<BottomDrawerValue>
        get() = sequenceOf(
            BottomDrawerValue.Closed,
            BottomDrawerValue.Open,
            BottomDrawerValue.Expanded
        )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TutorialContent(initialBottomDrawerValue: BottomDrawerValue = BottomDrawerValue.Closed) {
    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = gesturesEnabled,
                    onValueChange = toggleGesturesEnabled
                )
        ) {
            Checkbox(gesturesEnabled, null)
            Text(text = if (gesturesEnabled) "Жесты включены" else "Жесты отключены")
        }
        val drawerState = rememberBottomDrawerState(
            initialValue = initialBottomDrawerValue,
            confirmStateChange = { _: BottomDrawerValue ->
                true
            }
        )

        BottomDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            // scrimColor — цвет затемнения, который скрывает контент, когда панель открыта.
            // Если указать Color.Unspecified, затемнение применяться не будет.
//            scrimColor = Color.Unspecified,
            drawerContent = {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    onClick = { scope.launch { drawerState.close() } },
                    content = { Text("Закрыть выдвижную панель") }
                )
                LazyColumn {
                    items(25) {
                        ListItem(
                            text = { Text("Элемент $it") },
                            icon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Локализованное описание"
                                )
                            }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val openText = if (gesturesEnabled) "▲▲▲ Потяните вверх ▲▲▲" else "Нажмите на кнопку!"
                    Text(text = if (drawerState.isClosed) openText else "▼▼▼ Потяните вниз ▼▼▼")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = {
                        scope.launch {
                            drawerState.open()
                            // Расширяет на всю высоту
//                            drawerState.expand()
                        }
                    }
                    ) {
                        Text("Нажмите для открытия")
                    }
                }
            }
        )
    }
}
