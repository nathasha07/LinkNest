package com.example.linknest.repository

import com.example.linknest.data.Bookmark
import com.example.linknest.database.BookmarkDao
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    fun getAllBookmarks(): Flow<List<Bookmark>> = bookmarkDao.getAllBookmarks()
    fun getFavorites(): Flow<List<Bookmark>> = bookmarkDao.getFavorites()
    fun searchBookmarks(query: String): Flow<List<Bookmark>> = bookmarkDao.searchBookmarks(query)

    suspend fun addBookmark(bookmark: Bookmark) {
        require(bookmarkDao.findByUrl(bookmark.url) == null) {
            "This link is already saved!"
        }
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun updateBookmark(bookmark: Bookmark) {
        require(bookmarkDao.findDuplicateUrl(bookmark.url, bookmark.id) == null) {
            "Another bookmark already uses this URL."
        }
        bookmarkDao.updateBookmark(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) = bookmarkDao.deleteBookmark(bookmark)
}
