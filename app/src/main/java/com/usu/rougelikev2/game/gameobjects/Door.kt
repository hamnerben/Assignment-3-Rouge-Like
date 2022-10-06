package com.usu.rougelikev2.game.gameobjects


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Location


class Door(game: Game?) : GameObject(game!!) {
    override fun render(canvas: Canvas, paint: Paint) {
//        things you can do in this render method for reference.
//        val coords: Location = state["coords"] // gets the location of the object in the grid
//        val cellSize: Int = game.gameState["cellSize"] // gets the size of each cell in the game
//        val myX = coords.x * cellSize // gets the current x position (in pixels) in screen space
//        val myY = coords.y * cellSize // gets the current y position (in pixels) in screen space

        val coords: Location = state["coords"]
        val cellSize: Int = game.gameState["cellSize"]
        val myX = coords.x * cellSize
        val myY = coords.y * cellSize

        canvas.translate(myX, myY)
        paint.color = Color.rgb(103,56,0)
        canvas.drawRect(10f, 10f, (cellSize - 10).toFloat(), (cellSize - 10).toFloat(), paint)
        paint.color = Color.BLACK
        canvas.drawCircle(cellSize.toFloat() * .16f, cellSize.toFloat() * .16f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .16f, cellSize.toFloat() * .84f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .84f, cellSize.toFloat() * .84f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .84f, cellSize.toFloat() * .16f, cellSize.toFloat() * .05f, paint)
        paint.style = Paint.Style.STROKE
        canvas.drawRect(10f, 10f, (cellSize - 10).toFloat(), (cellSize - 10).toFloat(), paint)

    }
}