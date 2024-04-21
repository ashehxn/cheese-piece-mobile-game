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
        // Maximum number of grid cells horizontally and vertically
        private const val MAX_GRID_WIDTH = 5 // Adjust according to your grid width
        private const val MAX_GRID_HEIGHT = 5 // Adjust according to your grid height
    }

    // Variables to store initial touch position and offset
    private var initialX = 0f
    private var initialY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    // Size of each grid cell
    private val gridSize by lazy { resources.getDimensionPixelSize(R.dimen.box_size) }

    // Variables to store positions of image views
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

        imageView1Position = getCoveredGridCells(imageView1).toMutableSet()
        imageView2Position = getCoveredGridCells(imageView2).toMutableSet()
        imageView3Position = getCoveredGridCells(imageView3).toMutableSet()
        imageView4Position = getCoveredGridCells(imageView4).toMutableSet()
        imageView5Position = getCoveredGridCells(imageView5).toMutableSet()
        imageView6Position = getCoveredGridCells(imageView6).toMutableSet()
        imageView7Position = getCoveredGridCells(imageView7).toMutableSet()


        allImageViewPositions.addAll(imageView1Position)
        allImageViewPositions.addAll(imageView2Position)
        allImageViewPositions.addAll(imageView3Position)
        allImageViewPositions.addAll(imageView4Position)
        allImageViewPositions.addAll(imageView5Position)
        allImageViewPositions.addAll(imageView6Position)
        allImageViewPositions.addAll(imageView7Position)

        Log.d("Tag", allImageViewPositions.toString())

        setTouchListener(imageView1)
        setTouchListener(imageView2)
        setTouchListener(imageView3)
        setTouchListener(imageView4)
        setTouchListener(imageView5)
        setTouchListener(imageView6)
        setTouchListener(imageView7)
    }

    // Set touch listener for each image view
    private fun setTouchListener(view: View) {
        view.setOnTouchListener(touchListener)
    }

    // Touch listener to handle moving and snapping of image views
    // Touch listener to handle moving and snapping of image views
    private val touchListener = View.OnTouchListener { view, event ->
        // Define overlappingCells here so it's accessible throughout the scope of the touchListener
        var overlappingCells: Set<Pair<Int, Int>> = emptySet()

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialX = view.x
                initialY = view.y
                offsetX = event.rawX - view.x
                offsetY = event.rawY - view.y
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.rawX - offsetX
                val newY = event.rawY - offsetY

                // Check if the move is within bounds
                if (isWithinBounds(newX, newY, view.width, view.height)) {
                    // Check if the move overlaps with occupied grid cells
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




                    overlappingCells = newCoveredCells.intersect(allImageViewPositions)

                    // If there are no overlapping cells, allow the move
                    if (overlappingCells.isEmpty()) {
                        view.x = newX
                        view.y = newY

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
                // Clear overlappingCells here after the move is complete
                overlappingCells = emptySet()
            }
        }
        true
    }



    private fun snapToGridPosition(view: View) {
        val newGridX = ((view.x + gridSize / 2) / gridSize).toInt()
        val newGridY = ((view.y + gridSize / 2) / gridSize).toInt()

        // Calculate the actual position after snapping to the grid
        val snappedX = newGridX * gridSize
        val snappedY = newGridY * gridSize

        // Snap to the nearest grid position
        view.x = snappedX.toFloat()
        view.y = snappedY.toFloat()
    }

    // Check if the view's new position is within the bounds of the grid
    private fun isWithinBounds(x: Float, y: Float, width: Int, height: Int): Boolean {
        return x >= 0 && y >= 0 && x + width <= MAX_GRID_WIDTH * gridSize && y + height <= MAX_GRID_HEIGHT * gridSize
    }

    // Function to get all grid cells covered by an ImageView
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
}
