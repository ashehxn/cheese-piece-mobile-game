package com.example.cheesepiece

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import android.util.Log

class Level1 : AppCompatActivity() {
    companion object {
        private const val MAX_GRID_WIDTH = 5
        private const val MAX_GRID_HEIGHT = 5
    }

    private var offsetX = 0f
    private var offsetY = 0f

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

        // Find image views and set touch listeners
        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val imageView6 = findViewById<ImageView>(R.id.imageView6)
        val imageView7 = findViewById<ImageView>(R.id.imageView7)

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


        allImageViewPositions.addAll(imageView1Position)
        allImageViewPositions.addAll(imageView2Position)
        allImageViewPositions.addAll(imageView3Position)
        allImageViewPositions.addAll(imageView4Position)
        allImageViewPositions.addAll(imageView5Position)
        allImageViewPositions.addAll(imageView6Position)
        allImageViewPositions.addAll(imageView7Position)

        Log.d("allImageViewPositions1", allImageViewPositions.toString())

        setTouchListener(imageView1)
        setTouchListener(imageView2)
        setTouchListener(imageView3)
        setTouchListener(imageView4)
        setTouchListener(imageView5)
        setTouchListener(imageView6)
        setTouchListener(imageView7)
    }

    private fun setTouchListener(view: View) {
        view.setOnTouchListener(touchListener)
    }

    private val touchListener = View.OnTouchListener { view, event ->

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                offsetX = event.rawX - view.x
                offsetY = event.rawY - view.y
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.rawX - offsetX
                val newY = event.rawY - offsetY

                val tempX = newX
                val tempY = newY

                if (isWithinBounds(newX, newY, view.width, view.height)) {
                    view.x = newX
                    view.y = newY

                    val newCoveredCells = getCoveredGridCells(view)

                    when (view) {
                        findViewById<ImageView>(R.id.imageView1) -> imageView1Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView2) -> imageView2Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView3) -> imageView3Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView4) -> imageView4Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView5) -> imageView5Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView6) -> imageView6Position = newCoveredCells.toMutableSet()
                        findViewById<ImageView>(R.id.imageView7) -> imageView7Position = newCoveredCells.toMutableSet()
                    }
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
            MotionEvent.ACTION_UP -> {
                snapToGridPosition(view)
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
        return x >= 0 && y >= 0 && x + width <= MAX_GRID_WIDTH * gridSize && y + height <= MAX_GRID_HEIGHT * gridSize
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
        for (i in startColumn..<endColumn) {
            for (j in startRow..<endRow) {
                coveredCells.add(Pair(i, j))
            }
        }
        return coveredCells
    }
}
