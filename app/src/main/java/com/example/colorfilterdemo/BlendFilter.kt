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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val blendModes = listOf<BlendMode>(
    BlendMode.Color, BlendMode.Clear, BlendMode.ColorBurn, BlendMode.ColorDodge,
    BlendMode.Darken, BlendMode.Difference, BlendMode.Dst, BlendMode.DstAtop,
    BlendMode.DstIn, BlendMode.DstOut, BlendMode.Exclusion, BlendMode.Hardlight,
    BlendMode.Hue, BlendMode.Lighten, BlendMode.Luminosity, BlendMode.Modulate,
    BlendMode.Multiply, BlendMode.Overlay, BlendMode.Plus, BlendMode.Saturation,
    BlendMode.Screen, BlendMode.Softlight, BlendMode.Src, BlendMode.SrcAtop,
    BlendMode.SrcIn, BlendMode.SrcOut, BlendMode.SrcOver, BlendMode.Xor
)

fun resetBlendValues(blendFilterValues: BlendFilterValues) {
    blendFilterValues.color = Color(1f, 1f, 1f, 0f)
    blendFilterValues.blendMode.value = BlendMode.SrcOver
}

class BlendFilterValues {
    val r = mutableStateOf(1f)
    val g = mutableStateOf(1f)
    val b = mutableStateOf(1f)
    val a = mutableStateOf(0f)
    val blendMode = mutableStateOf(BlendMode.SrcOver)

    var color: Color
        get() = Color(r.value, g.value, b.value, a.value)
        set(newColor) {
            r.value = newColor.red
            g.value = newColor.green
            b.value = newColor.blue
            a.value = newColor.alpha
        }
}

/**
 * Panel for choosing BlendModeFilter
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BlendModeFilterUI(blendFilterValues: BlendFilterValues) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var expanded by remember {
            mutableStateOf(false)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .padding(8.dp)
                .width(20.dp)
                .height(20.dp)
                .border(1.dp, Color.Black)
                .background(blendFilterValues.color))
            Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Row(modifier = Modifier.padding(8.dp)) {
                    SliderFloatLabel(matrixValue = blendFilterValues.r)
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    SliderFloatLabel(matrixValue = blendFilterValues.g)
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    SliderFloatLabel(matrixValue = blendFilterValues.b)
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    SliderFloatLabel(matrixValue = blendFilterValues.a)
                }
            }
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
                Text(modifier = Modifier.fillMaxWidth(),
                    text = "BlendMode: ${blendFilterValues.blendMode.value.toString()}")
                ExposedDropdownMenu(//modifier = Modifier.verticalScroll(rememberScrollState()),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    blendModes.forEach {
                        DropdownMenuItem(onClick = {
                            expanded = false
                            blendFilterValues.blendMode.value = it
                        }) {
                            Text(text = it.toString())
                        }
                    }
                }
            }
        }
        Button(modifier = Modifier.padding(8.dp), onClick = { resetBlendValues(blendFilterValues) }) {
            Text(text = "Reset")
        }
    }
}

