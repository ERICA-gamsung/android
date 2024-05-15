package com.erica.gamsung.core.presentation

sealed class Screen(
    val route: String,
) {
    data object Main : Screen("main")

    data object Setting : Screen("setting")

    data object PublishPosting : Screen("publishPosting")

    data object CheckPosting : Screen("checkPosting")

    data class InputStore(
        val isEditMode: Boolean,
    ) : Screen("inputStore/$isEditMode")

    data class InputMenu(
        val isEditMode: Boolean,
    ) : Screen("inputMenu/$isEditMode")

    data object DateSelect : Screen("dateSelect")

    data object TimeSelect : Screen("timeSelect")

    data object DateTimeListCheck : Screen("dateTimeListCheck")

    data object DateTimeFinish : Screen("dateTimeFinish")

    data object Login : Screen("login")

    data object SelectNewPost : Screen("selectPost")

    data object PreviewNewPost : Screen("previewPost")

    data object PostsStatus : Screen("postsStatus")
}
