package com.fastcampus.blog.common.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun String.toSlug() = lowercase(Locale.getDefault())
   .replace("\n", " ")
   .replace("[^a-z\\d\\s]".toRegex(), " ")
   .trim()
   .split(" ")
   .joinToString("-")
   .replace("-+".toRegex(), "-")

fun LocalDateTime.toDate(): Date {
   val zoneId = ZoneId.systemDefault();
   val zoneDateTime = atZone(zoneId)
   return Date.from(zoneDateTime.toInstant())
}
