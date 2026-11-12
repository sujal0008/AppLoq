package com.example.apploq

import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppSelectionScreen(
    modifier: Modifier = Modifier,
    onSave: (Set<String>) -> Unit
) {

    val context = LocalContext.current

    var installedApps by remember {
        mutableStateOf<List<InstalledApp>>(emptyList())
    }

    var selectedApps by remember {
        mutableStateOf(
            ProtectedAppsStorage.getProtectedApps(context)
        )
    }

    LaunchedEffect(Unit) {

        val packageManager = context.packageManager

        val intent = Intent(
            Intent.ACTION_MAIN,
            null
        ).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val packages = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_ALL
        )

        installedApps = packages
            .map { resolveInfo ->

                InstalledApp(
                    name = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.loadIcon(packageManager)
                )
            }
            .distinctBy { it.packageName }
            .sortedBy { it.name.lowercase() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Select Apps",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Choose the apps you want AppLoq to protect."
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(
                items = installedApps,
                key = { it.packageName }
            ) { app ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            bitmap = app.icon
                                .toBitmap()
                                .asImageBitmap(),

                            contentDescription = app.name,

                            modifier = Modifier
                                .size(48.dp)
                        )

                        Spacer(
                            modifier = Modifier.size(12.dp)
                        )

                        Column {

                            Text(
                                text = app.name,
                                fontSize = 18.sp
                            )

                            Text(
                                text = app.packageName,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Checkbox(
                        checked = selectedApps.contains(
                            app.packageName
                        ),

                        onCheckedChange = { checked ->

                            selectedApps =
                                if (checked) {

                                    selectedApps + app.packageName

                                } else {

                                    selectedApps - app.packageName
                                }
                        }
                    )
                }
            }
        }

        Button(
            onClick = {

                ProtectedAppsStorage.saveProtectedApps(
                    context = context,
                    apps = selectedApps
                )

                onSave(selectedApps)
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Save"
            )
        }
    }
}