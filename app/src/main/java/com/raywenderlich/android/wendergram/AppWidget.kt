/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.wendergram

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.AppWidgetTarget
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

  override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
      appWidgetIds: IntArray) {
    // There may be multiple widgets active, so update all of them
    for (appWidgetId in appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId)
    }
  }

  override fun onEnabled(context: Context) {
    // Enter relevant functionality for when the first widget is created
  }

  override fun onDisabled(context: Context) {
    // Enter relevant functionality for when the last widget is disabled
  }


  private fun loadWidgetPic(profilePicUrl: String, context: Context) {
  }

  fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
      appWidgetId: Int) {
    val profilePicUrl = "https://source.unsplash.com/random"

    // Construct the RemoteViews object
    val rvPortrait = RemoteViews(context.packageName, R.layout.app_widget)
    val rvLandscape = RemoteViews(context.packageName, R.layout.app_widget)
    rvLandscape.setTextViewText(R.id.appwidget_text, "landscape")
    rvPortrait.setTextViewText(R.id.appwidget_text, "portrait")

    // Construct AppWidgetTargets, not sure if separate ones are needed for orientation
    val awtPortrait = AppWidgetTarget(context, R.id.appwidget_imageview, rvPortrait, appWidgetId)
    val awtLandscape = AppWidgetTarget(context, R.id.appwidget_imageview, rvLandscape , appWidgetId)

//    val awt: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.appwidget_imageview,
//        view1, appWidgetId) {
//      override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
//        super.onResourceReady(resource, transition)
//      }
//    }

    Glide.with(context) //1
        .asBitmap()
        .load(profilePicUrl)
        .placeholder(R.drawable.ic_profile_placeholder)
        .error(R.drawable.ic_profile_placeholder)
        .skipMemoryCache(true) //2
        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
        .transform(CircleCrop()) //4
        .into(awtPortrait)

    Glide.with(context) //1
        .asBitmap()
        .load(profilePicUrl)
        .placeholder(R.drawable.ic_profile_placeholder)
        .error(R.drawable.ic_profile_placeholder)
        .skipMemoryCache(true) //2
        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
        .transform(CircleCrop()) //4
        .into(awtLandscape)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, RemoteViews(rvLandscape, rvPortrait))

/* swap comment the line above with this one to get widget to stop crashing
appWidgetManager.updateAppWidget(appWidgetId, rvPortrait)
 */
  }
}
