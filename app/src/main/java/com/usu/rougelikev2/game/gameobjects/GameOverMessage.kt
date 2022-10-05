package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject


class GameOverMessage(game: Game?) : GameObject(game!!) {
    override fun render(canvas: Canvas, paint: Paint) {
        // this object is a little different than the others as is doesn't have a position inherently.
        // its position is determined at render time.
        // here are some of the things that will be useful for you.
        // game.height // get the height of the game
        // game.width; // get the width of the game
        val isPlaying: Boolean = game.gameState["playing"]
        if (isPlaying) return
        paint.color = Color.RED
        paint.textSize = 100f
        canvas.drawText("GAME OVER", 30f, game.height / 2, paint)
    }
}