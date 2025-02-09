package com.smarttoolfactory.tutorial1_1basics.chapter3_layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.ui.Blue400
import com.smarttoolfactory.tutorial1_1basics.ui.components.StyleableTutorialText
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialHeader
import com.smarttoolfactory.tutorial1_1basics.ui.components.TutorialText2


@Preview(showBackground = true)
@Composable
fun Tutorial3_1Screen6() {
    TutorialContent()
}

@Composable
private fun TutorialContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TutorialHeader(text = "Цепочка модификаторов размера (Chaining Size Modifiers)")

        // В этом примере используются только модификаторы ширины, так как
        // вертикальный скролл меняет maxHeight на Constraints.Infinity
        StyleableTutorialText(
            text = "1-) Модификаторы размера возвращают Constraints, которые содержат " +
                    "диапазон min-max, необходимый для измерения Composable. " +
                    "При цепочке (chaining) модификаторов можно сужать " +
                    "диапазон (narrow range), но нельзя его расширять. " +
                    "Например, если первый модификатор это **Modifier.width(50.dp)**, " +
                    "он устанавливает min=50.dp, max=50.dp, и следующий " +
                    "**Modifier.size** уже не влияет на итоговые Constraints."
        )

        ChainSizeModifiersSample()

        StyleableTutorialText(
            text = "3-) Модификаторы **required** могут изменять min или/и max Constraints, " +
                    "приходящие сверху или от родителя (Parent). " +
                    "Таким образом они могут, например, расширить диапазон измерения " +
                    "в отличие от обычных size-модификаторов. " +
                    "Если контент выбрал размер, не удовлетворя incoming Constraints, " +
                    "родительский лейаут всё равно принудительно будет учитывать " +
                    "Constraints. При этом контент будет смещён (offset) по центру, " +
                    "будто Constraints были соблюдены."
        )
        ChainRequiredSizeModifierSample()
    }
}

@Composable
private fun ChainSizeModifiersSample() {
    TutorialText2(text = "❌fillMaxWidth().width(50.dp)")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .width(50.dp)
            .border(2.dp, Color.Red)
    ) {
        Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
    }

    TutorialText2(text = "❌width(200.dp).width(50.dp)")
    BoxWithConstraints(
        modifier = Modifier
            .width(200.dp)
            .width(50.dp)
            .border(2.dp, Color.Red)
    ) {
        Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
    }

    StyleableTutorialText(
        text = "2-) **Modifier.width/height/sizeIn** описывает диапазон " +
                "между min и max значениями. Допустимо сужать диапазон, " +
                "но нельзя его расширять, как показано в примерах ниже."
    )

    TutorialText2(text = "✅widthIn(min = 100.dp, max = 200.dp).width(150.dp)")
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 100.dp, max = 200.dp)
            .width(150.dp)
            .border(2.dp, Color.Green)
    ) {
        Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
    }

    TutorialText2(text = "❌widthIn(min = 100.dp, max = 200.dp).width(50.dp)")
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 100.dp, max = 200.dp)
            .width(50.dp)
            .border(2.dp, Color.Red)
    ) {
        Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
    }

    TutorialText2(text = "❌widthIn(min = 100.dp, max = 200.dp).width(250.dp)")
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 100.dp, max = 200.dp)
            .width(250.dp)
            .border(2.dp, Color.Red)
    ) {
        Text(text = "minWidth: $minWidth, maxWidth: $maxWidth")
    }
}

@Composable
private fun ChainRequiredSizeModifierSample() {
    // В этих примерах requiredWidth не совпадает с Constraints от Modifier.size,
    // поэтому родитель пытается расположить контент по центру. Если required больше,
    // то элемент смещается к левой границе, если required меньше — к правой (по центру).
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .border(2.dp, Blue400)
    ) {
        TutorialText2(text = "❌size(100.dp).requiredWidth(140.dp)")
        BoxWithConstraints(
            modifier = Modifier
                .border(2.dp, Color.Red)
                .size(100.dp)
                .requiredWidth(140.dp)
        ) {
            Text(
                text = "minWidth: $minWidth, maxWidth: $maxWidth",
                modifier = Modifier.border(3.dp, Color.Green)
            )
        }

        TutorialText2(text = "❌size(100.dp).requiredWidth(80.dp)")
        BoxWithConstraints(
            modifier = Modifier
                .border(2.dp, Color.Red)
                .size(100.dp)
                .requiredWidth(80.dp)
        ) {
            Text(
                text = "minWidth: $minWidth, maxWidth: $maxWidth",
                modifier = Modifier.border(3.dp, Color.Green)
            )
        }
    }
}
