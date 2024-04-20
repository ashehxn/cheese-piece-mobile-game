package com.example.cheesepiece

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Level1 : AppCompatActivity() {
    companion object {
        private const val GRID_WIDTH = 5 // Adjust according to your grid width
        private const val GRID_HEIGHT = 5 // Adjust according to your grid height
    }

    private val occupiedCells = mutableSetOf<Pair<Int, Int>>()
    private val elementToCells = mutableMapOf<View, MutableSet<Pair<Int, Int>>>()
    private val allPairsForElements = mutableSetOf<Pair<Int, Int>>()
    private var initialX = 0f
    private var initialY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private val gridSize by lazy { resources.getDimensionPixelSize(R.dimen.box_size) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val imageView6 = findViewById<ImageView>(R.id.imageView6)
        val imageView7 = findViewById<ImageView>(R.id.imageView7)

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
        val occupiedCellsForElement = mutableSetOf<Pair<Int, Int>>()
        val positions = findCoveredPositions(view)
        occupiedCellsForElement.addAll(positions)
        elementToCells[view] = occupiedCellsForElement
        allPairsForElements.addAll(occupiedCellsForElement)
    }

    private val touchListener = View.OnTouchListener { view, event ->
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

                // Check if the new position is within bounds
                if (isWithinBounds(newX, newY, view.width, view.height)) {
                    // Update the view's position
                    view.x = newX
                    view.y = newY
                }
            }
            MotionEvent.ACTION_UP -> {
                val oldOccupiedCells = elementToCells[view] ?: emptySet()
                oldOccupiedCells.forEach { removeOccupiedCell(it.first, it.second) }

                val newGridX = ((view.x + gridSize / 2) / gridSize).toInt()
                val newGridY = ((view.y + gridSize / 2) / gridSize).toInt()

                // Add occupied cells for the new position
                val positions = findCoveredPositions(view)
                positions.forEach { addOccupiedCell(it.first, it.second) }

                // Snap the view to the nearest grid position
                snapToGridPosition(view)

                // Update the elementToCells map
                val newOccupiedCells = generateOccupiedCells(view)
                elementToCells[view] = newOccupiedCells.toMutableSet()
                newOccupiedCells.forEach { addOccupiedCell(it.first, it.second) }
            }
        }
        true
    }

    private fun findCoveredPositions(view: View): Set<Pair<Int, Int>> {
        val newGridX = (view.x / gridSize).toInt()
        val newGridY = (view.y / gridSize).toInt()
        val widthInCells = view.width / gridSize
        val heightInCells = view.height / gridSize

        val coveredPositions = mutableSetOf<Pair<Int, Int>>()
        for (i in newGridX until newGridX + widthInCells) {
            for (j in newGridY until newGridY + heightInCells) {
                coveredPositions.add(Pair(i, j))
            }
        }
        return coveredPositions
    }

    private fun isCellOccupied(gridX: Int, gridY: Int): Boolean {
        return Pair(gridX, gridY) in occupiedCells
    }

    private fun addOccupiedCell(gridX: Int, gridY: Int) {
        occupiedCells.add(Pair(gridX, gridY))
        allPairsForElements.add(Pair(gridX, gridY))
    }

    private fun removeOccupiedCell(gridX: Int, gridY: Int) {
        occupiedCells.remove(Pair(gridX, gridY))
        allPairsForElements.remove(Pair(gridX, gridY))
    }

    private fun generateOccupiedCells(view: View): Set<Pair<Int, Int>> {
        val newGridX = (view.x / gridSize).toInt()
        val newGridY = (view.y / gridSize).toInt()
        val cells = mutableSetOf<Pair<Int, Int>>()
        for (i in newGridX until newGridX + view.width / gridSize) {
            for (j in newGridY until newGridY + view.height / gridSize) {
                cells.add(Pair(i, j))
                allPairsForElements.add(Pair(i, j))
            }
        }
        return cells
    }

    private fun snapToGridPosition(view: View) {
        val newGridX = ((view.x + gridSize / 2) / gridSize).toInt()
        val newGridY = ((view.y + gridSize / 2) / gridSize).toInt()

        // Calculate the actual position after snapping to the grid
        val snappedX = newGridX * gridSize
        val snappedY = newGridY * gridSize

        // Check if the snapped position matches the original position
        val epsilon = 0.001f // Small epsilon value to account for floating-point imprecisions
        if (Math.abs(snappedX - view.x) > epsilon || Math.abs(snappedY - view.y) > epsilon) {
            // Snap to the nearest grid position
            view.x = snappedX.toFloat()
            view.y = snappedY.toFloat()
        }
    }


    private fun isWithinBounds(x: Float, y: Float, width: Int, height: Int): Boolean {
        return x >= 0 && y >= 0 && x + width <= GRID_WIDTH * gridSize && y + height <= GRID_HEIGHT * gridSize
    }
}
