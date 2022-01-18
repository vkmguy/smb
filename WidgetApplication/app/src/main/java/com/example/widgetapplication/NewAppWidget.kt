package com.example.widgetapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetIds)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context, "New widget added!!", Toast.LENGTH_SHORT)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Last widget removed !!", Toast.LENGTH_SHORT)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val rnd = Random()
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(5)
            .build()
        val sampleId1 = soundPool.load(context, R.raw.mixkitclassic, 1)
        val sampleId2 = soundPool.load(context, R.raw.mixkitfast, 1)
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Log.i("media_app", "Loaded sample with id: $sampleId, status: $status")
        }
        var stream = 0
        when {
            intent!!.action.equals(context!!.getString(R.string.actionImage)) -> {
                val str = "img_" + rnd.nextInt(3)
                Log.e(str, "onReceive: next image")
                val views = RemoteViews(context.packageName, R.layout.new_app_widget)
                views.setImageViewResource(
                    R.id.imageView,
                    getResourceID(str, "drawable", context)
                )
            }
            intent.action.equals(context.getString(R.string.actionPlayMusic)) -> {
                stream = soundPool.play(sampleId1, 0.85F, 0.9F, 1, 0, 1F)
            }
            intent.action.equals(context.getString(R.string.actionNextMusic)) -> {
                soundPool.play(sampleId2, 0.85F, 0.9F, 1, 0, 1F)
            }
            intent.action.equals(context.getString(R.string.actionPauseMusic)) -> {
                soundPool.pause(stream)
            }
            intent.action.equals(context.getString(R.string.actionStopMusic)) -> {
                soundPool.stop(stream)
                soundPool.release()
            }
            intent.action.equals(context.getString(R.string.actionOpenNearestLocation)) -> {
                PendingIntent.getActivity(context, 0, intent, 0)

            }
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: IntArray
) {
    // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

    //intent for opening a website
    val siteIntent = Intent(Intent.ACTION_VIEW)
    siteIntent.data = Uri.parse(context.getString(R.string.pjwstkWebsite))

    val pendingIntentWebsite = PendingIntent.getActivity(
        context,
        0,
        siteIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    //setup setOnClickPendingIntent
    views.setOnClickPendingIntent(R.id.btBrowser, pendingIntentWebsite)

    // image view widget
    val imageIntent = Intent(context.getString(R.string.actionImage))
    imageIntent.component = ComponentName(context, NewAppWidget::class.java)
    val pendingIntentImage = PendingIntent.getActivity(
        context,
        0,
        imageIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btRandomImage, pendingIntentImage)

    // music intents
    val playIntent = Intent(context.getString(R.string.actionPlayMusic))
    playIntent.component = ComponentName(context, NewAppWidget::class.java)
    val pendingPlayIntentMusic = PendingIntent.getActivity(
        context,
        0,
        playIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btPlay, pendingPlayIntentMusic)

    val pauseIntent = Intent(context.getString(R.string.actionPauseMusic))
    pauseIntent.component = ComponentName(context, NewAppWidget::class.java)
    val pendingPauseIntentMusic = PendingIntent.getActivity(
        context,
        0,
        pauseIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btPause, pendingPauseIntentMusic)

    val nextIntent = Intent(context.getString(R.string.actionNextMusic))
    nextIntent.component = ComponentName(context, NewAppWidget::class.java)
    val pendingNextIntentMusic = PendingIntent.getActivity(
        context,
        0,
        nextIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btNext, pendingNextIntentMusic)

    val stopIntent = Intent(context.getString(R.string.actionStopMusic))
    stopIntent.component = ComponentName(context, NewAppWidget::class.java)
    val pendingStopIntentMusic = PendingIntent.getActivity(
        context,
        0,
        stopIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btStop, pendingStopIntentMusic)


    val gmmIntentUri = Uri.parse("geo:52.1702612,21.0045064")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps");
    val pendingOpenNearestLocation = PendingIntent.getActivity(
        context,
        0,
        mapIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btLocation, pendingOpenNearestLocation)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun getResourceID(resName: String, resType: String?, ctx: Context): Int {
    val resourceID = ctx.resources.getIdentifier(
        resName, resType,
        ctx.applicationInfo.packageName
    )
    return if (resourceID == 0) {
        throw IllegalArgumentException(
            "No resource string found with name $resName"
        )
    } else {
        resourceID
    }
}