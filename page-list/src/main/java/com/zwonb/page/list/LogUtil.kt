package com.zwonb.page.list

import android.util.Log

/**
 * Created on 2021/1/23
 * @author zwonb
 */
fun loge(msg: String?, t: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.e("zwonb", msg, t)
    }
}