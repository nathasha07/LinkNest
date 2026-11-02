package com.example.linknest.backup

import com.example.linknest.data.Bookmark
import com.example.linknest.data.Collection
import org.json.JSONArray
import org.json.JSONObject

object BackupManager {
    fun encode(bookmarks: List<Bookmark>, collections: List<Collection>): String {
        val root = JSONObject()
        root.put("format", "LinkNest")
        root.put("version", 1)
        root.put("bookmarks", JSONArray().apply {
            bookmarks.forEach { item ->
                put(JSONObject().apply {
                    put("id", item.id)
                    put("title", item.title)
                    put("url", item.url)
                    put("description", item.description)
                    put("category", item.category)
                    put("tags", item.tags)
                    put("isFavorite", item.isFavorite)
                    if (item.collectionId == null) put("collectionId", JSONObject.NULL)
                    else put("collectionId", item.collectionId)
                    put("createdAt", item.createdAt)
                })
            }
        })
        root.put("collections", JSONArray().apply {
            collections.forEach { item ->
                put(JSONObject().apply {
                    put("id", item.id)
                    put("name", item.name)
                    put("description", item.description)
                    put("createdAt", item.createdAt)
                })
            }
        })
        return root.toString(2)
    }

    fun decode(json: String): Pair<List<Bookmark>, List<Collection>> {
        val root = JSONObject(json)
        require(root.optString("format") == "LinkNest") { "This is not a LinkNest backup file." }
        val bookmarks = buildList {
            val array = root.optJSONArray("bookmarks") ?: JSONArray()
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                val collectionId = if (item.isNull("collectionId")) null else item.optInt("collectionId")
                add(
                    Bookmark(
                        id = item.optInt("id"),
                        title = item.getString("title"),
                        url = item.getString("url"),
                        description = item.optString("description"),
                        category = item.optString("category", "General"),
                        tags = item.optString("tags"),
                        isFavorite = item.optBoolean("isFavorite"),
                        collectionId = collectionId,
                        createdAt = item.optLong("createdAt", System.currentTimeMillis())
                    )
                )
            }
        }
        val collections = buildList {
            val array = root.optJSONArray("collections") ?: JSONArray()
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                add(
                    Collection(
                        id = item.optInt("id"),
                        name = item.getString("name"),
                        description = item.optString("description"),
                        createdAt = item.optLong("createdAt", System.currentTimeMillis())
                    )
                )
            }
        }
        return bookmarks to collections
    }
}
