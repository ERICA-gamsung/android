package com.erica.gamsung.post.data.mock

import com.erica.gamsung.post.domain.Schedule
import com.erica.gamsung.post.domain.ScheduleList

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
