package com.ingenioustech.testapp1

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    var supabase: SupabaseClient
) {
    suspend fun uploadImage(imagePath: String) {
        val bucket = supabase.storage.from("tasks")
        val file = File(imagePath)
        if (!file.exists()) throw IllegalArgumentException("File does not exist: $imagePath")
        val byteArray = withContext(Dispatchers.IO) { file.readBytes() }
        bucket.upload("myIcon.png", byteArray) {
            upsert = false
        }
    }
    suspend fun downloadImage(fileName: String): String {
        return supabase.storage.from("tasks").publicUrl("")

    }
}