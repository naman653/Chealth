package com.btp.chealth.data

data class User (
        var userid: String = "",
        var age: Int = 0,
        var sex: String = "",
        var weight: Int = 0,
        var heightFoot: Int = 0,
        var heightInch: Int = 0,
        var bmi: Double = 0.0
)