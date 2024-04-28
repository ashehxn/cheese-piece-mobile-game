package com.example.cheesepiece

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Level1Stars3 : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefs"
    private val LEVEL_1_SCORE_KEY = "Level1Score"
    private val DEFAULT_SCORE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1_stars3)

        val score = loadScore()

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = "$score Points"

        val replayBtn = findViewById<Button>(R.id.button)
        val nextLvlBtn = findViewById<Button>(R.id.button2)
        val levelMapBtn = findViewById<Button>(R.id.button3)
        val quitBtn = findViewById<Button>(R.id.button4)

        replayBtn.setOnClickListener {
            val intent = Intent(this, Level1::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        nextLvlBtn.setOnClickListener {
            val intent = Intent(this, Level2::class.java)
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
        return sharedPreferences.getInt(LEVEL_1_SCORE_KEY, DEFAULT_SCORE)
    }
}