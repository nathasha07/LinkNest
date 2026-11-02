
package com.example.linknest.database

import androidx.room.*
import com.example.linknest.data.Bookmark
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavorites(): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(bookmarks: List<Bookmark>)

    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    suspend fun getAllBookmarksOnce(): List<Bookmark>

    @Query("DELETE FROM bookmarks")
    suspend fun deleteAllBookmarks()

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("""
        SELECT * FROM bookmarks
        WHERE title LIKE '%' || :query || '%'
        OR url LIKE '%' || :query || '%'
        OR tags LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchBookmarks(query: String): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE url = :url LIMIT 1")
    suspend fun findByUrl(url: String): Bookmark?

    @Query("SELECT * FROM bookmarks WHERE url = :url AND id != :excludeId LIMIT 1")
    suspend fun findDuplicateUrl(url: String, excludeId: Int): Bookmark?


    @Query("UPDATE bookmarks SET collectionId = NULL WHERE collectionId = :collectionId")
    suspend fun unassignBookmarksFromCollection(collectionId: Int)

    @Query("SELECT * FROM bookmarks WHERE collectionId = :collectionId ORDER BY createdAt DESC")
    fun getBookmarksByCollection(collectionId: Int): kotlinx.coroutines.flow.Flow<List<Bookmark>>
}
