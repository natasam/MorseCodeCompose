package com.natasa.morsecodecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.natasa.morsecodecompose.R
import kotlinx.coroutines.delay

@Composable
fun MorseCodeScreen(
    viewModel: MorseViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val gradientColors = listOf(Black, Blue, Magenta, Red)

    val brush = remember {
        Brush.linearGradient(
            colors = gradientColors
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        Column(modifier = Modifier.padding(all = 16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.morse_code_app),
                modifier = Modifier.padding(all = 16.dp),
                style = TextStyle(brush = brush, fontSize = 26.sp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutputTextField(state, brush)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                val startTime = System.currentTimeMillis()
                                awaitRelease()
                                val endTime = System.currentTimeMillis()
                                if (endTime - startTime > 400) {
                                    viewModel.processEvent(MorseIntent.Dash)
                                } else {
                                    viewModel.processEvent(MorseIntent.Dot)
                                }

                                delay(3200) // Wait a bit to see if the user continues inputting
                                viewModel.processEvent(MorseIntent.Evaluate)

                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.morseSequence, color = Black, style = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold), fontSize = 64.sp)
            }
        }
    }


}

@Composable
private fun OutputTextField(
    state: MorseUiState,
    brush: Brush
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.translatedText,
            onValueChange = { },
            textStyle = TextStyle(brush = brush, fontSize = 26.sp),
            readOnly = true,
            label = { Text("Output Text") }
        )
    }
}
