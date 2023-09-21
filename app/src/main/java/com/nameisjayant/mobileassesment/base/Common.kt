package com.nameisjayant.mobileassesment.base

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.nameisjayant.mobileassesment.data.models.ResponseModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun convertErrorJsonToObject(
    moshi: Moshi,
    jsonData: String
): ResponseModel? {
    val jsonAdapter: JsonAdapter<ResponseModel> = moshi.adapter(ResponseModel::class.java)
    return jsonAdapter.fromJson(jsonData)
}

fun getDate(
    timestamp: String
): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.US)
    val outputFormat = SimpleDateFormat("d MMM, yy hh:mm a", Locale.US)

    inputFormat.timeZone = TimeZone.getTimeZone("GMT+05:30")

    val parsedDate = inputFormat.parse(timestamp)

    return outputFormat.format(parsedDate ?: "")
}


fun downloadAndStoreFile(context: Context, fileUrl: String, fileName: String) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(Uri.parse(fileUrl))

    request.setTitle(fileName)
    request.setDescription("Downloading $fileName")

    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS, fileName
    )
    downloadManager.enqueue(request)
}