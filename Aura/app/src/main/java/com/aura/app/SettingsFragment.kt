package com.aura.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aura.app.HomeFragment.Companion.KEY_VIBE
import com.aura.app.HomeFragment.Companion.PREFS_NAME
import com.aura.app.HomeFragment.Companion.VIBE_CALM
import com.aura.app.HomeFragment.Companion.VIBE_DEEP
import com.aura.app.HomeFragment.Companion.VIBE_ENERGETIC
import com.aura.app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentVibe = prefs.getString(KEY_VIBE, VIBE_CALM)

        val checkedId = when (currentVibe) {
            VIBE_CALM -> R.id.radio_calm
            VIBE_ENERGETIC -> R.id.radio_energetic
            VIBE_DEEP -> R.id.radio_deep
            else -> R.id.radio_calm
        }
        binding.radioGroup.check(checkedId)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val vibe = when (checkedId) {
                R.id.radio_calm -> VIBE_CALM
                R.id.radio_energetic -> VIBE_ENERGETIC
                R.id.radio_deep -> VIBE_DEEP
                else -> VIBE_CALM
            }
            prefs.edit().putString(KEY_VIBE, vibe).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
