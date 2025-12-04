package com.vk.kmprecipeapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val AITags: String?,
    val description: String?,
    val fileId: String,
    val filePath: String,
    val fileType: String,
    val height: Int,
    val name: String,
    val size: Int,
    val thumbnailUrl: String,
    val url: String,
    val versionInfo: VersionInfo,
    val width: Int
)


@Serializable
data class VersionInfo(
    val id: String,
    val name: String
)