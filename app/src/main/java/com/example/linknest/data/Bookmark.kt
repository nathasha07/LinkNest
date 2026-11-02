
package com.example.linknest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val url: String,

    val description: String = "",

    val category: String = "General",

    val tags: String = "",

    val isFavorite: Boolean = false,

    val collectionId: Int? = null,

    val createdAt: Long = System.currentTimeMillis()
)
