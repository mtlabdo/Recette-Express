package com.example.androidtest.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


fun openUrl(context: Context, videoUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
    context.startActivity(intent)
}