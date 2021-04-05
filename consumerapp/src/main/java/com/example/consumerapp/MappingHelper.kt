package com.example.consumerapp

import android.database.Cursor
import java.util.*

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.ID))
                val username =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.USERNAME))
                val image =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.IMAGE))
                userList.add(User(id, username, image))
            }
        }
        return userList
    }
}
