package com.example.cheesepiece

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.color.utilities.Score

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playBtn = findViewById<Button>(R.id.button7)
        val scoreBtn = findViewById<Button>(R.id.button8)
        val howToPlayBtn = findViewById<Button>(R.id.button12)

        playBtn.setOnClickListener {
            val intent = Intent(this, LevelsMap::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        scoreBtn.setOnClickListener {
            val intent = Intent(this, ScoreSheet::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        howToPlayBtn.setOnClickListener {
            val intent = Intent(this, Instructions1::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}