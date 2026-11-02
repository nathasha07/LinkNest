
package com.example.linknest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.linknest.data.Bookmark
import com.example.linknest.data.Collection as BookmarkCollection
import com.example.linknest.database.DatabaseProvider
import com.example.linknest.repository.BookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.room.withTransaction

class BookmarkViewModel(application: Application) :
    AndroidViewModel(application) {

    private val database = DatabaseProvider.getDatabase(application)

    private val bookmarkDao = database.bookmarkDao()
    private val collectionDao = database.collectionDao()

    private val repository = BookmarkRepository(bookmarkDao)

    // All bookmarks
    val bookmarks = repository.getAllBookmarks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Favourite bookmarks
    val favorites = repository.getFavorites()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // All collections
    val collections = collectionDao.getAllCollections()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Add bookmark
    fun addBookmark(
        bookmark: Bookmark,
        onResult: (String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                repository.addBookmark(bookmark)
                onResult(null)
            } catch (e: Exception) {
                onResult(e.message ?: "Unable to save bookmark")
            }
        }
    }

    // Update bookmark
    fun updateBookmark(
        bookmark: Bookmark,
        onResult: (String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                repository.updateBookmark(bookmark)
                onResult(null)
            } catch (e: Exception) {
                onResult(e.message ?: "Unable to update bookmark")
            }
        }
    }

    suspend fun exportBookmarks(): List<Bookmark> = bookmarkDao.getAllBookmarksOnce()

    suspend fun exportCollections(): List<BookmarkCollection> = collectionDao.getAllCollectionsOnce()

    fun replaceAllData(
        importedBookmarks: List<Bookmark>,
        importedCollections: List<BookmarkCollection>,
        onResult: (String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                database.withTransaction {
                    bookmarkDao.deleteAllBookmarks()
                    collectionDao.deleteAllCollections()
                    collectionDao.insertCollections(importedCollections)
                    bookmarkDao.insertBookmarks(importedBookmarks)
                }
                onResult(null)
            } catch (e: Exception) {
                onResult(e.message ?: "Unable to restore backup")
            }
        }
    }

    // Delete bookmark
    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    // Create collection
    fun addCollection(collection: BookmarkCollection) {
        viewModelScope.launch {
            collectionDao.insertCollection(collection)
        }
    }

    // Rename collection
    fun renameCollection(collection: BookmarkCollection) {
        viewModelScope.launch {
            collectionDao.updateCollection(collection)
        }
    }

    // Delete collection and unassign its bookmarks
    fun deleteCollection(collection: BookmarkCollection) {
        viewModelScope.launch {
            bookmarkDao.unassignBookmarksFromCollection(collection.id)
            collectionDao.deleteCollection(collection)
        }
    }

    fun getBookmarksByCollection(collectionId: Int) =
        bookmarkDao.getBookmarksByCollection(collectionId)
}
