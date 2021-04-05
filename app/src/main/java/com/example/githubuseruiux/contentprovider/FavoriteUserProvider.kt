package com.example.githubuseruiux.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuseruiux.localdb.FavoriteUserDao
import com.example.githubuseruiux.localdb.UserDatabase

class FavoriteUserProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.example.githubuseruiux"
        private const val TABLE_NAME = "favorite_user"
        private const val FAVORITE_USER_DATA = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init{
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_USER_DATA)
        }
    }

    private lateinit var favoriteUserDao : FavoriteUserDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
       favoriteUserDao = context?.let{
           UserDatabase.getInstance(it)?.favoriteUserDao()
       }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
       var cursor : Cursor?
       when(uriMatcher.match(uri)){
           FAVORITE_USER_DATA ->{
               cursor = favoriteUserDao.getAll()
               if(context!=null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
               }
           }
           else ->{
               cursor = null
           }
       }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}