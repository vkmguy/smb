package com.example.widgetapplication

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.media.SoundPool
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import java.io.IOException

class MusicControllerService : Service(){

    private var stream1: Int = 0
    private var stream2: Int = 0
    private lateinit var soundPool: SoundPool
    private val sampleId1 = soundPool.load(this, R.raw.mixkitclassic, 1)
    val sampleId2 = soundPool.load(this, R.raw.mixkitfast, 1)

    override fun onCreate() {
        super.onCreate()
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(5)
            .build()

        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Log.i("media_app", "Loaded sample with id: $sampleId, status: $status")
        }
        stream1 = soundPool.play(sampleId1, 0.85F, 0.9F, 1, 0, 1F)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.e("MusicControllerService", "on start")
    }

    override fun onDestroy() {
        Log.e("MusicControllerService", "on destroy")
        super.onDestroy()
    }

    fun playEvent() {
        soundPool.release()
        soundPool.play(sampleId1, 0.85F, 0.9F, 1, 0, 1F)
    }

    fun nextEvent(mediaPlayer: MediaPlayer) {
        soundPool.play(sampleId2, 0.85F, 0.9F, 1, 0, 1F)
    }

    fun pauseEvent(mediaPlayer: MediaPlayer) {
        soundPool.pause(stream1)
    }

    fun stopEvent(mediaPlayer: MediaPlayer) {
        soundPool.pause(stream2)
    }

    private fun sendEventToWidget(action: String?) {
        val intent = Intent(this, NewAppWidget::class.java)
        intent.action = action
        intent.putExtra(
            "appWidgetIds", AppWidgetManager.getInstance(application).getAppWidgetIds(
                ComponentName(
                    application,
                    NewAppWidget::class.java
                )
            )
        )
        sendBroadcast(intent)
    }

}
