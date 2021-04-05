package com.example.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.githubuseruiux"
    const val SCHEME = "content"

    internal class FavoriteUserColumn: BaseColumns{
        companion object{
            private const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "username"
            const val IMAGE = "image"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}