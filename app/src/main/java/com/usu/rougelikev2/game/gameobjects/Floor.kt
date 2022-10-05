package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Location


class Floor(game: Game?) : GameObject(game!!) {
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
        paint.style = Paint.Style.FILL
        paint.color = Color.DKGRAY
        paint.strokeWidth = 2f
        canvas.drawRect(0f, 0f, cellSize.toFloat(), cellSize.toFloat(), paint)
        paint.color = Color.GRAY
        canvas.drawCircle(cellSize.toFloat() * 0.4f, cellSize.toFloat() * .3f , cellSize.toFloat()  * .1f, paint)
        canvas.drawCircle(cellSize.toFloat() * .9f, cellSize.toFloat() * .3f , cellSize.toFloat() * .09f, paint)
        canvas.drawCircle(cellSize.toFloat() * .1f, cellSize.toFloat() * .1f , cellSize.toFloat() * .09f, paint)
        canvas.drawCircle(cellSize.toFloat() * .5f, cellSize.toFloat() * .6f , cellSize.toFloat() * .15f, paint)
        canvas.drawCircle(cellSize.toFloat() * .1f, cellSize.toFloat() * .8f , cellSize.toFloat() * .15f, paint)
        canvas.drawCircle(cellSize.toFloat() * .7f, cellSize.toFloat() * .8f , cellSize.toFloat() * .14f, paint)


    }
}
