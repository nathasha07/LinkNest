
package com.example.linknest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.linknest.data.Collection
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel

@Composable
fun CollectionDetailsScreen(
    collection: Collection,
    viewModel: BookmarkViewModel,
    onBack: () -> Unit,
    onEditClick: (com.example.linknest.data.Bookmark) -> Unit = {}
) {
    val bookmarkFlow = remember(collection.id) {
        viewModel.getBookmarksByCollection(collection.id)
    }

    val bookmarks by bookmarkFlow.collectAsState(
        initial = emptyList()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        // BACK BUTTON
        TextButton(onClick = onBack) {
            Text(
                text = "← Back to Collections",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))

        // COLLECTION HEADER
        Text(
            text = "📂 ${collection.name}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (collection.description.isNotBlank()) {
            Spacer(Modifier.height(6.dp))

            Text(
                text = collection.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "${bookmarks.size} saved links",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(16.dp))

        if (bookmarks.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🍂",
                        style = MaterialTheme.typography.displayMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "This collection is empty",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Add bookmarks to this collection.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(
                    items = bookmarks,
                    key = { it.id }
                ) { bookmark ->

                    BookmarkCard(
                        bookmark = bookmark,
                        onFavoriteClick = {
                            viewModel.updateBookmark(
                                bookmark.copy(
                                    isFavorite = !bookmark.isFavorite
                                )
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
