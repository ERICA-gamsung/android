package com.erica.gamsung.core.presentation

enum class Screen(
    val route: String,
) {
    MAIN("main"),
    SETTING("setting"),
    PUBLISH_POSTING("publishPosting"),
    CHECK_POSTING("checkPosting"),
    INPUT_STORE("inputStore"),
    INPUT_MENU("inputMenu"),
    DATE_SELECT("dateSelect"),
    TIME_SELECT("timeSelect"),
    DATE_TIME_LIST_CHECK("dateTimeListCheck"),
}
