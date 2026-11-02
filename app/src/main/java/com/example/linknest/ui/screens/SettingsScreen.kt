package com.example.linknest.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linknest.backup.BackupManager
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    selectedTheme: String,
    onThemeSelected: (String) -> Unit,
    viewModel: BookmarkViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pendingBackup by remember { mutableStateOf<String?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { writer ->
                    writer.write(pendingBackup.orEmpty())
                } ?: error("Could not open the selected file.")
                statusMessage = "Backup exported successfully."
            } catch (e: Exception) {
                statusMessage = "Export failed: ${e.message ?: "Unknown error"}"
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                val json = context.contentResolver.openInputStream(uri)
                    ?.bufferedReader()?.use { it.readText() }
                    ?: error("Could not read the selected file.")
                val (bookmarks, collections) = BackupManager.decode(json)
                viewModel.replaceAllData(bookmarks, collections) { error ->
                    statusMessage = if (error == null) {
                        "Backup restored. Your previous saved data was replaced."
                    } else {
                        "Restore failed: $error"
                    }
                }
            } catch (e: Exception) {
                statusMessage = "Invalid backup: ${e.message ?: "Unable to read file"}"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Appearance",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text("Choose how LinkNest looks.", color = MaterialTheme.colorScheme.onSurfaceVariant)

        listOf(
            ThemePreferences.SYSTEM to "System default",
            ThemePreferences.LIGHT to "Light theme",
            ThemePreferences.DARK to "Dark theme"
        ).forEach { (mode, label) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTheme == mode,
                    onClick = { onThemeSelected(mode) }
                )
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        Text(
            text = "Backup & Restore",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Save your bookmarks and collections to a JSON file, or restore them later.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(
            onClick = {
                scope.launch {
                    try {
                        val bookmarks = viewModel.exportBookmarks()
                        val collections = viewModel.exportCollections()
                        pendingBackup = BackupManager.encode(bookmarks, collections)
                        exportLauncher.launch("linknest-backup.json")
                    } catch (e: Exception) {
                        statusMessage = "Export failed: ${e.message ?: "Unknown error"}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Export Backup", color = MaterialTheme.colorScheme.surface)
        }

        OutlinedButton(
            onClick = { importLauncher.launch(arrayOf("application/json", "text/*")) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        ) {
            Text("Restore from Backup")
        }

        Text(
            text = "Restoring replaces the bookmarks and collections currently stored on this device.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )

        statusMessage?.let { message ->
            Text(
                text = message,
                color = if (message.startsWith("Export failed") || message.startsWith("Restore failed") || message.startsWith("Invalid")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
