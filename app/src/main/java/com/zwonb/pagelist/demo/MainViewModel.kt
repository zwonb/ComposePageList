package com.zwonb.pagelist.demo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var loading by mutableStateOf(false)
    var noMore by mutableStateOf(false)

    private var page = 1

    val pageData = mutableListOf<String>().apply {
        for (i in 0 until 5) {
            add("item #$i")
        }
    }

    fun nextPage() {
        page++
        addPageData()
    }

    private fun addPageData() {
        loading = true
        viewModelScope.launch {
            delay(2000)
            val size = pageData.size
            if (size > 40) {
                noMore = true
            } else {
                for (i in 0 until 10) {
                    pageData.add("item #${size + i}")
                }
            }
            loading = false
        }
    }

}