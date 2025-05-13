package com.fastcampus.blog.common.util

import java.util.*

fun String.toSlug() = lowercase(Locale.getDefault())
   .replace("\n", " ")
   .replace("[^a-z\\d\\s]".toRegex(), " ")
   .trim()
   .split(" ")
   .joinToString("-")
   .replace("-+".toRegex(), "-")
