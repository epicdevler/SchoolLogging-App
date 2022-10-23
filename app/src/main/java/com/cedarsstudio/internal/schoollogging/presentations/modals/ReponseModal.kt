package com.cedarsstudio.internal.schoollogging.presentations.modals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.databinding.EventResponseModalLayoutBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView

class ResponseModal(context: Context) {

    private var alertDialog: AlertDialog
    private var builder: MaterialAlertDialogBuilder

    private var actionButton: MaterialButton
    private var infoText: MaterialTextView
    private var infoImage: LottieAnimationView
    private var binding: EventResponseModalLayoutBinding

    init {
        builder = MaterialAlertDialogBuilder(context)
        val view =
            LayoutInflater.from(context).inflate(R.layout.event_response_modal_layout, null, false)
        binding = EventResponseModalLayoutBinding.bind(view)
        builder.setView(view)

        actionButton = binding.responseAction
        infoText = binding.responseMsg
        infoImage = binding.responseImg

        alertDialog = builder.create()
//        alertDialog.

    }

    fun isLoading() {
        binding.apply {

        }
    }

    fun show(
        isSuccess: Boolean = false,
        msg: String,
        isCancellable: Boolean = false,
        action: () -> Unit = {}
    ) {
        alertDialog.setCancelable(isCancellable)
        actionButton.visibility = View.VISIBLE
        binding.responseMsg.text = msg
        binding.responseImg.repeatCount = 0
        val raw = if (isSuccess) R.raw.success else R.raw.error
        binding.responseImg.setAnimation(raw)
        if (!isSuccess) binding.responseAction.text = "Seen"
        alertDialog.show()
        binding.responseAction.setOnClickListener {
            if (isSuccess) action()
            else dismiss()
        }
    }

    fun showLoader(msg: String = "Loading", isCancellable: Boolean = false) {
        alertDialog.setCancelable(isCancellable)
        binding.apply {
            actionButton.visibility = View.GONE
            infoText.text = msg
            binding.responseImg.apply {
                repeatMode = LottieDrawable.RESTART
                this.repeatCount = LottieDrawable.INFINITE
                setAnimation(R.raw.loader_one)
            }
        }
        alertDialog.show()
    }

    fun dismiss() {
        binding.responseImg.cancelAnimation()
        alertDialog.dismiss()
        alertDialog.cancel()
    }
}