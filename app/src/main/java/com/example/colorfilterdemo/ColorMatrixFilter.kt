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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


fun resetColorValues(values: ColorMatrixFilterValues) {
    // Row 0
    values.matrix[0][0].value = 1.0f
    values.matrix[0][1].value = 0.0f
    values.matrix[0][2].value = 0.0f
    values.matrix[0][3].value = 0.0f
    values.matrix[0][4].value = 0.0f

    // Row 1
    values.matrix[1][0].value = 0.0f
    values.matrix[1][1].value = 1.0f
    values.matrix[1][2].value = 0.0f
    values.matrix[1][3].value = 0.0f
    values.matrix[1][4].value = 0.0f

    // Row 2
    values.matrix[2][0].value = 0.0f
    values.matrix[2][1].value = 0.0f
    values.matrix[2][2].value = 1.0f
    values.matrix[2][3].value = 0.0f
    values.matrix[2][4].value = 0.0f

    // Row 3
    values.matrix[3][0].value = 0.0f
    values.matrix[3][1].value = 0.0f
    values.matrix[3][2].value = 0.0f
    values.matrix[3][3].value = 1.0f
    values.matrix[3][4].value = 0.0f
}

class ColorMatrixFilterValues {
    val matrix = arrayOf(
        arrayOf(mutableStateOf(1.0f), mutableStateOf(0.0f),
            mutableStateOf(0.0f), mutableStateOf(0.0f), mutableStateOf(0.0f)),
        arrayOf(mutableStateOf(0.0f), mutableStateOf(1.0f),
            mutableStateOf(0.0f), mutableStateOf(0.0f), mutableStateOf(0.0f)),
        arrayOf(mutableStateOf(0.0f), mutableStateOf(0.0f),
            mutableStateOf(1.0f), mutableStateOf(0.0f), mutableStateOf(0.0f)),
        arrayOf(mutableStateOf(0.0f), mutableStateOf(0.0f),
            mutableStateOf(0.0f), mutableStateOf(1.0f), mutableStateOf(0.0f)),
    )
}

@Composable
fun ColorMatrixFilterUI(values: ColorMatrixFilterValues) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                resetColorValues(values)
                values.matrix[0][0].value = .393f
                values.matrix[0][1].value = .769f
                values.matrix[0][2].value = .189f
                values.matrix[1][0].value = .349f
                values.matrix[1][1].value = .686f
                values.matrix[1][2].value = .168f
                values.matrix[2][0].value = .272f
                values.matrix[2][1].value = .534f
                values.matrix[2][2].value = .131f
            }) {
                Text("Sepia")
            }
            Button(onClick = {
                resetColorValues(values)
                values.matrix[0][0].value = .299f
                values.matrix[0][1].value = .587f
                values.matrix[0][2].value = .114f
                values.matrix[1][0].value = .299f
                values.matrix[1][1].value = .587f
                values.matrix[1][2].value = .114f
                values.matrix[2][0].value = .299f
                values.matrix[2][1].value = .587f
                values.matrix[2][2].value = .114f
            }) {
                Text("Grayscale")
            }
            Button(onClick = {
                resetColorValues(values)
                values.matrix[0][0].value = -1f
                values.matrix[1][1].value = -1f
                values.matrix[2][2].value = -1f
                values.matrix[0][4].value = 255f
                values.matrix[1][4].value = 255f
                values.matrix[2][4].value = 255f
            }) {
                Text("Inverse")
            }
        }
        values.matrix.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                it.forEachIndexed { index, matrixElement ->
                    var minValue = -1f
                    var maxValue = 1f
                    // Last column is 0-255
                    if (index == it.size - 1) {
                        minValue = 0f
                        maxValue = 255f
                    }
                    SliderFloatLabel(
                        matrixValue = matrixElement,
                        min = minValue,
                        max = maxValue,
                        asInt = maxValue == 255f
                    )
                }
            }
        }
        Button(modifier = Modifier.padding(8.dp), onClick = { resetColorValues(values) }) {
            Text(text = "Reset")
        }
    }
}
