package com.erica.gamsung.post.data.mock

import com.erica.gamsung.post.domain.Schedule
import com.erica.gamsung.post.domain.ScheduleList

val mockPosts =
    listOf(
        Post(
            id = 1,
            content =
                """Discover the beauty of our products and enjoy the latest technology.
            |Discover the beauty of our products and enjoy the latest technology.
            |Discover the beauty of our products and enjoy the latest technology.
            |Discover the beauty of our products and enjoy the latest technology.
            |Discover the beauty of our products and enjoy the latest technology.
                """.trimMargin(),
        ),
        Post(
            id = 2,
            content =
                """Learn how we're making a difference with sustainability at the heart of our products.
            |Learn how we're making a difference with sustainability at the heart of our products.
            |Learn how we're making a difference with sustainability at the heart of our products.
            |Learn how we're making a difference with sustainability at the heart of our products.
            |Learn how we're making a difference with sustainability at the heart of our products.
                """.trimMargin(),
        ),
        Post(
            id = 3,
            content =
                """
            |Join our community events and connect with other Gamsung enthusiasts.
            |Join our community events and connect with other Gamsung enthusiasts
            |Join our community events and connect with other Gamsung enthusiasts.
            |Join our community events and connect with other Gamsung enthusiasts.
            |
                """.trimMargin(),
        ),
    )
val mockSchedules =
    ScheduleList(
        schedules =
            listOf(
                Schedule(
                    reservationId = 1,
                    date = "2024-04-01",
                    time = "12:00",
                    menu = "Vegetarian Pizza",
                    event = "Birthday Party",
                    message = "Happy Birthday!",
                ),
                Schedule(
                    reservationId = 2,
                    date = "2024-04-02",
                    time = "18:00",
                    menu = "Chicken Curry",
                    event = "Anniversary Dinner",
                    message = "Happy Anniversary!",
                ),
            ),
    )
