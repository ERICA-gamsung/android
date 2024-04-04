package com.erica.gamsung.post.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erica.gamsung.post.data.mock.Post
import com.erica.gamsung.post.data.mock.mockPosts

class PostViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>(mockPosts)
    val posts: LiveData<List<Post>> = _posts

    // 사용자 action 처리
}
