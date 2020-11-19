package com.btp.chealth.data

data class MealResponse(
        var Mon: DayMeal,
        var Tues: DayMeal,
        var Wed: DayMeal,
        var Thurs: DayMeal,
        var Fri: DayMeal,
        var Sat: DayMeal,
        var Sun: DayMeal
)