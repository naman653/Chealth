package com.btp.chealth.data

data class User (
        var userid: String = "",
        var age: String = "",
        var sex: String = "",
        var weight: String = "",
        var height: String = "",
        var bmi: String = "",
        var weightList: List<String> = mutableListOf(),
        var heightList: List<String> = mutableListOf(),
        var dateList: List<String> = mutableListOf()
)