package com.natasa.morsecodecompose.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.natasa.morsecodecompose.data.MorseCodeMap.morseCodeMap


class MorseViewModel : ViewModel() {
    private val _state = mutableStateOf(MorseUiState())
    val state: State<MorseUiState> get() = _state

    fun processEvent(intent: MorseIntent) {
        when (intent) {
            is MorseIntent.Dot ->
                _state.value = _state.value.copy(morseSequence = _state.value.morseSequence + ".")

            is MorseIntent.Dash ->
                _state.value = _state.value.copy(morseSequence = _state.value.morseSequence + "-")

            is MorseIntent.Evaluate -> {
                morseToText(_state.value.morseSequence)?.let {
                    _state.value = _state.value.copy(
                        translatedText = _state.value.translatedText + it,
                        morseSequence = ""
                    )
                } ?: run {
                    _state.value =
                        MorseUiState(translatedText = _state.value.translatedText) // reset only morse sequence
                }
            }

            is MorseIntent.Reset -> _state.value =
                MorseUiState(translatedText = _state.value.translatedText)
        }
    }

    private fun morseToText(input: String): Char? {
        return morseCodeMap.entries.find { it.value == input }?.key
    }
}


