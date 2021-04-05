package com.example.githubuseruiux.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetServices : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}