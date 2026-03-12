package com.example.appsicenet.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.appsicenet.datos.db.SicenetDatabase

class SicenetProvider : ContentProvider() {

    companion object {

        const val AUTHORITY = "com.example.sicenet.provider"

        val CONTENT_URI_CARGA =
            Uri.parse("content://$AUTHORITY/carga_academica")

        val CONTENT_URI_CARDEX =
            Uri.parse("content://$AUTHORITY/cardex")

        private const val CARGA = 1
        private const val CARDEX = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "carga_academica", CARGA)
            addURI(AUTHORITY, "cardex", CARDEX)
        }
    }

    private lateinit var db: SicenetDatabase

    override fun onCreate(): Boolean {
        db = SicenetDatabase.Companion.getDatabase(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        val database = db.openHelper.readableDatabase

        val table = when (uriMatcher.match(uri)) {
            CARGA -> "carga_academica"
            CARDEX -> "cardex"
            else -> throw IllegalArgumentException("URI no soportada: $uri")
        }

        val columns = projection?.joinToString(", ") ?: "*"

        val queryBuilder = StringBuilder()
        queryBuilder.append("SELECT $columns FROM $table")

        if (!selection.isNullOrEmpty()) {
            queryBuilder.append(" WHERE $selection")
        }

        if (!sortOrder.isNullOrEmpty()) {
            queryBuilder.append(" ORDER BY $sortOrder")
        }

        val query = SimpleSQLiteQuery(
            queryBuilder.toString(),
            selectionArgs
        )

        return database.query(query)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val database = db.openHelper.writableDatabase

        val table = when (uriMatcher.match(uri)) {
            CARGA -> "carga_academica"
            CARDEX -> "cardex"
            else -> throw IllegalArgumentException("URI no soportada")
        }

        val id = database.insert(table, 0, values ?: ContentValues())

        return ContentUris.withAppendedId(uri, id)
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {

        val database = db.openHelper.writableDatabase

        return when (uriMatcher.match(uri)) {

            CARGA -> database.delete(
                "carga_academica",
                selection,
                selectionArgs
            )

            CARDEX -> database.delete(
                "cardex",
                selection,
                selectionArgs
            )

            else -> throw IllegalArgumentException("URI no soportada")
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {

        val database = db.openHelper.writableDatabase

        val table = when (uriMatcher.match(uri)) {
            CARGA -> "carga_academica"
            CARDEX -> "cardex"
            else -> throw IllegalArgumentException("URI no soportada")
        }

        return database.update(
            table,
            0,
            values ?: ContentValues(),
            selection,
            selectionArgs
        )
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CARGA -> "vnd.android.cursor.dir/carga_academica"
            CARDEX -> "vnd.android.cursor.dir/cardex"
            else -> null
        }
    }
}