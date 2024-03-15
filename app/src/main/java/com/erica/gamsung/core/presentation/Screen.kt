package com.erica.gamsung.core.presentation

sealed class Screen(
    val route: String,
) {
    data object Main : Screen("main")

    data object Setting : Screen("setting")

    data object PublishPosting : Screen("publishPosting")

    data object CheckPosting : Screen("checkPosting")

    data object InputStore : Screen("inputStore")

    data object InputMenu : Screen("inputMenu")

    data object DateSelect : Screen("dateSelect")

    data object TimeSelect : Screen("timeSelect")

    data object DateTimeListCheck : Screen("dateTimeListCheck")

    data object Login : Screen("login")
}
