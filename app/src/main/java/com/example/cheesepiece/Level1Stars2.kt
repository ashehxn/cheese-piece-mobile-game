package com.example.cheesepiece

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Level1Stars2 : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefs"
    private val LEVEL_1_SCORE_KEY = "Level1Score"
    private val DEFAULT_SCORE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1_stars2)

        val score = loadScore()

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = "$score Points"
    }

    private fun loadScore(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_1_SCORE_KEY, DEFAULT_SCORE)
    }
}