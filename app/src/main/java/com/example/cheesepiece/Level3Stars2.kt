package com.example.cheesepiece

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Level3Stars2 : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefs"
    private val LEVEL_3_SCORE_KEY = "Level3Score"
    private val DEFAULT_SCORE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level3_stars2)

        val score = loadScore()

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = "$score Points"

        val replayBtn = findViewById<Button>(R.id.button)
        val nextLvlBtn = findViewById<Button>(R.id.button2)
        val levelMapBtn = findViewById<Button>(R.id.button3)
        val quitBtn = findViewById<Button>(R.id.button4)

        replayBtn.setOnClickListener {
            val intent = Intent(this, Level3::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        nextLvlBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        levelMapBtn.setOnClickListener {
            val intent = Intent(this, LevelsMap::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        quitBtn.setOnClickListener {
            finishAffinity()
        }
    }

    private fun loadScore(): Int {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LEVEL_3_SCORE_KEY, DEFAULT_SCORE)
    }
}