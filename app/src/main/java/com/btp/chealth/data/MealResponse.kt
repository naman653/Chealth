package com.btp.chealth.data

data class MealResponse(
        var calorie: Int = 0,
        var protienItem: String = "",
        var protienQun: String = "",
        var fruitItem: String = "",
        var fruitQun: String = "",
        var vegeItem: String = "",
        var vegeQun: String = "",
        var grainItem: String = "",
        var grainQun: String = "",
        var dairyItem: String = "",
        var dairyQun: String = ""
)