package com.example.cheesepiece

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import java.util.concurrent.TimeUnit
import android.os.Handler
import android.content.Intent


class Level1 : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var elapsedTimeInMillis: Long = 0
    private var isTimerRunning = false

    private var offsetX = 0f
    private var offsetY = 0f

    private val PREFS_NAME = "MyPrefs"
    private val LEVEL_1_SCORE_KEY = "Level1Score"
    private val DEFAULT_SCORE = 0


    private val gridSize by lazy { resources.getDimensionPixelSize(R.dimen.box_size) }

    private var imageView1Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView2Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView3Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView4Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView5Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView6Position = mutableSetOf<Pair<Int, Int>>()
    private var imageView7Position = mutableSetOf<Pair<Int, Int>>()
    private var allImageViewPositions = mutableSetOf<Pair<Int, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        // Initialize views
        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val winningBox = findViewById<ImageView>(R.id.winningBox)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val imageView6 = findViewById<ImageView>(R.id.imageView6)
        val imageView7 = findViewById<ImageView>(R.id.imageView7)

        // Initialize image positions
        imageView1Position = mutableSetOf(
            Pair(0, 0),
            Pair(1, 0)
        )
        imageView2Position = mutableSetOf(
            Pair(2, 0),
            Pair(2, 1)
        )
        imageView3Position = mutableSetOf(
            Pair(0, 1),
            Pair(0, 2),
            Pair(0, 3),
            Pair(0, 4)
        )
        imageView4Position = mutableSetOf(
            Pair(1, 1),
            Pair(1, 2),
            Pair(1, 3)
        )
        imageView5Position = mutableSetOf(
            Pair(1, 4),
            Pair(2, 4),
            Pair(3, 4),
            Pair(4, 4)
        )
        imageView6Position = mutableSetOf(
            Pair(3, 1),
            Pair(4, 1)
        )
        imageView7Position = mutableSetOf(
            Pair(3, 2),
            Pair(3, 3)
        )

        // Add all positions to the set of all positions
        allImageViewPositions.addAll(imageView1Position)
        allImageViewPositions.addAll(imageView2Position)
        allImageViewPositions.addAll(imageView3Position)
        allImageViewPositions.addAll(imageView4Position)
        allImageViewPositions.addAll(imageView5Position)
        allImageViewPositions.addAll(imageView6Position)
        allImageViewPositions.addAll(imageView7Position)

        // Set touch listeners for all ImageViews
        setTouchListener(imageView1)
        setTouchListener(winningBox)
        setTouchListener(imageView3)
        setTouchListener(imageView4)
        setTouchListener(imageView5)
        setTouchListener(imageView6)
        setTouchListener(imageView7)

        // Start the timer
        startTimer()
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    elapsedTimeInMillis += 1000
                    updateTimerText()
                }

                override fun onFinish() {
                    // Timer finished
                }
            }
            timer.start()
            isTimerRunning = true
        }
    }

    private fun stopTimer() {
        if (isTimerRunning) {
            timer.cancel()
            isTimerRunning = false
        }
    }

    private fun updateTimerText() {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis)
        val timeString = String.format("%02d", seconds)

        findViewById<TextView>(R.id.time).text = timeString
    }

    private fun saveScore(score: Int) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(LEVEL_1_SCORE_KEY, score)
        editor.apply()
    }

    private fun setTouchListener(view: View) {
        view.setOnTouchListener(touchListener)
    }

    private val touchListener = View.OnTouchListener { view, event ->
        var overlappingCells: Set<Pair<Int, Int>> = emptySet()

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                offsetX = event.rawX - view.x
                offsetY = event.rawY - view.y
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.rawX - offsetX
                val newY = event.rawY - offsetY

                var tempX = newX
                var tempY = newY

                if (isWithinBounds(newX, newY, view.width, view.height)) {
                    view.x = newX
                    view.y = newY

                    val newCoveredCells = getCoveredGridCells(view)

                    when (view) {
                        findViewById<ImageView>(R.id.imageView1) -> imageView1Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.winningBox) -> imageView2Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView3) -> imageView3Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView4) -> imageView4Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView5) -> imageView5Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView6) -> imageView6Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView7) -> imageView7Position = newCoveredCells.toMutableSet()
                    }

                    allImageViewPositions.clear()
                    allImageViewPositions.addAll(imageView1Position)
                    allImageViewPositions.addAll(imageView2Position)
                    allImageViewPositions.addAll(imageView3Position)
                    allImageViewPositions.addAll(imageView4Position)
                    allImageViewPositions.addAll(imageView5Position)
                    allImageViewPositions.addAll(imageView6Position)
                    allImageViewPositions.addAll(imageView7Position)

                    overlappingCells = newCoveredCells.intersect(allImageViewPositions)

                    if (overlappingCells.isNotEmpty()) {
                        view.x = tempX
                        view.y = tempY

                        allImageViewPositions.clear()
                        allImageViewPositions.addAll(imageView1Position)
                        allImageViewPositions.addAll(imageView2Position)
                        allImageViewPositions.addAll(imageView3Position)
                        allImageViewPositions.addAll(imageView4Position)
                        allImageViewPositions.addAll(imageView5Position)
                        allImageViewPositions.addAll(imageView6Position)
                        allImageViewPositions.addAll(imageView7Position)
                    }

                }
            }
            MotionEvent.ACTION_UP -> {
                snapToGridPosition(view)
                if (view.id == R.id.winningBox) {
                    checkWinningCondition()
                }
            }
        }
        true
    }

    private fun snapToGridPosition(view: View) {
        val newGridX = ((view.x + gridSize / 2) / gridSize).toInt()
        val newGridY = ((view.y + gridSize / 2) / gridSize).toInt()

        val snappedX = newGridX * gridSize
        val snappedY = newGridY * gridSize

        view.x = snappedX.toFloat()
        view.y = snappedY.toFloat()
    }

    private fun isWithinBounds(x: Float, y: Float, width: Int, height: Int): Boolean {
        return x >= 0 && y >= 0 && x + width <= 5 * gridSize && y + height <= 5 * gridSize
    }

    private fun getCoveredGridCells(view: View): Set<Pair<Int, Int>> {
        val parent = view.parent as? GridLayout ?: return emptySet()
        val cellWidth = parent.width / parent.columnCount
        val cellHeight = parent.height / parent.rowCount

        val startColumn = (view.x / cellWidth).toInt()
        val startRow = (view.y / cellHeight).toInt()

        val endColumn = ((view.x + view.width) / cellWidth).toInt()
        val endRow = ((view.y + view.height) / cellHeight).toInt()

        val coveredCells = mutableSetOf<Pair<Int, Int>>()
        for (i in startColumn..endColumn-1) {
            for (j in startRow..endRow-1) {
                coveredCells.add(Pair(i, j))
            }
        }
        return coveredCells
    }

    private fun calculateStarsAndScore(seconds: Long): Pair<Int, Int> {
        val stars: Int
        val score: Int

        stars = when {
            seconds < 30 -> 3
            seconds < 60 -> 2
            else -> 1
        }

        score = when (stars) {
            3 -> 300
            2 -> 200
            else -> 100
        }

        return Pair(stars, score)
    }

    private fun checkWinningCondition() {
        val winCells = setOf(Pair(4, 3), Pair(4, 4))
        if (allImageViewPositions.containsAll(winCells)) {
            stopTimer() // Stop the timer when the player wins
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis)
            val (stars, score) = calculateStarsAndScore(seconds)

            saveScore(score)

            Toast.makeText(this, "Congratulations! You won with $stars stars! Score: $score", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                when (stars) {
                    3 -> startActivity(Intent(this, Level1Stars3::class.java))
                    2 -> startActivity(Intent(this, Level1Stars2::class.java))
                    else -> startActivity(Intent(this, Level1Stars1::class.java))
                }
            }, 3000)
        }
    }

}
