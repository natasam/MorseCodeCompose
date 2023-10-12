package com.natasa.morsecodecompose.ui

sealed class MorseIntent {
    object Dot : MorseIntent()
    object Dash : MorseIntent()
    object Evaluate : MorseIntent()
    object Reset : MorseIntent()
}