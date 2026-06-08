package com.aura.app

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class TutorialDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Welcome to Aura")
            .setMessage(
                "Aura helps you tune into your daily focus vibe.\n\n" +
                "Choose a Focus Vibe in Settings:\n\n" +
                "• Calm Blue — for deep focus and tranquility.\n" +
                "• Energetic Orange — for high energy and motivation.\n" +
                "• Deep Purple — for creative and reflective thinking.\n\n" +
                "Your choice is saved automatically and reflected on the Home screen each time you open the app."
            )
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
