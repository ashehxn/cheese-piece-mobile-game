package com.example.cheesepiece

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LevelsMap : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels_map)

        val lvl1 = findViewById<Button>(R.id.button1)
        val lvl2 = findViewById<Button>(R.id.button2)
        val lvl3 = findViewById<Button>(R.id.button4)
        val lvl4 = findViewById<Button>(R.id.button3)

        lvl1.setOnClickListener {
            val intent = Intent(this, Level1::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        lvl2.setOnClickListener {
            val intent = Intent(this, Level2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        lvl3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        lvl4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}