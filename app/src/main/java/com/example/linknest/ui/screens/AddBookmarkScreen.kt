
package com.example.linknest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linknest.data.Bookmark
import com.example.linknest.data.Collection
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun AddBookmarkScreen(
    onBack: () -> Unit,
    bookmarkViewModel: BookmarkViewModel = viewModel(),
    existingBookmark: Bookmark? = null
) {
    var title by remember(existingBookmark?.id) { mutableStateOf(existingBookmark?.title ?: "") }
    var url by remember(existingBookmark?.id) { mutableStateOf(existingBookmark?.url ?: "") }
    var description by remember(existingBookmark?.id) { mutableStateOf(existingBookmark?.description ?: "") }
    var tags by remember(existingBookmark?.id) { mutableStateOf(existingBookmark?.tags ?: "") }
    var category by remember(existingBookmark?.id) { mutableStateOf(existingBookmark?.category ?: "Learning") }

    var categoryExpanded by remember { mutableStateOf(false) }
    var collectionExpanded by remember { mutableStateOf(false) }
    var selectedCollection by remember(existingBookmark?.id) { mutableStateOf<Collection?>(null) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    var isFetchingTitle by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val collections by bookmarkViewModel.collections.collectAsState()

    LaunchedEffect(collections, existingBookmark?.collectionId) {
        selectedCollection = collections.firstOrNull { it.id == existingBookmark?.collectionId }
    }

    val categories = listOf(
        "Learning",
        "Work",
        "Entertainment",
        "Technology",
        "Travel",
        "General"
    )

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        errorBorderColor = MaterialTheme.colorScheme.primary,
        errorLabelColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // HEADER
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onBack()
                    }
                )

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        if (existingBookmark == null) "Add a bookmark 🍂" else "Edit bookmark 🍂",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        "Save something worth revisiting.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // URL
            OutlinedTextField(
                value = url,
                onValueChange = {
                    url = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Website URL *") },
                placeholder = {
                    Text("https://example.com")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors
            )

            TextButton(
                onClick = {
                    val raw = url.trim()
                    if (raw.isBlank()) {
                        errorMessage = "Enter a URL first."
                    } else {
                        isFetchingTitle = true
                        errorMessage = null
                        scope.launch {
                            val fetchedTitle = withContext(Dispatchers.IO) {
                                runCatching {
                                    val normalized = if (raw.startsWith("http://", true) || raw.startsWith("https://", true)) raw else "https://$raw"
                                    val connection = (URL(normalized).openConnection() as HttpURLConnection).apply {
                                        requestMethod = "GET"
                                        connectTimeout = 5000
                                        readTimeout = 5000
                                        setRequestProperty("User-Agent", "Mozilla/5.0 LinkNest")
                                    }
                                    try {
                                        connection.inputStream.bufferedReader().use { reader ->
                                            val html = reader.readText().take(1_000_000)
                                            Regex("(?is)<title[^>]*>(.*?)</title>")
                                                .find(html)?.groupValues?.get(1)
                                                ?.replace(Regex("\\s+"), " ")?.trim()
                                        }
                                    } finally {
                                        connection.disconnect()
                                    }
                                }.getOrNull()
                            }
                            isFetchingTitle = false
                            if (!fetchedTitle.isNullOrBlank()) {
                                title = fetchedTitle
                            } else {
                                errorMessage = "Couldn't fetch the page title. You can enter it manually."
                            }
                        }
                    }
                },
                enabled = !isFetchingTitle
            ) {
                Text(
                    if (isFetchingTitle) "Fetching page title..." else "Fetch title from website",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // TITLE
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title *") },
                placeholder = {
                    Text("Give your bookmark a name")
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors
            )

            // DESCRIPTION
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Description") },
                placeholder = {
                    Text("What is this link about?")
                },
                minLines = 3,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors
            )

            // CATEGORY
            Column {
                Text(
                    "Category",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Box {
                    OutlinedButton(
                        onClick = {
                            categoryExpanded = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            category,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "⌄",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = {
                            categoryExpanded = false
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        item,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    category = item
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // COLLECTION
            Column {
                Text(
                    "Collection",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Box {
                    OutlinedButton(
                        onClick = {
                            collectionExpanded = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            selectedCollection?.name ?: "Unassigned",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "⌄",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    DropdownMenu(
                        expanded = collectionExpanded,
                        onDismissRequest = {
                            collectionExpanded = false
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Unassigned",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                selectedCollection = null
                                collectionExpanded = false
                            }
                        )

                        collections.forEach { collection ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        collection.name,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    selectedCollection = collection
                                    collectionExpanded = false
                                }
                            )
                        }
                    }
                }

                if (collections.isEmpty()) {
                    Text(
                        text = "Create a collection first to organize this bookmark.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // TAGS
            OutlinedTextField(
                value = tags,
                onValueChange = {
                    tags = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tags") },
                placeholder = {
                    Text("e.g. Kotlin, Programming, Tutorial")
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors
            )

            Text(
                "Separate multiple tags using commas.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ERROR MESSAGE
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp
                )
            }

            // SAVE BUTTON
            Button(
                onClick = {
                    val cleanUrl = url.trim()
                    val cleanTitle = title.trim()

                    if (cleanUrl.isBlank() || cleanTitle.isBlank()) {
                        errorMessage = "Please enter both URL and title."
                        return@Button
                    }

                    val normalizedUrl = when {
                        cleanUrl.startsWith("https://", true) ||
                                cleanUrl.startsWith("http://", true) -> cleanUrl

                        else -> "https://$cleanUrl"
                    }

                    val validUrl = android.util.Patterns.WEB_URL
                        .matcher(normalizedUrl)
                        .matches()

                    if (!validUrl) {
                        errorMessage = "Please enter a valid website URL."
                        return@Button
                    }

                    isSaving = true
                    errorMessage = null

                    val bookmark = Bookmark(
                        id = existingBookmark?.id ?: 0,
                        title = cleanTitle,
                        url = normalizedUrl,
                        description = description.trim(),
                        category = category,
                        tags = tags.trim(),
                        isFavorite = existingBookmark?.isFavorite ?: false,
                        collectionId = selectedCollection?.id,
                        createdAt = existingBookmark?.createdAt ?: System.currentTimeMillis()
                    )

                    val result: (String?) -> Unit = { error ->
                        isSaving = false
                        if (error == null) onBack() else errorMessage = error
                    }

                    if (existingBookmark == null) {
                        bookmarkViewModel.addBookmark(bookmark, result)
                    } else {
                        bookmarkViewModel.updateBookmark(bookmark, result)
                    }
                },

                enabled = !isSaving,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    if (isSaving) "Saving..." else if (existingBookmark == null) "Save Bookmark 🍂" else "Update Bookmark 🍂",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // CANCEL BUTTON
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Cancel")
            }
        }
    }
}
