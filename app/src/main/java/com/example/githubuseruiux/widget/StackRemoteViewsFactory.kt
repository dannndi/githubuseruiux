package com.example.githubuseruiux.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Binder
import android.service.autofill.UserData
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.githubuseruiux.R
import com.example.githubuseruiux.localdb.UserDatabase
import com.example.githubuseruiux.model.User

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    companion object {
        const val AUTHORITY = "com.example.githubuseruiux"
        const val SCHEME = "content"
        private const val TABLE_NAME = "favorite_user"
        const val ID = "id"
        const val USERNAME = "username"
        const val IMAGE = "image"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_appwidget_preview))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_appwidget_preview))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_appwidget_preview))
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_appwidget_preview))

        val identityToken = Binder.clearCallingIdentity()

        // querying ke database
        var cursor = UserDatabase.getInstance(mContext)?.favoriteUserDao()?.getAll()
        val userList = java.util.ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val username =
                    getString(getColumnIndexOrThrow(USERNAME))
                val image =
                    getString(getColumnIndexOrThrow(IMAGE))
                userList.add(User(id, username, image))
            }
        }

        userList.forEach {
            Glide.with(mContext).asBitmap().load(it.image).into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        mWidgetItems.add(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                }
            )
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}