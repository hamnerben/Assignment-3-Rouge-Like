package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Location


class BossBarrier(game: Game?) : GameObject(game!!) {
    override fun render(canvas: Canvas, paint: Paint) {
//        things you can do in this render method for reference.
//        val coords: Location = state["coords"] // gets the location of the object in the grid
//        val cellSize: Int = game.gameState["cellSize"] // gets the size of each cell in the game
//        val myX = coords.x * cellSize // gets the current x position (in pixels) in screen space
//        val myY = coords.y * cellSize // gets the current y position (in pixels) in screen space
        val coords: Location = state["coords"]
        val cellSize: Float = game.gameState["cellSize"]
        val myX = coords.x * cellSize
        val myY = coords.y * cellSize

        canvas.translate(myX, myY)
        paint.color = Color.rgb(0,102,204)
        canvas.drawRect(0f, 0f, cellSize.toFloat(), cellSize.toFloat(), paint)
        paint.color = Color.rgb(0,51,102)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        canvas.drawRect(0f, 0f, cellSize.toFloat(), cellSize.toFloat() * .33f, paint)
        canvas.drawRect(0f, cellSize.toFloat() * .33f, cellSize.toFloat(), cellSize.toFloat() * .66f, paint)
        canvas.drawRect(0f, cellSize.toFloat() * .66f, cellSize.toFloat(), cellSize.toFloat(), paint)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cellSize.toFloat() * .1f, cellSize.toFloat() * .16f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .1f, cellSize.toFloat() * .49f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .1f, cellSize.toFloat() * .84f, cellSize.toFloat() * .05f, paint)

        canvas.drawCircle(cellSize.toFloat() * .9f, cellSize.toFloat() * .16f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .9f, cellSize.toFloat() * .49f, cellSize.toFloat() * .05f, paint)
        canvas.drawCircle(cellSize.toFloat() * .9f, cellSize.toFloat() * .84f, cellSize.toFloat() * .05f, paint)


    }
}