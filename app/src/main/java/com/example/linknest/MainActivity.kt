
package com.example.linknest

import com.example.linknest.data.Collection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.linknest.ui.theme.ThemePreferences
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linknest.data.Bookmark
import com.example.linknest.ui.screens.*
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val themeMode by ThemePreferences.themeMode(context).collectAsState(
                initial = ThemePreferences.SYSTEM
            )
            val systemDark = androidx.compose.foundation.isSystemInDarkTheme()
            val useDarkTheme = when (themeMode) {
                ThemePreferences.LIGHT -> false
                ThemePreferences.DARK -> true
                else -> systemDark
            }

            LinkNestTheme(darkTheme = useDarkTheme) {
                val bookmarkViewModel: BookmarkViewModel = viewModel()

                var selectedTab by remember {
                    mutableStateOf("Home")
                }

                var showAddScreen by remember {
                    mutableStateOf(false)
                }

                var selectedCollection by remember { mutableStateOf<Collection?>(null) }
                var editingBookmark by remember { mutableStateOf<Bookmark?>(null) }

                if (showAddScreen) {
                    AddBookmarkScreen(
                        onBack = {
                            showAddScreen = false
                            editingBookmark = null
                        },
                        bookmarkViewModel = bookmarkViewModel,
                        existingBookmark = editingBookmark
                    )

                } else if (selectedTab == "Home") {

                    HomeScreen(
                        bookmarkViewModel = bookmarkViewModel,
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        onAddClick = {
                            editingBookmark = null
                            showAddScreen = true
                        },
                        onEditClick = { bookmark ->
                            editingBookmark = bookmark
                            showAddScreen = true
                        }
                    )

                } else {

                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.background,
                        bottomBar = {
                            LinkNestBottomBar(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    ) { padding ->

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {

                            when (selectedTab) {

                                "Collections" -> {
                                    if (selectedCollection != null) {
                                        CollectionDetailsScreen(
                                            collection = selectedCollection!!,
                                            viewModel = bookmarkViewModel,
                                            onBack = {
                                                selectedCollection = null
                                            },
                                            onEditClick = { bookmark ->
                                                editingBookmark = bookmark
                                                showAddScreen = true
                                            }
                                        )
                                    } else {
                                        CollectionsScreen(
                                            viewModel = bookmarkViewModel,
                                            onCollectionClick = { collection ->
                                                selectedCollection = collection
                                            }
                                        )
                                    }
                                }

                                "Favorites" -> {
                                    FavoritesScreen(
                                        viewModel = bookmarkViewModel,
                                        onEditClick = { bookmark ->
                                            editingBookmark = bookmark
                                            showAddScreen = true
                                        }
                                    )
                                }

                                "Settings" -> {
                                    SettingsScreen(
                                        selectedTheme = themeMode,
                                        onThemeSelected = { mode ->
                                            lifecycleScope.launch {
                                                ThemePreferences.setThemeMode(this@MainActivity, mode)
                                            }
                                        },
                                        viewModel = bookmarkViewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LinkNestBottomBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val tabs = listOf(
        Triple("Home", "⌂", "Home"),
        Triple("Collections", "▦", "Collections"),
        Triple("Favorites", "♥", "Favorites"),
        Triple("Settings", "⚙", "Settings")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        tabs.forEach { (tab, icon, label) ->

            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Text(icon, fontSize = MaterialTheme.typography.titleLarge.fontSize)
                },
                label = {
                    Text(label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}

@Composable
fun FavoritesScreen(
    viewModel: BookmarkViewModel,
    onEditClick: (Bookmark) -> Unit = {}
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "My Favorites ♥",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(20.dp))

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No favorite bookmarks yet.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites.size) { index ->
                    val bookmark = favorites[index]

                    BookmarkCard(
                        bookmark = bookmark,
                        onFavoriteClick = {
                            viewModel.updateBookmark(
                                bookmark.copy(isFavorite = false)
                            )
                        },
                        onDeleteClick = {
                            viewModel.deleteBookmark(bookmark)
                        },
                        onEditClick = { onEditClick(bookmark) }
                    )
                }
            }
        }
    }
}

