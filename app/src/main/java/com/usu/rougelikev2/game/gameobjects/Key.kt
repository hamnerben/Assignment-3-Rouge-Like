package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Location


class Key(game: Game?) : GameObject(game!!) {
    override fun render(canvas: Canvas, paint: Paint) {
//        things you can do in this render method for reference.
//        val coords: Location = state["coords"] // gets the location of the object in the grid
//        val cellSize: Int = game.gameState["cellSize"] // gets the size of each cell in the game
//        val myX = coords.x * cellSize // gets the current x position (in pixels) in screen space
//        val myY = coords.y * cellSize // gets the current y position (in pixels) in screen space
//        val isActive: Boolean = state.get("active") // get whether the key is active or not (not active means the player already picked it up)
        val isActive: Boolean = state["active"]
        if (!isActive) return
        val coords: Location = state["coords"]
        val cellSize: Int = game.gameState["cellSize"]
        val myX = coords.x * cellSize
        val myY = coords.y * cellSize

        canvas.translate(myX, myY)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas.drawCircle(
            (cellSize / 2).toFloat(),
            (cellSize / 2).toFloat(),
            (cellSize / 2).toFloat(),
            paint
        )
    }

    init {
        state["active"] = true
    }
}