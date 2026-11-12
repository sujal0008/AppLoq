package com.example.apploq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apploq.ui.theme.ApploqTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ApploqTheme {

                val context = LocalContext.current

                var currentScreen by remember {
                    mutableStateOf(
                        if (PinStorage.hasPin(context)) {
                            "enter"
                        } else {
                            "home"
                        }
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    when (currentScreen) {

                        "home" -> {
                            AppLoqHome(
                                modifier = Modifier.padding(innerPadding),
                                onCreatePin = {
                                    currentScreen = "create"
                                }
                            )
                        }

                        "create" -> {
                            CreatePinScreen(
                                modifier = Modifier.padding(innerPadding),
                                onPinCreated = {
                                    currentScreen = "dashboard"
                                }
                            )
                        }

                        "enter" -> {
                            EnterPinScreen(
                                modifier = Modifier.padding(innerPadding),
                                onUnlocked = {
                                    currentScreen = "dashboard"
                                }
                            )
                        }

                        "dashboard" -> {
                            AppLoqDashboard(
                                modifier = Modifier.padding(innerPadding),
                                onSelectApps = {
                                    currentScreen = "apps"
                                }
                            )
                        }

                        "apps" -> {
                            AppSelectionScreen(
                                modifier = Modifier.padding(innerPadding),
                                onSave = { selectedApps ->

                                    println("Selected apps: $selectedApps")

                                    currentScreen = "dashboard"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppLoqHome(
    modifier: Modifier = Modifier,
    onCreatePin: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🔒",
            fontSize = 64.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "AppLoq",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Protect your apps",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onCreatePin
        ) {
            Text(
                text = "Create PIN"
            )
        }
    }
}


@Composable
fun CreatePinScreen(
    modifier: Modifier = Modifier,
    onPinCreated: () -> Unit
) {

    val context = LocalContext.current

    var pin by remember {
        mutableStateOf("")
    }

    var firstPin by remember {
        mutableStateOf("")
    }

    var isConfirming by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (isConfirming) "🔒" else "🔐",
            fontSize = 50.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = if (isConfirming)
                "Confirm your PIN"
            else
                "Create your PIN",

            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = if (isConfirming)
                "Enter the same PIN again"
            else
                "Enter a 4-digit PIN",

            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "• ".repeat(pin.length),
            fontSize = 32.sp
        )

        if (errorMessage.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("1") {
                    if (pin.length < 4) {
                        pin += "1"
                        errorMessage = ""
                    }
                }

                PinButton("2") {
                    if (pin.length < 4) {
                        pin += "2"
                        errorMessage = ""
                    }
                }

                PinButton("3") {
                    if (pin.length < 4) {
                        pin += "3"
                        errorMessage = ""
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("4") {
                    if (pin.length < 4) {
                        pin += "4"
                        errorMessage = ""
                    }
                }

                PinButton("5") {
                    if (pin.length < 4) {
                        pin += "5"
                        errorMessage = ""
                    }
                }

                PinButton("6") {
                    if (pin.length < 4) {
                        pin += "6"
                        errorMessage = ""
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("7") {
                    if (pin.length < 4) {
                        pin += "7"
                        errorMessage = ""
                    }
                }

                PinButton("8") {
                    if (pin.length < 4) {
                        pin += "8"
                        errorMessage = ""
                    }
                }

                PinButton("9") {
                    if (pin.length < 4) {
                        pin += "9"
                        errorMessage = ""
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("⌫") {
                    if (pin.isNotEmpty()) {
                        pin = pin.dropLast(1)
                    }
                }

                PinButton("0") {
                    if (pin.length < 4) {
                        pin += "0"
                        errorMessage = ""
                    }
                }

                PinButton("✓") {

                    if (pin.length == 4) {

                        if (!isConfirming) {

                            firstPin = pin
                            pin = ""
                            isConfirming = true

                        } else {

                            if (pin == firstPin) {

                                PinStorage.savePin(
                                    context = context,
                                    pin = firstPin
                                )

                                errorMessage =
                                    "PIN created successfully ✓"

                                pin = ""
                            }

                            else {

                                errorMessage =
                                    "PINs do not match"

                                pin = ""
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PinButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(60.dp)
            .width(80.dp)
    ) {

        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}
@Composable
fun EnterPinScreen(
    modifier: Modifier = Modifier,
    onUnlocked: () -> Unit
) {
    val context = LocalContext.current

    var pin by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🔒",
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter your PIN",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your PIN to continue",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "• ".repeat(pin.length),
            fontSize = 32.sp
        )

        if (errorMessage.isNotEmpty()) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("1") {
                    if (pin.length < 4) {
                        pin += "1"
                        errorMessage = ""
                    }
                }

                PinButton("2") {
                    if (pin.length < 4) {
                        pin += "2"
                        errorMessage = ""
                    }
                }

                PinButton("3") {
                    if (pin.length < 4) {
                        pin += "3"
                        errorMessage = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("4") {
                    if (pin.length < 4) {
                        pin += "4"
                        errorMessage = ""
                    }
                }

                PinButton("5") {
                    if (pin.length < 4) {
                        pin += "5"
                        errorMessage = ""
                    }
                }

                PinButton("6") {
                    if (pin.length < 4) {
                        pin += "6"
                        errorMessage = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("7") {
                    if (pin.length < 4) {
                        pin += "7"
                        errorMessage = ""
                    }
                }

                PinButton("8") {
                    if (pin.length < 4) {
                        pin += "8"
                        errorMessage = ""
                    }
                }

                PinButton("9") {
                    if (pin.length < 4) {
                        pin += "9"
                        errorMessage = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                PinButton("⌫") {
                    if (pin.isNotEmpty()) {
                        pin = pin.dropLast(1)
                    }
                }

                PinButton("0") {
                    if (pin.length < 4) {
                        pin += "0"
                        errorMessage = ""
                    }
                }

                PinButton("✓") {

                    if (pin.length == 4) {

                        if (PinStorage.verifyPin(context, pin)) {

                            errorMessage = ""
                            onUnlocked()

                        } else {

                            errorMessage = "Incorrect PIN"
                            pin = ""
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppLoqDashboard(
    modifier: Modifier = Modifier,
    onSelectApps: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🔒",
            fontSize = 60.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "AppLoq",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Welcome back!",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Your apps are protected",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onSelectApps
        ) {
            Text(
                text = "Select Apps"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                // Settings will be added later
            }
        ) {
            Text(
                text = "Settings"
            )
        }
    }
}