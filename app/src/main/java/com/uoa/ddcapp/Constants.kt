package com.uoa.ddcapp

import android.Manifest

object Constants {
    const val TAG="camerX"
    const val FILE_NAME_FORMAT="yy-MM-dd:HH-mm:ss-SSS"
    const val REQUEST_CODE_PERMISSIONS=123
    const val REQUEST_VIDEO_CAPTURE = 1
    val REQUIRED_PERMISSIONS= arrayOf(Manifest.permission.CAMERA)
}