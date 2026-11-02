
package com.example.linknest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.linknest.data.Bookmark
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel

@Composable
fun HomeScreen(
    bookmarkViewModel: BookmarkViewModel,
    onAddClick: () -> Unit = {},
    selectedTab: String = "Home",
    onTabSelected: (String) -> Unit = {},
    onEditClick: (Bookmark) -> Unit = {}
) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var sortOption by remember { mutableStateOf("Newest first") }
    var sortExpanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "All",
        "Learning",
        "Work",
        "Entertainment",
        "General"
    )

    val filteredBookmarks = bookmarks.filter { bookmark ->
        val matchesSearch =
            bookmark.title.contains(searchText, ignoreCase = true) ||
                    bookmark.url.contains(searchText, ignoreCase = true) ||
                    bookmark.tags.contains(searchText, ignoreCase = true) ||
                    bookmark.category.contains(searchText, ignoreCase = true) ||
                    bookmark.description.contains(searchText, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    bookmark.category == selectedCategory

        matchesSearch && matchesCategory
    }

    val displayedBookmarks = when (sortOption) {
        "Oldest first" -> filteredBookmarks.sortedBy { it.createdAt }
        "Title A–Z" -> filteredBookmarks.sortedBy { it.title.lowercase() }
        "Title Z–A" -> filteredBookmarks.sortedByDescending { it.title.lowercase() }
        else -> filteredBookmarks.sortedByDescending { it.createdAt }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Text("+", fontSize = 30.sp)
            }
        },

        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                NavigationBarItem(
                    selected = selectedTab == "Home",
                    onClick = { onTabSelected("Home") },
                    icon = {
                        Text("⌂", fontSize = 22.sp)
                    },
                    label = {
                        Text("Home")
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "Collections",
                    onClick = { onTabSelected("Collections") },
                    icon = {
                        Text("▦", fontSize = 22.sp)
                    },
                    label = {
                        Text("Collections")
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "Favorites",
                    onClick = { onTabSelected("Favorites") },
                    icon = {
                        Text("♥", fontSize = 22.sp)
                    },
                    label = {
                        Text("Favorites")
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "Settings",
                    onClick = { onTabSelected("Settings") },
                    icon = {
                        Text("⚙", fontSize = 22.sp)
                    },
                    label = {
                        Text("Settings")
                    },
                    colors = navigationColors()
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 24.dp,
                bottom = 90.dp
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // HEADER
            item {
                Column {
                    Text(
                        text = "Good day, sunshine! ☀️",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Your LinkNest 🍂",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "A cozy place for everything worth saving.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // SEARCH BAR
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Search your saved links...")
                    },
                    leadingIcon = {
                        Text("⌕", fontSize = 24.sp)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            // CATEGORIES
            item {
                Column {
                    Text(
                        text = "Your collections",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.horizontalScroll(
                            rememberScrollState()
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = {
                                    selectedCategory = category
                                },
                                label = {
                                    Text(
                                        text = category,
                                        maxLines = 1
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    labelColor = MaterialTheme.colorScheme.onSurface,
                                    selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // RECENTLY SAVED HEADER
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recently saved",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${displayedBookmarks.size} links",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                        Box {
                            TextButton(onClick = { sortExpanded = true }) {
                                Text("Sort", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                            }
                            DropdownMenu(
                                expanded = sortExpanded,
                                onDismissRequest = { sortExpanded = false },
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 6.dp
                            ) {
                                listOf("Newest first", "Oldest first", "Title A–Z", "Title Z–A").forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontWeight = if (sortOption == option) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            sortOption = option
                                            sortExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // BOOKMARK LIST
            if (displayedBookmarks.isEmpty()) {
                item {
                    EmptyBookmarks()
                }
            } else {
                items(
                    items = displayedBookmarks,
                    key = { it.id }
                ) { bookmark ->
                    BookmarkCard(
                        bookmark = bookmark,
                        onFavoriteClick = {
                            bookmarkViewModel.updateBookmark(
                                bookmark.copy(
                                    isFavorite = !bookmark.isFavorite
                                )
                            )
                        },
                        onDeleteClick = {
                            bookmarkViewModel.deleteBookmark(bookmark)
                        },
                        onEditClick = { onEditClick(bookmark) }
                    )
                }
            }
        }
    }
}

@Composable
private fun navigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = MaterialTheme.colorScheme.surfaceVariant
    )

// BOOKMARK CARD
@Composable
fun BookmarkCard(
    bookmark: Bookmark,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // BOOKMARK HEADER
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🔗", fontSize = 20.sp)
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = bookmark.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )

                    Text(
                        text = bookmark.category,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = if (bookmark.isFavorite) "♥" else "♡",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    modifier = Modifier.clickable {
                        onFavoriteClick()
                    }
                )
            }

            Spacer(Modifier.height(12.dp))

            // CLICKABLE URL
            Text(
                text = bookmark.url,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    try {
                        uriHandler.openUri(bookmark.url)
                    } catch (e: Exception) {
                        // Browser could not open the URL
                    }
                }
            )

            // DESCRIPTION
            if (bookmark.description.isNotBlank()) {
                Spacer(Modifier.height(6.dp))

                Text(
                    text = bookmark.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // ACTION BUTTONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        try { uriHandler.openUri(bookmark.url) } catch (_: Exception) { }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) { Text("Open ↗") }

                TextButton(onClick = {
                    clipboardManager.setText(AnnotatedString(bookmark.url))
                }) { Text("Copy", color = MaterialTheme.colorScheme.secondary) }

                TextButton(onClick = {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "${bookmark.title}\n${bookmark.url}")
                    }
                    context.startActivity(Intent.createChooser(sendIntent, "Share bookmark"))
                }) { Text("Share", color = MaterialTheme.colorScheme.secondary) }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEditClick) {
                    Text("Edit", color = MaterialTheme.colorScheme.onSurface)
                }
                TextButton(onClick = onDeleteClick) {
                    Text("Delete", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

// EMPTY BOOKMARK SCREEN
@Composable
fun EmptyBookmarks() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "🍂",
            fontSize = 48.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Your nest is still empty!",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Save your first link and keep it close.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )
    }
}
