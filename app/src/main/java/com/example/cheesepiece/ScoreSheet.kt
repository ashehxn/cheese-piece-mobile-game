package com.example.cheesepiece

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ScoreSheet : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefs"
    private val DEFAULT_SCORE = 0
    private val LEVEL_1_SCORE_KEY = "Level1Score"
    private val LEVEL_2_SCORE_KEY = "Level2Score"
    private val LEVEL_3_SCORE_KEY = "Level3Score"
    private val LEVEL_4_SCORE_KEY = "Level4Score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_sheet)

        val score1 = loadScore1()
        val score2 = loadScore2()
        val score3 = loadScore3()
        val score4 = loadScore4()

        val score1TextView = findViewById<TextView>(R.id.textView9)
        score1TextView.text = "$score1"

        val score2TextView = findViewById<TextView>(R.id.textView11)
        score1TextView.text = "$score2"

        val score3TextView = findViewById<TextView>(R.id.textView13)
        score1TextView.text = "$score3"

        val score4TextView = findViewById<TextView>(R.id.textView15)
        score1TextView.text = "$score4"
    }

    private fun loadScore1(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_1_SCORE_KEY, DEFAULT_SCORE)
    }

    private fun loadScore2(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_2_SCORE_KEY, DEFAULT_SCORE)
    }

    private fun loadScore3(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_3_SCORE_KEY, DEFAULT_SCORE)
    }

    private fun loadScore4(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_4_SCORE_KEY, DEFAULT_SCORE)
    }

}