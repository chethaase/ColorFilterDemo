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

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.colorfilterdemo.ui.theme.ColorFilterDemoTheme
import com.example.colorfilterdemo.ui.theme.LightingFilterUI
import com.example.colorfilterdemo.ui.theme.LightingFilterValues

/**
 * This activity shows how the three main color filter classes work. Select a picture from the
 * gallaery at the top and select the filter to use on it, then change the paramaters for that
 * filter in the UI.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ColorFilterDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content(resources)
                }
            }
        }
    }
}

@Composable
fun Content(resources: Resources) {
    var currentFilter by remember { mutableStateOf(colorMatrixLabel) }
    val lightingFilterValues by remember { mutableStateOf(LightingFilterValues()) }
    val blendFilterValues by remember { mutableStateOf(BlendFilterValues()) }
    val colorMatrixFilterValues by remember { mutableStateOf(ColorMatrixFilterValues()) }

    // Hack alert: Demo purposes only. In a real application, these images would be loaded
    // asynchronously
    val imageIds = arrayOf(R.drawable.bay, R.drawable.sfbay, R.drawable.tree,
        R.drawable.skyline, R.drawable.missionpeak)
    val preloadedImages = mutableListOf<ImageBitmap>()
    for (id in imageIds) {
        preloadedImages.add(ImageBitmap.imageResource(resources, id))
    }
    var currentImage by remember { mutableStateOf(preloadedImages[0]) }

    Column() {

        Gallery(preloadedImages, currentImage, { currentImage = it})
        Row(modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterHorizontally)) {
            FilteredImage(currentFilter, currentImage,
                    lightingFilterValues, blendFilterValues, colorMatrixFilterValues)
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            FilterUI(currentFilter, { currentFilter = it }, lightingFilterValues, blendFilterValues,
                colorMatrixFilterValues)
        }
    }
}

/**
 * Horizontal scrolling list of images to choose from
 */
@Composable
fun Gallery(images: List<ImageBitmap>, currentImage: ImageBitmap, onImageChange: (ImageBitmap) -> Unit) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState()),) {
        for (image in images) {
            Image(image,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .clickable { onImageChange(image) }
            )
        }
    }
}

/**
 * Displays a given image filtered through the currently selected filter.
 */
@Composable
fun FilteredImage(currentFilter: String, currentImage: ImageBitmap,
        lightingFilterValues: LightingFilterValues, blendFilterValues: BlendFilterValues,
        colorMatrixFilterValues: ColorMatrixFilterValues)
{
    val valuesArray = FloatArray(20)
    lateinit var filter: ColorFilter
    when (currentFilter) {
        lightingLabel -> {
            filter = ColorFilter.lighting(lightingFilterValues.multiplyColor,
                lightingFilterValues.addColor)
        }
        colorMatrixLabel -> {
            var elementIndex = 0
            colorMatrixFilterValues.matrix.forEach {
                it.forEach { valuesArray[elementIndex++] = it.value }
            }
            val colorMatrix = ColorMatrix(valuesArray)
            filter = ColorFilter.colorMatrix(colorMatrix)
        }
        blendModeLabel -> {
            filter = ColorFilter.tint(blendFilterValues.color, blendFilterValues.blendMode.value)
        }
        else -> {
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.DarkGray)) {
        Image(currentImage,
            colorFilter = filter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

/**
 * Panel for choosing current filter options
 */
@Composable
fun FilterUI(currentFilter: String, onFilterChange: (String) -> Unit,
        lightingFilterValues: LightingFilterValues, blendFilterValues: BlendFilterValues,
        colorMatrixFilterValues: ColorMatrixFilterValues)
{
    var filterMenuExpanded by remember { mutableStateOf(false) }
    Row(Modifier.background(Color.White)) {
        when (currentFilter) {
            colorMatrixLabel -> {
                ColorMatrixFilterUI(colorMatrixFilterValues)
            }
            lightingLabel -> {
                LightingFilterUI(lightingFilterValues)
            }
            blendModeLabel -> {
                BlendModeFilterUI(blendFilterValues)
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        listOf(colorMatrixLabel, lightingLabel, blendModeLabel).forEach { filterLabel ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = currentFilter == filterLabel, onClick = {
                        onFilterChange(filterLabel)
                    })
                Text(filterLabel)
            }
        }
    }
}
