package com.erica.gamsung.post.data.mock

import com.erica.gamsung.post.domain.Schedule
import com.erica.gamsung.post.domain.ScheduleList
import com.erica.gamsung.post.domain.ScheduleState
import com.erica.gamsung.post.domain.ScheduleStateList

// 로딩 문제 발생시를 위한 기본값
val beforeConnectPost =
    listOf(
        Post(
            id = 1,
            content = "",
        ),
        Post(
            id = 2,
            content = "",
        ),
        Post(
            id = 3,
            content = "",
        ),
    )

// GPT Contents 예시 (발행되는 글 3개가 여기에 들어가야 한다)
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

// Output 값을 서버에 전송할 때 사용한다.
val mockSchedules =
    ScheduleList(
        schedules =
            listOf(
                Schedule(
                    reservationId = 1,
                    date = "2024-04-01",
                    time = "12:00",
                    contents = mockPosts,
                    fixedContent = "Birthday Party",
                    imageUrl = listOf("Happy Anniversary!"),
                ),
                Schedule(
                    reservationId = 2,
                    date = "2024-04-02",
                    time = "18:00",
                    contents = mockPosts,
                    fixedContent = "Anniversary Dinner",
                    imageUrl = listOf("Happy Anniversary!"),
                ),
            ),
    )

// States -> 선택한 reservationId에 맞는 Schedule 서버에 요청
val mockSchedule =
    Schedule(
        reservationId = 1,
        date = "2024-04-01",
        time = "12:00",
        contents = mockPosts,
        fixedContent = "Birthday Party",
        imageUrl = listOf("Happy Anniversary!"),
    )

// 화면 진입 -> States list 서버에 요청
val mockStates =
    ScheduleStateList(
        scheduleState =
            listOf(
                ScheduleState(
                    reservationId = 1,
                    date = "2024-04-01",
                    time = "12:00",
                    state = "done",
                ),
                ScheduleState(
                    reservationId = 2,
                    date = "2024-04-02",
                    time = "18:00",
                    state = "done",
                ),
            ),
    )
