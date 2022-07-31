package com.example.smarthealthconsultingapp.eventbus

class StringEvent(
    str: String, private val data: String
): MessageEvent(str) {
    fun getDataString(): String {
        return data
    }
}