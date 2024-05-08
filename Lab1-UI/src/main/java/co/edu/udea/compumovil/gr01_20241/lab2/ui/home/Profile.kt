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

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr01_20241.lab2.ui.components.JetsnackScaffold
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun Profile(
    name: String?,
    age: Int?,
    weight: Float?,
    email: String?,
    onSignInWithGoogle: (String) -> Unit, // Update parameter type to handle name
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // Function to handle Google Sign-In result
    val handleGoogleSignInResult = remember {
        { task: Intent ->
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(task)
                val credential = GoogleAuthProvider.getCredential(account.result.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val firebaseUser = auth.currentUser
                            // Extract user information (assuming name is available)
                            val userName = firebaseUser?.displayName ?: ""
                            onSignInWithGoogle(userName) // Pass name to callback
                        }
                    }
            } catch (e: ApiException) {
                // Handle sign-in error
            }
        }
    }

    val signInLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle sign-in activity result here
        if (result.resultCode == Activity.RESULT_OK) {
            // User successfully signed in
            result.data ?: return@rememberLauncherForActivityResult // Handle null result
            // Extract information from data (e.g., using Intent)
        } else {
            // Handle sign-in error (optional)
        }
    }




    // Launch sign-in activity on button click
    LaunchedEffect(Unit) {
        if (name == null) {
            signInLauncher.launch(Intent(context, GoogleSignIn::class.java))
        }
    }

    JetsnackScaffold(
        bottomBar = {
            JetsnackBottomBar(
                tabs = HomeSections.values(),
                currentRoute = HomeSections.PROFILE.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .padding(46.dp)
                .padding(paddingValues)
        ) {
            if (name != null && age != null && weight != null && email != null) {
                // Mostrar la información de perfil si está disponible
                Text(text = "Name: $name")
                Text(text = "Age: $age")
                Text(text = "Weight: $weight kg")
                Text(text = "Email: $email")
            } else {
                
                Button(onClick = { signInLauncher.launch(Intent(context, GoogleSignIn::class.java)) }) {
                    Text(text = "Sign In with Google")
                }
            }
        }
    }
}
