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

package co.edu.udea.compumovil.gr01_20241.lab2.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import co.edu.udea.compumovil.gr01_20241.lab2.ui.home.HomeSections
import co.edu.udea.compumovil.gr01_20241.lab2.ui.home.addHomeGraph
import co.edu.udea.compumovil.gr01_20241.lab2.ui.navigation.MainDestinations
import co.edu.udea.compumovil.gr01_20241.lab2.ui.navigation.rememberJetsnackNavController
import co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail.SnackDetail
import co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail.SnackDetailViewModel
import co.edu.udea.compumovil.gr01_20241.lab2.ui.theme.JetsnackTheme

@Composable
fun JetsnackApp() {
    val snackDetailViewModel: SnackDetailViewModel = viewModel()
    JetsnackTheme {
        val jetsnackNavController = rememberJetsnackNavController()
        NavHost(
            navController = jetsnackNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            jetsnackNavGraph(
                onSnackSelected = jetsnackNavController::navigateToSnackDetail,
                upPress = jetsnackNavController::upPress,
                onNavigateToRoute = jetsnackNavController::navigateToBottomBarRoute,
                snackDetailViewModel = snackDetailViewModel
            )
        }
    }
}

private fun NavGraphBuilder.jetsnackNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
    snackDetailViewModel: SnackDetailViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onSnackSelected, onNavigateToRoute, snackDetailViewModel)
    }
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        SnackDetail(snackId, upPress, snackDetailViewModel)
    }
}
