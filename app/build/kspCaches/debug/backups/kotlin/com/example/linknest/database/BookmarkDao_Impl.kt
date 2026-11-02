package com.example.linknest.database

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.linknest.`data`.Bookmark
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class BookmarkDao_Impl(
  __db: RoomDatabase,
) : BookmarkDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBookmark: EntityInsertAdapter<Bookmark>

  private val __insertAdapterOfBookmark_1: EntityInsertAdapter<Bookmark>

  private val __deleteAdapterOfBookmark: EntityDeleteOrUpdateAdapter<Bookmark>

  private val __updateAdapterOfBookmark: EntityDeleteOrUpdateAdapter<Bookmark>
  init {
    this.__db = __db
    this.__insertAdapterOfBookmark = object : EntityInsertAdapter<Bookmark>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `bookmarks` (`id`,`title`,`url`,`description`,`category`,`tags`,`isFavorite`,`collectionId`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Bookmark) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.url)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.category)
        statement.bindText(6, entity.tags)
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpCollectionId: Int? = entity.collectionId
        if (_tmpCollectionId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpCollectionId.toLong())
        }
        statement.bindLong(9, entity.createdAt)
      }
    }
    this.__insertAdapterOfBookmark_1 = object : EntityInsertAdapter<Bookmark>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `bookmarks` (`id`,`title`,`url`,`description`,`category`,`tags`,`isFavorite`,`collectionId`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Bookmark) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.url)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.category)
        statement.bindText(6, entity.tags)
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpCollectionId: Int? = entity.collectionId
        if (_tmpCollectionId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpCollectionId.toLong())
        }
        statement.bindLong(9, entity.createdAt)
      }
    }
    this.__deleteAdapterOfBookmark = object : EntityDeleteOrUpdateAdapter<Bookmark>() {
      protected override fun createQuery(): String = "DELETE FROM `bookmarks` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Bookmark) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfBookmark = object : EntityDeleteOrUpdateAdapter<Bookmark>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `bookmarks` SET `id` = ?,`title` = ?,`url` = ?,`description` = ?,`category` = ?,`tags` = ?,`isFavorite` = ?,`collectionId` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Bookmark) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.url)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.category)
        statement.bindText(6, entity.tags)
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpCollectionId: Int? = entity.collectionId
        if (_tmpCollectionId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpCollectionId.toLong())
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindLong(10, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertBookmark(bookmark: Bookmark): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfBookmark.insert(_connection, bookmark)
  }

  public override suspend fun insertBookmarks(bookmarks: List<Bookmark>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfBookmark_1.insert(_connection, bookmarks)
  }

  public override suspend fun deleteBookmark(bookmark: Bookmark): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfBookmark.handle(_connection, bookmark)
  }

  public override suspend fun updateBookmark(bookmark: Bookmark): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfBookmark.handle(_connection, bookmark)
  }

  public override fun getAllBookmarks(): Flow<List<Bookmark>> {
    val _sql: String = "SELECT * FROM bookmarks ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Bookmark> = mutableListOf()
        while (_stmt.step()) {
          val _item: Bookmark
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getFavorites(): Flow<List<Bookmark>> {
    val _sql: String = "SELECT * FROM bookmarks WHERE isFavorite = 1 ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Bookmark> = mutableListOf()
        while (_stmt.step()) {
          val _item: Bookmark
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllBookmarksOnce(): List<Bookmark> {
    val _sql: String = "SELECT * FROM bookmarks ORDER BY createdAt DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Bookmark> = mutableListOf()
        while (_stmt.step()) {
          val _item: Bookmark
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchBookmarks(query: String): Flow<List<Bookmark>> {
    val _sql: String = """
        |
        |        SELECT * FROM bookmarks
        |        WHERE title LIKE '%' || ? || '%'
        |        OR url LIKE '%' || ? || '%'
        |        OR tags LIKE '%' || ? || '%'
        |        ORDER BY createdAt DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        _argIndex = 3
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Bookmark> = mutableListOf()
        while (_stmt.step()) {
          val _item: Bookmark
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun findByUrl(url: String): Bookmark? {
    val _sql: String = "SELECT * FROM bookmarks WHERE url = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, url)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: Bookmark?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun findDuplicateUrl(url: String, excludeId: Int): Bookmark? {
    val _sql: String = "SELECT * FROM bookmarks WHERE url = ? AND id != ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, url)
        _argIndex = 2
        _stmt.bindLong(_argIndex, excludeId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: Bookmark?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getBookmarksByCollection(collectionId: Int): Flow<List<Bookmark>> {
    val _sql: String = "SELECT * FROM bookmarks WHERE collectionId = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, collectionId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCollectionId: Int = getColumnIndexOrThrow(_stmt, "collectionId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Bookmark> = mutableListOf()
        while (_stmt.step()) {
          val _item: Bookmark
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCollectionId: Int?
          if (_stmt.isNull(_columnIndexOfCollectionId)) {
            _tmpCollectionId = null
          } else {
            _tmpCollectionId = _stmt.getLong(_columnIndexOfCollectionId).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              Bookmark(_tmpId,_tmpTitle,_tmpUrl,_tmpDescription,_tmpCategory,_tmpTags,_tmpIsFavorite,_tmpCollectionId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllBookmarks() {
    val _sql: String = "DELETE FROM bookmarks"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun unassignBookmarksFromCollection(collectionId: Int) {
    val _sql: String = "UPDATE bookmarks SET collectionId = NULL WHERE collectionId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, collectionId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
