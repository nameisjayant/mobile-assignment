package com.nameisjayant.mobileassesment.data.models

import com.squareup.moshi.Json

data class ResponseModel(
    val code: Int? = null,
    val message: String? = null,
    val status: Boolean? = false,
    val data: List<DataResponse>? = null
)

data class DataResponse(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val section: String? = null,
    val tags: List<Tags>? = null,
    val file: String? = null,
    val version: Int? = null,
    val status: Int? = null,
    @Json(name = "comment_count")
    val commentCount: Int? = null,
    @Json(name = "uploaded_at")
    val uploadedAt: String? = null,
    @Json(name = "uploaded_by")
    val uploadedBy: UploadedBy? = null,
    @Json(name = "file_size")
    val fileSize: String? = null,
    // these variable are for downloading and progress bar
    var isDownloaded: Boolean? = false,
    var isDownloading: Boolean? = false,
    var progress: Int? = 0
)

data class UploadedBy(
    val id: String? = null,
    val name: String? = null,
    val photo: String? = null,
    @Json(name = "organization_name")
    val organizationName: String? = null
)

data class Tags(
    val id: String? = null,
    val name: String? = null
)
