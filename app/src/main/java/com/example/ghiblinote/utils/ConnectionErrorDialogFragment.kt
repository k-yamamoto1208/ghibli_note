package com.example.ghiblinote.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.ghiblinote.R
import java.lang.IllegalStateException

class ConnectionErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.communication_error_dialog_title)
                .setMessage(R.string.communication_error_dialog_message)
                .setPositiveButton(
                    R.string.communication_error_dialog_positive_button
                ) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity is null")
    }
}