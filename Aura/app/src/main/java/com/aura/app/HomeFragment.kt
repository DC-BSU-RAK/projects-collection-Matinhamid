package com.aura.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aura.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTutorial.setOnClickListener {
            TutorialDialog().show(parentFragmentManager, "TutorialDialog")
        }

        updateAuraCircle()
    }

    override fun onResume() {
        super.onResume()
        updateAuraCircle()
    }

    private fun updateAuraCircle() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val vibe = prefs.getString(KEY_VIBE, VIBE_CALM)

        val color = when (vibe) {
            VIBE_CALM -> requireContext().getColor(R.color.aura_calm)
            VIBE_ENERGETIC -> requireContext().getColor(R.color.aura_energetic)
            VIBE_DEEP -> requireContext().getColor(R.color.aura_deep)
            else -> requireContext().getColor(R.color.aura_calm)
        }

        val label = when (vibe) {
            VIBE_CALM -> "Calm Blue"
            VIBE_ENERGETIC -> "Energetic Orange"
            VIBE_DEEP -> "Deep Purple"
            else -> "Calm Blue"
        }

        binding.auraCircle.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(color)
        )
        binding.tvGreeting.text = "Your vibe today: $label"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PREFS_NAME = "aura_prefs"
        const val KEY_VIBE = "selected_vibe"
        const val VIBE_CALM = "calm"
        const val VIBE_ENERGETIC = "energetic"
        const val VIBE_DEEP = "deep"
    }
}
