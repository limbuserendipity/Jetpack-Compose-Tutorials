import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.smarttoolfactory.tutorial1_1basics.R
import com.smarttoolfactory.tutorial1_1basics.model.Snack
import com.smarttoolfactory.tutorial1_1basics.model.snacks
import com.smarttoolfactory.tutorial1_1basics.ui.backgroundColor

/**
 * В этом учебном примере у карточки «Snack» есть заголовок, который может состоять из одной или двух строк.
 * Мы получаем количество строк и высоту заголовка, и динамически меняем отступ снизу,
 * чтобы высота каждого Composable совпадала друг с другом.
 */
@Preview
@Composable
fun Tutorial2_5Screen6() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    LazyVerticalGrid(
        // Вертикальный отступ между элементами
        verticalArrangement = Arrangement.spacedBy(8.dp),
        // Горизонтальный отступ между элементами
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        // Отступы по краям
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        // Фиксированное количество столбцов
        columns = GridCells.Fixed(2),
        content = {
            items(snacks) { snack: Snack ->
                GridSnackCardWithTitle(snack = snack)
            }
        }
    )
}

@Composable
fun GridSnackCardWithTitle(
    modifier: Modifier = Modifier,
    snack: Snack,
) {
    Column(
        modifier = modifier
            .heightIn(min = 200.dp)
            // Тень для карточки
            .shadow(1.dp, shape = RoundedCornerShape(5.dp))
            // Фон карточки
            .background(Color.White),
    ) {

        // Получаем текущее значение плотности пикселей (density) экрана
        val density = LocalDensity.current.density

        Image(
            // Установка способа масштабирования контента
            contentScale = ContentScale.None,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .clickable { /* Обработка нажатия на изображение */ },
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = snack.imageUrl)
                    .apply(block = fun ImageRequest.Builder.() {
                        // Плейсхолдер до загрузки изображения
                        placeholder(drawableResId = R.drawable.placeholder)
                    }
                    ).build()
            ),
            contentDescription = null
        )

        // Переменная для динамического отступа снизу
        var padding by remember { mutableStateOf(0.dp) }

        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = padding),
            text = "Snack ${snack.name}",
            fontSize = 20.sp,
            // onTextLayout вызывается при вычислении лейаута текста
            onTextLayout = {
                val lineCount = it.lineCount
                // Высота текста в dp
                val height = (it.size.height / density).dp

                println("lineCount: $lineCount, Height: $height")

                // Если строк > 1, не добавляем дополнительный отступ, иначе
                // прибавляем высоту, чтобы все карточки были одинакового размера
                padding = 4.dp + if (lineCount > 1) 0.dp else height
            }
        )
    }
}
