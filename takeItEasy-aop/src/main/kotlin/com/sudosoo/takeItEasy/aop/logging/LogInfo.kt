package com.sudosoo.takeItEasy.aop.logging

class LogInfo(
    val url: String,
    val apiName: String,
    val method: String,
    val header: Map<String, String>,
    val parameters: String,
    val body: String,
    val ipAddress: String
) {
    var exception: String? = null
}