package com.example.smarthealthconsultingapp.eventbus

open class MessageEvent(
var str: String
) {
    fun getString(): String {
        return str
    }
}