package com.mzakialkhairi.manga.model

data class DetailChapterResponse(
    val chapter_endpoint: String,
    val chapter_image: List<ChapterImage>,
    val chapter_pages: Int
)