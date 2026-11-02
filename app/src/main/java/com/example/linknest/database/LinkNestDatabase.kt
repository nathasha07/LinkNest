package com.example.linknest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.linknest.data.Bookmark
import com.example.linknest.data.Collection
import com.example.linknest.data.CollectionDao

@Database(
    entities = [Bookmark::class, Collection::class],
    version = 2,
    exportSchema = false
)
abstract class LinkNestDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun collectionDao(): CollectionDao

    companion object {
        // Upgrades the original bookmark-only database without deleting saved links.
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bookmarks ADD COLUMN collectionId INTEGER")
                db.execSQL("CREATE TABLE IF NOT EXISTS collections (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, description TEXT NOT NULL, createdAt INTEGER NOT NULL)")
            }
        }
    }
}
