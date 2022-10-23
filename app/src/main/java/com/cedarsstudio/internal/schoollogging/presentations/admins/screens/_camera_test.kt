package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.budiyev.android.codescanner.*
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.AuthStudentVM
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CameraTest(context: Context = LocalContext.current, vm: AuthStudentVM, onNavigate: (Boolean) -> Unit) {

    val TAG = "CAMERA_TEST"

    val cameraView = LayoutInflater.from(context)
        .inflate(R.layout.camera_activity, null, false) as CodeScannerView
    val codeScanner: CodeScanner = CodeScanner(context, cameraView).apply {
        camera = CodeScanner.CAMERA_BACK
        formats = listOf(BarcodeFormat.QR_CODE)
        autoFocusMode = AutoFocusMode.SAFE
        scanMode = ScanMode.SINGLE
        isAutoFocusEnabled = false
        isFlashEnabled = false
    }
    val coroutineScope = rememberCoroutineScope()

    val authType = vm.authType.value
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
            vm.authStudent(result.text, authType)
            coroutineScope.launch(Dispatchers.Main){
                onNavigate(true)
            }
        }
        codeScanner.errorCallback = ErrorCallback { result ->
            vm.setErrorState(result)
            coroutineScope.launch(Dispatchers.Main){
                onNavigate(true)
            }
        }
    }, modifier = Modifier.fillMaxSize())
}
