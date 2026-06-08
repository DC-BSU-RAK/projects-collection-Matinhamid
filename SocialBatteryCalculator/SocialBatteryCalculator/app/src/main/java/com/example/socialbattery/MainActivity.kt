package com.example.socialbattery

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private lateinit var tvResult: TextView
    private lateinit var chipGroup: ChipGroup

    private val selectedEvents = mutableListOf<SocialEvent>()

    data class SocialEvent(val label: String, val drainPoints: Int)

    private val socialEvents = listOf(
        SocialEvent("🎉 Party", 9),
        SocialEvent("💼 Office", 6),
        SocialEvent("🏋️ Gym", 3),
        SocialEvent("💘 Date", 7),
        SocialEvent("👨‍👩‍👧 Family", 5),
        SocialEvent("🎤 Speaking", 8),
        SocialEvent("🍻 Bar Night", 8),
        SocialEvent("🎮 Game Night", 4),
        SocialEvent("🛒 Shopping", 3),
        SocialEvent("📞 Phone Call", 2),
        SocialEvent("✈️ Travel", 7),
        SocialEvent("🤝 Networking", 9),
        SocialEvent("🎓 Class", 5),
        SocialEvent("🍽️ Dinner Out", 6),
        SocialEvent("🎬 Cinema", 2),
        SocialEvent("🏖️ Vacation", 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        tvResult = findViewById(R.id.tvResult)
        chipGroup = findViewById(R.id.chipGroupSelected)

        setupEventGrid()

        findViewById<MaterialButton>(R.id.btnCalculate).setOnClickListener {
            calculateRecovery()
        }

        findViewById<MaterialButton>(R.id.btnClear).setOnClickListener {
            clearAll()
        }

        findViewById<MaterialButton>(R.id.btnInfo).setOnClickListener {
            InfoDialogFragment().show(supportFragmentManager, "InfoDialog")
        }
    }

    private fun setupEventGrid() {
        val grid = findViewById<android.widget.GridLayout>(R.id.gridEvents)
        grid.removeAllViews()

        for (event in socialEvents) {
            val btn = MaterialButton(this, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
                text = event.label
                textSize = 11f
                setPadding(8, 8, 8, 8)
                isAllCaps = false
                val params = android.widget.GridLayout.LayoutParams().apply {
                    width = 0
                    height = android.widget.GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f)
                    setMargins(6, 6, 6, 6)
                }
                layoutParams = params
                setOnClickListener { onEventSelected(event) }
            }
            grid.addView(btn)
        }
    }

    private fun onEventSelected(event: SocialEvent) {
        selectedEvents.add(event)

        val chip = Chip(this).apply {
            text = event.label
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                selectedEvents.remove(event)
                chipGroup.removeView(this)
                updateDisplayText()
                if (selectedEvents.isEmpty()) tvResult.visibility = View.GONE
            }
        }
        chipGroup.addView(chip)
        updateDisplayText()
        tvResult.visibility = View.GONE
    }

    private fun updateDisplayText() {
        if (selectedEvents.isEmpty()) {
            tvDisplay.text = getString(R.string.display_placeholder)
        } else {
            tvDisplay.text = selectedEvents.joinToString(" + ") { it.label }
        }
    }

    private fun calculateRecovery() {
        if (selectedEvents.isEmpty()) {
            tvResult.text = getString(R.string.result_empty)
            tvResult.visibility = View.VISIBLE
            return
        }

        val totalDrain = selectedEvents.sumOf { it.drainPoints }

        val (tier, recovery) = when {
            totalDrain <= 5  -> "🟢 Light Buzz"      to "30 min of quiet reading"
            totalDrain <= 12 -> "🟡 Mild Drain"      to "An evening alone, no screens"
            totalDrain <= 20 -> "🟠 Social Fatigue"  to "1 full day of solitude"
            totalDrain <= 30 -> "🔴 Total Burn"      to "2 days of solitude + naps"
            totalDrain <= 45 -> "💀 Extrovert Debt"  to "A long weekend in hermit mode"
            else             -> "☠️ Social Overdose" to "One full week off-grid, immediately"
        }

        tvResult.text = "Drain Score: $totalDrain pts\nResult: $tier\nRecovery: $recovery"
        tvResult.visibility = View.VISIBLE
    }

    private fun clearAll() {
        selectedEvents.clear()
        chipGroup.removeAllViews()
        tvDisplay.text = getString(R.string.display_placeholder)
        tvResult.visibility = View.GONE
    }
}
