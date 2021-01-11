package com.danshrout.projectmanager.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat.startActivityForResult
import com.danshrout.projectmanager.activities.MyProfileActivity

object Constants {
    const val USERS: String = "users"
    const val BOARDS: String = "boards"
    const val IMAGE: String = "image"
    const val NAME: String = "name"
    const val MOBILE: String = "mobile"
    // Unique code for asking the Read Storage Permission. Check and identify
    const val READ_STORAGE_PERMISSION_CODE = 1
    const val PICK_IMAGE_REQUEST_CODE = 2

    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
    // Function to find out what kind of file the uri is
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        // MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
        // getSingleton(): Get the singleton instance of MimeTypeMap.
        // getExtensionFromMimeType: Return the registered extension for the given MIME type.
        // contentResolver.getType: Return the MIME type of the given content URL.
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}
