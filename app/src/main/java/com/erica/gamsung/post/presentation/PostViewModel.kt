package com.erica.gamsung.post.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.post.data.mock.Post
import com.erica.gamsung.post.data.mock.mockSchedule
import com.erica.gamsung.post.data.remote.PostApi
import com.erica.gamsung.post.domain.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postApi: PostApi,
    ) : ViewModel() {
        private val _contents = MutableLiveData<List<Post>>()
        val contents: LiveData<List<Post>> = _contents

        private val _postData = MutableLiveData<Schedule>()
        val postData: LiveData<Schedule> = _postData

        private val _reservationId = MutableLiveData<Int>()
        val reservationId: LiveData<Int> = _reservationId

        fun selectReservation(id: Int) {
            _reservationId.value = id
            Log.d("VM_Scope", "resID: ${_reservationId.value}")
        }

        fun fetchPostData(reservationId: Int) {
            viewModelScope.launch {
                try {
                    val response = postApi.fetchPostData(reservationId)
                    if (response.isSuccessful) {
                        val schedule = response.body()
                        _postData.value = schedule
                        _contents.value = schedule?.contents
                    }
                } catch (_: Exception) {
                }
            }
        }

        // 실제 통신에서는 reservationId를 사용한다
        fun fetchPostDataLocally() {
            val schedule = mockSchedule
            if (schedule != null) {
                _contents.value = schedule.contents
                _postData.value = schedule
            }
        }
        // 사용자 action 처리
    }
