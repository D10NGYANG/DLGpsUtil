package com.d10ng.gps.demo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtils {

    const val NOTIFY_ID = 2001

    fun buildNotification(context: Context): Notification? {
        try {
            val mContext = context.applicationContext
            val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = if (Build.VERSION.SDK_INT >= 26) {
                //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏

                val channelId = mContext.packageName
                val notificationChannel = NotificationChannel(
                    channelId,
                    "导航",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(
                    notificationChannel
                )
                Notification.Builder(mContext, channelId)
            } else {
                Notification.Builder(mContext)
            }
            builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GPS导航")
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis())
            return builder.build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}