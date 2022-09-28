package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.budiyev.android.codescanner.*
import com.cedarsstudio.internal.schoollogging.R

@Composable
fun CameraTest(onNavigate: (route: String, up: Boolean) -> Unit) {
//    ScannerCamera(onResult = { success, error ->
//
//        if (success != null)
//            Toast.makeText(context, "Scan result: ${success}", Toast.LENGTH_LONG).show()
//        if (error != null)
//            Toast.makeText(context, "Error result: ${success}", Toast.LENGTH_LONG).show()
//    })
    val context = LocalContext.current
    val cameraView = LayoutInflater.from(context)
        .inflate(R.layout.camera_activity, null, false) as CodeScannerView
    val codeScanner: CodeScanner = CodeScanner(context, cameraView).apply {
        camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        isAutoFocusEnabled = false // Whether to enable auto focus or not
        isFlashEnabled = false // Whether to enable flash or not
    }
    DisposableEffect(key1 = Unit) {
        codeScanner.startPreview()
        onDispose {
            codeScanner.stopPreview()
            codeScanner.releaseResources()
        }
    }
    AndroidView(factory = { cameraView }, update = {
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback { result ->
            Toast.makeText(context, "Error result: ${result.text}", Toast.LENGTH_LONG).show()
        }
        codeScanner.errorCallback = ErrorCallback { result -> // or ErrorCallback.SUPPRESS
            Toast.makeText(context, "Error result: ${result.message}", Toast.LENGTH_LONG).show()
        }
    }, modifier = Modifier.fillMaxSize())
}
