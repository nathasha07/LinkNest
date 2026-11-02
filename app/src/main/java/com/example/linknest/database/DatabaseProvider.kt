package com.example.linknest.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: LinkNestDatabase? = null

    fun getDatabase(context: Context): LinkNestDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LinkNestDatabase::class.java,
                "linknest_database"
            )
                .addMigrations(LinkNestDatabase.MIGRATION_1_2)
                .build()
                .also { INSTANCE = it }
        }
    }
}
