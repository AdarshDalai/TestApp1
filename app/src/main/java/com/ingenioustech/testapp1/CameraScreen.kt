package com.ingenioustech.testapp1

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ImagePickerScreen(
    viewModel: CameraViewModel = hiltViewModel()
) {
    //upload image to supabase storage
    // Registers a photo picker activity launcher in single-select mode.
    var pickedImageUri: String? = null
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Image Picker Screen",
        )

        Button(
            onClick = {
                // Launches the photo picker and allows the user to select only images.
                pickMedia.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Text(text = "Pick an image")
        }
        Card {
            Text("Image Preview will be shown here")
            //Parsing Uri to Image
            AsyncImage(
                model = pickedImageUri, // Replace with your image URL or URI
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = {
                // Handle the upload logic here
                // For example, upload pickedImageUri to Supabase storage
                Log.d("ImagePicker", "Upload image: $pickedImageUri")
                coroutineScope.launch {
                    viewModel.uploadImage(pickedImageUri!!)
                }
            }
        ) {
            Text(text = "Upload Image")
        }

        Card {
            Text("Uploaded Image Preview")

        }
    }

}