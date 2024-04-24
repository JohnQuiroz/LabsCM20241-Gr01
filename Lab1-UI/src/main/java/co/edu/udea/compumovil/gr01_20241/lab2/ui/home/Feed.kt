/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.edu.udea.compumovil.gr01_20241.lab2.ui.home

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr01_20241.lab2.model.Filter
import co.edu.udea.compumovil.gr01_20241.lab2.model.SnackCollection
import co.edu.udea.compumovil.gr01_20241.lab2.model.SnackRepo
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.FilterBar
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.JetsnackDivider
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.JetsnackScaffold
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.JetsnackSurface
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.SnackCollection
import co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail.SnackDetailViewModel
import co.edu.udea.compumovil.gr01_20241.lab2.ui.theme.JetsnackTheme
import co.edu.udea.compumovil.gr01_20241.lab2.ui.worker.InfoWorker
import co.edu.udea.compumovil.gr01_20241.lab2.ui.worker.WorkManagerJetsnackRepository

@Composable
fun Feed(
    onSnackClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackCollections = remember { SnackRepo.getSnacks() }
    val filters = remember { SnackRepo.getFilters() }
    JetsnackScaffold(
        bottomBar = {
            JetsnackBottomBar(
                tabs = HomeSections.values(),
                currentRoute = HomeSections.FEED.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Feed(
            snackCollections,
            filters,
            onSnackClick,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun Feed(
    snackCollections: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    snackDetailViewModel: SnackDetailViewModel = viewModel()
) {
    JetsnackSurface(modifier = modifier.fillMaxSize()) {
        Box {
            SnackCollectionList(snackCollections, filters, {
                val workManager = WorkManagerJetsnackRepository(context)
                InfoWorker.setViewModel(snackDetailViewModel)
                onSnackClick(it)
                workManager.getInfo()
            })
            DestinationBar()
        }
    }
}

@Composable
private fun SnackCollectionList(
    snackCollections: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Box(modifier) {
        LazyColumn {

            item {
                Spacer(
                    Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    )
                )
                FilterBar(filters, onShowFilters = { filtersVisible = true })
            }
            itemsIndexed(snackCollections) { index, snackCollection ->
                if (index > 0) {
                    JetsnackDivider(thickness = 2.dp)
                }

                SnackCollection(
                    snackCollection = snackCollection,
                    onSnackClick = onSnackClick,
                    index = index
                )
            }
        }
    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false }
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    JetsnackTheme {
        Feed(onSnackClick = { }, onNavigateToRoute = { })
    }
}
