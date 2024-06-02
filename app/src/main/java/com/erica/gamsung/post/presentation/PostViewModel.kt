package com.erica.gamsung.post.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.post.data.mock.Post
import com.erica.gamsung.post.data.remote.PostApi
import com.erica.gamsung.post.domain.Schedule
import com.erica.gamsung.post.domain.ScheduleState
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught", "MagicNumber")
@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postApi: PostApi,
    ) : ViewModel() {
        private val _contents = MutableLiveData<List<String>>()
        val contents: LiveData<List<String>> = _contents

        private val _content = MutableLiveData<String>()
        val content: LiveData<String> = _content

        private val _postData = MutableLiveData<Schedule>()
        val postData: LiveData<Schedule> = _postData

        private val _reservationId = MutableLiveData<Int>()
        val reservationId: LiveData<Int> = _reservationId

        private val _postListData = MutableLiveData<List<ScheduleState>>()
        val postListData: LiveData<List<ScheduleState>> = _postListData

//        private val _imgUri = MutableLiveData<Uri?>()
//        val imgUri: LiveData<Uri?> = _imgUri

        private val _imgBitMap = MutableLiveData<Bitmap>()
        val imgBitMap: LiveData<Bitmap> = _imgBitMap

        fun getPostListSize(): Int {
            return _postListData.value?.size ?: 0
        }

        fun getFilteredPostListSize(filterState: String): Int {
            return _postListData.value?.filter { it.state == filterState }?.size ?: 0
        }

        fun setImg(bitmap: Bitmap?) {
            _imgBitMap.value = bitmap
            Log.d("ViewModel", "Setting image URI: $bitmap")
        }

        fun setContent(content: String) {
            _content.value = content
            Log.d("ViewModel", "Set Content: $content")
        }
//        fun setImageUri(uri: Uri?){
//            _imgUri.value = uri
//            Log.d("ViewModel", "Setting image URI: $uri")
//        }

        fun selectReservation(id: Int) {
            _reservationId.value = id
            Log.d("VM_Scope", "resID: ${_reservationId.value}")
        }

        fun fetchPostListData() {
            viewModelScope.launch {
                try {
                    val response = postApi.fetchPostListData()
                    if (response.isSuccessful) {
                        _postListData.value = response.body()
                    } else {
                        Log.d(
                            "API_Error",
                            "Error Code: ${response.code()} Error Body: ${response.errorBody()?.string()}",
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "API_Exception",
                        "Exception during API call: ${e.message}",
                    )
                }
            }
        }

        fun fetchPostData(reservationId: Int) {
            viewModelScope.launch {
                try {
                    val response = postApi.fetchPostData(reservationId)
                    if (response.isSuccessful) {
                        val schedule = response.body()
                        _postData.value = schedule
                        _contents.value = schedule?.contents
                    } else {
                        Log.d(
                            "API_Error",
                            "Error Code: ${response.code()} Error Body: ${response.errorBody()?.string()}",
                        )
                    }
                } catch (e: Exception) {
                    Log.e("API_Exception", "Exception during API call: ${e.message}")
                }
            }
        }

        fun confirmPostData(
            reservationId: Int?,
            content: String?,
            imgBitmap: Bitmap?,
        ) {
            viewModelScope.launch {
                try {
                    reservationId?.let { resId ->
                        imgBitmap?.let {
                            val imgByte = bitmapToByteArray(it)
                            val requestBody = imgByte.toRequestBody("image/jpeg".toMediaTypeOrNull())
                            val formData = MultipartBody.Part.createFormData("files", "image.jpg", requestBody)
                            val filesList = listOf(formData)
                            val response = postApi.confirmImgData(resId, filesList)
                            if (imgByte.isNotEmpty()) {
                                if (response.isSuccessful) {
                                    val responseBody = response.body()
                                    Log.d("ViewModel", "Image upload successful: $responseBody")
                                } else {
                                    Log.e("ViewModel", "Error in image upload: ${response.errorBody()?.string()}")
                                }
                            } else {
                                Log.e("ViewModel", "Image byte array is empty, skipping upload")
                            }
                            postApi.confirmPostData(resId, Post(content))
                        } ?: Log.e("ViewModel", "Image Bitmap is null, skipping upload")
                    } ?: Log.e("ViewModel", "Reservation ID is null, cannot upload data")
                } catch (e: IOException) {
                    Log.e("ViewModel", "Network error in confirmPostData: ${e.message}")
                } catch (e: JsonParseException) {
                    Log.e("ViewModel", "JSON parsing error in confirmPostData: ${e.message}")
                } catch (e: IllegalArgumentException) {
                    Log.e("ViewModel", "Illegal argument error in confirmPostData: ${e.message}")
                }
            }
        }

        private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
            ByteArrayOutputStream().apply {
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, this)
                return toByteArray()
            }
        }
        // 실제 통신에서는 reservationId를 사용한다
//        fun fetchPostDataLocally() {
//            val schedule = mockSchedule
//            _contents.value = schedule.contents
//            _postData.value = schedule
//        }
        // 사용자 action 처리
    }
