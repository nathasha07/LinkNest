package com.example.linknest.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
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
public class CollectionDao_Impl(
  __db: RoomDatabase,
) : CollectionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCollection: EntityInsertAdapter<Collection>

  private val __deleteAdapterOfCollection: EntityDeleteOrUpdateAdapter<Collection>

  private val __updateAdapterOfCollection: EntityDeleteOrUpdateAdapter<Collection>
  init {
    this.__db = __db
    this.__insertAdapterOfCollection = object : EntityInsertAdapter<Collection>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `collections` (`id`,`name`,`description`,`createdAt`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Collection) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        statement.bindLong(4, entity.createdAt)
      }
    }
    this.__deleteAdapterOfCollection = object : EntityDeleteOrUpdateAdapter<Collection>() {
      protected override fun createQuery(): String = "DELETE FROM `collections` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Collection) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfCollection = object : EntityDeleteOrUpdateAdapter<Collection>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `collections` SET `id` = ?,`name` = ?,`description` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Collection) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        statement.bindLong(4, entity.createdAt)
        statement.bindLong(5, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertCollection(collection: Collection): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCollection.insert(_connection, collection)
  }

  public override suspend fun insertCollections(collections: List<Collection>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCollection.insert(_connection, collections)
  }

  public override suspend fun deleteCollection(collection: Collection): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfCollection.handle(_connection, collection)
  }

  public override suspend fun updateCollection(collection: Collection): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCollection.handle(_connection, collection)
  }

  public override fun getAllCollections(): Flow<List<Collection>> {
    val _sql: String = "SELECT * FROM collections ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("collections")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Collection> = mutableListOf()
        while (_stmt.step()) {
          val _item: Collection
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = Collection(_tmpId,_tmpName,_tmpDescription,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllCollectionsOnce(): List<Collection> {
    val _sql: String = "SELECT * FROM collections ORDER BY createdAt DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<Collection> = mutableListOf()
        while (_stmt.step()) {
          val _item: Collection
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = Collection(_tmpId,_tmpName,_tmpDescription,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllCollections() {
    val _sql: String = "DELETE FROM collections"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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
