package com.ramonaharrison.mininytimes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Section(val results: List<Result>)

@JsonClass(generateAdapter = true)
data class Result(val title: String,
                  val abstract: String,
                  val url: String,
                  val multimedia: List<Multimedia>)

@JsonClass(generateAdapter = true)
data class Multimedia(val format: String,
                      val url: String)
