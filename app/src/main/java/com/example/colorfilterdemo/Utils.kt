/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.colorfilterdemo

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.max

/**
 * Text component which accepts horizontal drag gestures to decrease/increase its value
 */
@Composable
fun SliderFloatLabel(matrixValue: MutableState<Float>, min: Float = 0f, max: Float = 1f,
                     stepValue: Float = (max - min) / 50, asInt: Boolean = false) {
    val formatString: String
    if (asInt) formatString = "%.0f"
    else formatString = "%.2f"
    Text(String.format(formatString, matrixValue.value), modifier = Modifier.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            if (dragAmount.x > 0) matrixValue.value += stepValue
            else matrixValue.value -= stepValue
            matrixValue.value = java.lang.Float.min(max(matrixValue.value, min), max)
        }
    })
}

val colorMatrixLabel = "Color"
val blendModeLabel = "Blend"
val lightingLabel = "Lighting"
