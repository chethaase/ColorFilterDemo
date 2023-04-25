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

package com.example.colorfilterdemo.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorfilterdemo.SliderFloatLabel

fun resetLightingValues(lightingFilterValues: LightingFilterValues) {
    lightingFilterValues.setMultiply(1f, 1f, 1f)
    lightingFilterValues.setAdd(0f, 0f, 0f)
}

class LightingFilterValues {
    val mulR = mutableStateOf(1f)
    val mulG = mutableStateOf(1f)
    val mulB = mutableStateOf(1f)
    val addR = mutableStateOf(0f)
    val addG = mutableStateOf(0f)
    val addB = mutableStateOf(0f)

    val multiplyColor: Color
        get() = Color(mulR.value, mulG.value, mulB.value)
    val addColor: Color
        get() = Color(addR.value, addG.value, addB.value)

    fun setMultiply(r: Float, g: Float, b: Float) {
        mulR.value = r
        mulG.value = g
        mulB.value = b
    }

    fun setAdd(r: Float, g: Float, b: Float) {
        addR.value = r
        addG.value = g
        addB.value = b
    }
}

@Composable
fun LightingFilterUI(lightingFilterValues: LightingFilterValues) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row() {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .width(20.dp)
                    .height(20.dp)
                    .border(1.dp, Color.Black)
                    .background(lightingFilterValues.multiplyColor)
            )
            Text(modifier = Modifier.padding(8.dp), text = "Multiplier")
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SliderFloatLabel(matrixValue = lightingFilterValues.mulR)
                SliderFloatLabel(matrixValue = lightingFilterValues.mulG)
                SliderFloatLabel(matrixValue = lightingFilterValues.mulB)
            }
        }
        Row() {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .width(20.dp)
                    .height(20.dp)
                    .border(1.dp, Color.Black)
                    .background(lightingFilterValues.addColor)
            )
            Text(modifier = Modifier.padding(8.dp), text = "Add")
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SliderFloatLabel(matrixValue = lightingFilterValues.addR)
                SliderFloatLabel(matrixValue = lightingFilterValues.addG)
                SliderFloatLabel(matrixValue = lightingFilterValues.addB)
            }
        }
        Button(modifier = Modifier.padding(8.dp), onClick = {
            resetLightingValues(lightingFilterValues) })
        {
            Text(text = "Reset")
        }
    }
}

