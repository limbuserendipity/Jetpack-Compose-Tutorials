package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.smarttoolfactory.tutorial1_1basics.R
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.Orange400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun Tutorial3_1Screen3() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        TutorialHeader(text = "graphicsLayer Modifier")

        StyleableTutorialText(
            text = "1-) Modifier.Element, который заставляет контент рисоваться в отдельном слое (draw layer). " +
                    "Этот слой может\n" +
                    " инвалидироваться отдельно от родителя. **graphicsLayer** следует использовать, " +
                    "когда контент\n" +
                    " обновляется независимо от верхних элементов, чтобы минимизировать область " +
                    "перерисовки.\n\n" +
                    " С помощью **graphicsLayer** можно применять эффекты к контенту, такие как " +
                    "масштаб (scale), поворот (rotation), прозрачность (opacity),\n" +
                    " тень (shadow) и обрезка (clipping)."
        )
        TutorialText2(text = "Смещение (Offset) и Перемещение (Translate)")
        OffsetAndTranslationExample()
        StyleableTutorialText(
            text = "2-) Изменение масштаба (scale) через **Modifier.graphicsLayer{}** не изменяет " +
                    "размер и границы (bounds) Composable, однако может меняться " +
                    "**положение** (positionInParent) с учётом того, где находится точка начала " +
                    "масштабирования (translate)."
        )
        GraphicsLayerExample()
        Spacer(modifier = Modifier.height(20.dp))
        StyleableTutorialText(
            text = "3-) При изменении ширины (width) границы Composable тоже изменяются."
        )
        WidthChangeExample()
        StyleableTutorialText(
            text = "4-) Изменения масштаба (scale) происходят относительно центра. Чтобы добиться " +
                    "такого же эффекта, как при изменении ширины, можно добавить " +
                    "перемещение (translate) и добиться того, чтобы преобразования " +
                    "выглядели начатым со **start** или **end** Composable."
        )
        TutorialText2(text = "Масштабирование (Scale) в сторону End Composable")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            ScaledAndTranslateEndExample()
        }
        TutorialText2(text = "Масштабирование (Scale) в сторону Start Composable")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            ScaledAndTranslateStartExample()
        }

        StyleableTutorialText(
            text = "5-) Параметры rotation (rotationX, rotationY, rotationZ) " +
                    "в **Modifier.graphicsLayer{}** позволяют вращать Composable " +
                    "по соответствующим осям."
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RotationExample()
        }

        StyleableTutorialText(
            text = "6-) **TransformOrigin** определяет, относительно какой точки " +
                    "(координаты pivotFractionX, pivotFractionY) применять " +
                    "преобразования, такие как масштабирование (scale) или перемещение (translate)."
        )
        TransformOriginExample()
    }
}

@Composable
private fun OffsetAndTranslationExample() {
    val context = LocalContext.current

    var value by remember { mutableStateOf(0f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(value.toInt(), 0)
                }
                .clickable {
                    Toast
                        .makeText(context, "Нажат элемент с offset", Toast.LENGTH_SHORT)
                        .show()
                }
                .zIndex(2f)
                .shadow(2.dp)
                .border(2.dp, Color.Green)
                .background(Orange400)
                .size(120.dp)
        )
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Blue400)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Нажат статичный элемент", Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }

    Spacer(modifier = Modifier.height(40.dp))

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationX = value
                }
                .clickable {
                    Toast
                        .makeText(context, "Нажат элемент с graphicsLayer translation", Toast.LENGTH_SHORT)
                        .show()
                }
                .zIndex(2f)
                .shadow(2.dp)
                .border(2.dp, Color.Green)
                .background(Orange400)
                .size(120.dp)
        )
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Blue400)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Нажат статичный элемент", Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("Offset/Translation: ${value.round2Digits()}")
    Slider(
        value = value,
        onValueChange = {
            value = it
        },
        valueRange = 0f..1000f
    )
}

@Composable
private fun GraphicsLayerExample() {
    val context = LocalContext.current

    var offsetX by remember { mutableStateOf(0f) }
    var sclX by remember { mutableStateOf(1f) }

    var textSize by remember { mutableStateOf("") }
    var textPosition by remember { mutableStateOf("") }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    translationX = offsetX
                    scaleX = sclX
                }
                .border(2.dp, Color.Green)
                .zIndex(2f)
                .size(120.dp)
                // Порядок clickable относительно graphicsLayer важен: то,
                // на каком этапе применяется clickable, и как рассчитываются размеры.
                // независимо от масштабирования точка нажатия остаётся прежней.
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            Toast
                                .makeText(context, "Клик по позиции: $it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
                .onSizeChanged {
                    textSize = "Size: $it\n"
                }
                .onGloballyPositioned {
                    textPosition =
                        "positionInParent: ${it.positionInParent()}\n" +
                                "positionInRoot: ${it.positionInRoot()}\n"
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Blue400)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Нажат статичный элемент", Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }

    Text(textSize + textPosition)

    Spacer(modifier = Modifier.height(5.dp))
    Text("translationX: ${offsetX.round2Digits()}")
    Slider(
        value = offsetX,
        onValueChange = {
            offsetX = it
        },
        valueRange = 0f..1000f
    )

    Text("scaleX: ${sclX.round2Digits()}")
    Slider(
        value = sclX,
        onValueChange = {
            sclX = it
        },
        valueRange = 0.3f..3f
    )
}

