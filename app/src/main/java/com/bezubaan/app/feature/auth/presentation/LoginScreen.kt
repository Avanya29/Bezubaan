package com.bezubaan.app.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bezubaan.app.ui.components.NeoButton
import com.bezubaan.app.ui.components.NeoInput
import com.bezubaan.app.ui.components.NeoOutlinedButton

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        NeoInput(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        Spacer(modifier = Modifier.height(16.dp))

        NeoInput(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onNavigateToForgot() }
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        NeoButton(
            text = if (uiState.isLoading) "Logging in..." else "Login",
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "OR", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp))

        NeoOutlinedButton(
            text = "Continue with Google",
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        NeoOutlinedButton(
            text = "Continue with Apple",
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Don't have an account? Register",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable { onNavigateToRegister() }
                .padding(8.dp)
        )
    }
}
