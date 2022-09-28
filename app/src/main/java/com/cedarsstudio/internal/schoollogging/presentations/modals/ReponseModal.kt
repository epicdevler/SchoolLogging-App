package com.cedarsstudio.internal.schoollogging.presentations.modals

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
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
        binding.responseMsg.text = msg
        binding.responseImg.setAnimation(if (isSuccess) R.raw.success else R.raw.error)
        if (!isSuccess) binding.responseAction.text = "Seen"
        alertDialog.show()
        binding.responseAction.setOnClickListener {
            if (isSuccess) action()
            else dismiss()
        }
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

}