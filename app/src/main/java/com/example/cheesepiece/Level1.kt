package com.example.cheesepiece

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Level1 : AppCompatActivity() {
    private val occupiedCells = mutableSetOf<Pair<Int, Int>>()
    private val elementToCells = mutableMapOf<View, Set<Pair<Int, Int>>>()
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

        // Add initial occupied cells for each element
        addInitialOccupiedCells(imageView1, 0, 0)
        addInitialOccupiedCells(imageView2, 2, 0)
        addInitialOccupiedCells(imageView3, 2, 0)
        addInitialOccupiedCells(imageView4, 3, 1)
        addInitialOccupiedCells(imageView5, 1, 4)
        addInitialOccupiedCells(imageView6, 3, 1)
        addInitialOccupiedCells(imageView7, 3, 2)

        imageView1.setOnTouchListener(touchListener)
        imageView2.setOnTouchListener(touchListener)
        imageView3.setOnTouchListener(touchListener)
        imageView4.setOnTouchListener(touchListener)
        imageView5.setOnTouchListener(touchListener)
        imageView6.setOnTouchListener(touchListener)
        imageView7.setOnTouchListener(touchListener)
    }

    private fun addInitialOccupiedCells(view: View, startGridX: Int, startGridY: Int) {
        val cells = mutableSetOf<Pair<Int, Int>>()
        val widthInCells = view.layoutParams.width / gridSize
        val heightInCells = view.layoutParams.height / gridSize
        for (i in startGridX until startGridX + widthInCells) {
            for (j in startGridY until startGridY + heightInCells) {
                cells.add(Pair(i, j))
                occupiedCells.add(Pair(i, j))
            }
        }
        elementToCells[view] = cells
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

                // Calculate the new grid position based on the new coordinates
                val newGridX = (newX / gridSize).toInt()
                val newGridY = (newY / gridSize).toInt()

                // Check if the new grid position is occupied
                val isOccupied = isCellOccupied(newGridX, newGridY)

                // If the new position is not occupied or the element is being moved back to its original position,
                // allow the movement
                if (!isOccupied || (newGridX == (initialX / gridSize).toInt() && newGridY == (initialY / gridSize).toInt()))
                {
                    view.x = newX
                    view.y = newY
                }
            }
            MotionEvent.ACTION_UP -> {
                val oldOccupiedCells = elementToCells[view] ?: emptySet()
                oldOccupiedCells.forEach { removeOccupiedCell(it.first, it.second) }

                val newGridX = (view.x / gridSize).toInt()
                val newGridY = (view.y / gridSize).toInt()
                addOccupiedCell(newGridX, newGridY)
                snapToGridPosition(view)

                val newOccupiedCells = generateOccupiedCells(view)
                elementToCells[view] = newOccupiedCells
                newOccupiedCells.forEach { addOccupiedCell(it.first, it.second) }
            }
        }
        true
    }

    private fun isCellOccupied(gridX: Int, gridY: Int): Boolean {
        return Pair(gridX, gridY) in occupiedCells
    }

    private fun addOccupiedCell(gridX: Int, gridY: Int) {
        occupiedCells.add(Pair(gridX, gridY))
    }

    private fun removeOccupiedCell(gridX: Int, gridY: Int) {
        occupiedCells.remove(Pair(gridX, gridY))
    }

    private fun generateOccupiedCells(view: View): Set<Pair<Int, Int>> {
        val newGridX = (view.x / gridSize).toInt()
        val newGridY = (view.y / gridSize).toInt()
        val cells = mutableSetOf<Pair<Int, Int>>()
        for (i in newGridX until newGridX + view.width / gridSize) {
            for (j in newGridY until newGridY + view.height / gridSize) {
                cells.add(Pair(i, j))
            }
        }
        return cells
    }

    private fun snapToGridPosition(view: View) {
        val newGridX = (view.x / gridSize).toInt()
        val newGridY = (view.y / gridSize).toInt()
        view.x = (newGridX * gridSize).toFloat()
        view.y = (newGridY * gridSize).toFloat()
    }
}