@Composable
private fun WidthChangeExample() {
    val context = LocalContext.current

    var offsetX by remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(100f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .width(width.dp)
                .graphicsLayer {
                    translationX = offsetX
                }
                .border(2.dp, Color.Green)
                .zIndex(2f)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Клик по изображению", Toast.LENGTH_SHORT)
                        .show()
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Blue400)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Нажат статичный элемент", Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("translationX: ${offsetX.round2Digits()}")
    Slider(
        value = offsetX,
        onValueChange = {
            offsetX = it
        },
        valueRange = 0f..1000f
    )

    Text("width: ${width.round2Digits()}dp")
    Slider(
        value = width,
        onValueChange = {
            width = it
        },
        valueRange = 0f..500f
    )
}

@Composable
private fun ScaledAndTranslateEndExample() {
    val context = LocalContext.current

    var sclX by remember { mutableStateOf(1f) }
    var width by remember { mutableStateOf(0f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    // смещение элемента вправо пропорционально изменениям масштаба
                    // (width * sclX - width) / 2
                    translationX = (width * sclX - width) / 2
                    scaleX = sclX
                }
                .onSizeChanged {
                    width = it.width.toFloat()
                }
                .zIndex(2f)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Клик по изображению", Toast.LENGTH_SHORT)
                        .show()
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("End Scale: ${(sclX.round2Digits())}")
    Slider(
        value = sclX,
        onValueChange = {
            sclX = it
        },
        valueRange = 0f..10f
    )
}

@Composable
private fun ScaledAndTranslateStartExample() {
    val context = LocalContext.current

    var sclX by remember { mutableStateOf(1f) }
    var width by remember { mutableStateOf(0f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    // смещение элемента влево пропорционально изменениям масштаба
                    // (width - width * sclX) / 2
                    translationX = (width - width * sclX) / 2
                    scaleX = sclX
                }
                .onSizeChanged {
                    width = it.width.toFloat()
                }
                .zIndex(2f)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Клик по изображению", Toast.LENGTH_SHORT)
                        .show()
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("Start Scale: ${(sclX.round2Digits())}")
    Slider(
        value = sclX,
        onValueChange = {
            sclX = it
        },
        valueRange = 0f..10f
    )
}

@Composable
private fun RotationExample() {
    val context = LocalContext.current
    var angleX by remember { mutableStateOf(0f) }
    var angleY by remember { mutableStateOf(0f) }
    var angleZ by remember { mutableStateOf(0f) }

    var camDistance by remember { mutableStateOf(5f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    rotationX = angleX
                    rotationY = angleY
                    rotationZ = angleZ
                    cameraDistance = camDistance
                }
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Клик по изображению", Toast.LENGTH_SHORT)
                        .show()
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("angleX: ${angleX.round2Digits()}")
    Slider(
        value = angleX,
        onValueChange = {
            angleX = it
        },
        valueRange = 0f..360f
    )

    Text("angleY: ${angleY.round2Digits()}")
    Slider(
        value = angleY,
        onValueChange = {
            angleY = it
        },
        valueRange = 0f..360f
    )

    Text("angleZ: ${angleZ.round2Digits()}")
    Slider(
        value = angleZ,
        onValueChange = {
            angleZ = it
        },
        valueRange = 0f..360f
    )

    Text("camDistance: ${camDistance.round2Digits()}")
    Slider(
        value = camDistance,
        onValueChange = {
            camDistance = it
        },
        valueRange = 0f..10f
    )
}

@Composable
private fun TransformOriginExample() {
    val context = LocalContext.current

    var sclX by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var angleX by remember { mutableStateOf(0f) }
    var angleY by remember { mutableStateOf(0f) }
    var angleZ by remember { mutableStateOf(0f) }

    var pivotFractionX by remember { mutableStateOf(0.5f) }

    Row(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    translationX = offsetX
                    scaleX = sclX
                    rotationX = angleX
                    rotationY = angleY
                    rotationZ = angleZ
                    transformOrigin =
                        TransformOrigin(pivotFractionX = pivotFractionX, pivotFractionY = 0.5f)
                }
                .size(120.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            Toast
                                .makeText(context, "Клик по позиции: $it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                },
            painter = painterResource(id = R.drawable.landscape1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
    Text("translationX: ${offsetX.round2Digits()}")
    Slider(
        value = offsetX,
        onValueChange = {
            offsetX = it
        },
        valueRange = 0f..1000f
    )

    Text("scaleX: ${sclX.round2Digits()}")
    Slider(
        value = sclX,
        onValueChange = {
            sclX = it
        },
        valueRange = 0.3f..3f
    )

    Text("angleX: ${angleX.round2Digits()}")
    Slider(
        value = angleX,
        onValueChange = {
            angleX = it
        },
        valueRange = 0f..360f
    )

    Text("angleY: ${angleY.round2Digits()}")
    Slider(
        value = angleY,
        onValueChange = {
            angleY = it
        },
        valueRange = 0f..360f
    )

    Text("angleZ: ${angleZ.round2Digits()}")
    Slider(
        value = angleZ,
        onValueChange = {
            angleZ = it
        },
        valueRange = 0f..360f
    )

    Text("pivotFractionX: ${(pivotFractionX.round2Digits())}")
    Slider(
        value = pivotFractionX,
        onValueChange = {
            pivotFractionX = it
        }
    )
}

private fun Float.round2Digits() = (this * 100).roundToInt() / 100f
