package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Input
import com.usu.rougelikev2.game.gameengine.Location


class Player(game: Game?) : GameObject(game!!) {
    override fun update(elapsedTime: Long) {
        val isAlive = state.get<Boolean>("alive")
        if (!isAlive) return
        if (game.gameState.get<String>("turn") === "player" && !game.inputQueue.isEmpty()) {
            val input: Input = game.inputQueue.removeAt(0)
            game.clearInputQueue()
            val cellSize = game.gameState.get<Int>("cellSize")
            val numRows = game.gameState.get<Int>("numRows")
            val maxY = cellSize * numRows
            if (input.location.y >= maxY) return
            val row = input.location.y.toInt() / cellSize
            val col = input.location.x.toInt() / cellSize
            val myLocation: Location = state["coords"]
            // check if neighbor
            if (myLocation.x.toInt() == col && (myLocation.y.toInt() == row + 1 || myLocation.y.toInt() == row - 1) ||
                myLocation.y.toInt() == row && (myLocation.x.toInt() == col + 1 || myLocation.x.toInt() == col - 1)
            ) {
                //check what we tapped on
                val map = game.gameState.get<Array<Array<GameObject?>>>("map")
                if (map[row][col] is Barrier) return  // don't move to barriers
                game.gameState["endTurn"] = true
                if (map[row][col] == null) {
                    map[row][col] = this
                    map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                    myLocation.x = col.toFloat()
                    myLocation.y = row.toFloat()
                }
                else if (map[row][col] is Key) {
                    map[row][col]!!.state["active"] = false
                    state["hasKey"] = true
                    map[row][col] = null
                }
                else if (map[row][col] is Monster) {
                    map[row][col]!!.state["alive"] = false
                    map[row][col] = null
                }
                else if (map[row][col] is BossMonster) {
                    val health = map[row][col]!!.state.get<Int>("health")
                    state["hasKey"] = true
                    if (health == 1) { //
                        map[row][col]!!.state["alive"] = false
                    } else {
                        map[row][col]!!.state["health"] = health - 1
                    }
                }
                val hasKey = state.get<Boolean>("hasKey")
                if (map[row][col] is Door && hasKey) {
                    game.gameState["nextLevel"] = true
                }
            }
        }
    }

    override fun render(canvas: Canvas, paint: Paint) {
//        things you can do in this render method for reference.
//        val coords: Location = state["coords"] // gets the location of the object in the grid
//        val cellSize: Int = game.gameState["cellSize"] // gets the size of each cell in the game
//        val myX = coords.x * cellSize // gets the current x position (in pixels) in screen space
//        val myY = coords.y * cellSize // gets the current y position (in pixels) in screen space
//        val alive: Boolean = state["alive"] // get whether or not the player is alive
//        val hasKey: Boolean = state["hasKey"]; // get whether the player has the key or not
        val coords: Location = state["coords"]
        val cellSize: Int = game.gameState["cellSize"]
        val myX = coords.x * cellSize
        val myY = coords.y * cellSize

        // TODO: Draw something interesting that represents the player

        // TODO: Draw something interesting that represents the player
        canvas.translate(myX, myY)
        paint.color = Color.rgb(255,255,255)
        canvas.drawCircle(cellSize.toFloat() * .5f, cellSize.toFloat() * .2f, cellSize.toFloat() * .2f,paint) // hat

        paint.color = Color.rgb(255, 229,204)
        canvas.drawCircle(cellSize.toFloat() * .5f, cellSize.toFloat() * .2f, cellSize.toFloat() * .15f,paint) // head
        canvas.drawRect(cellSize.toFloat() * .2f, cellSize.toFloat() * .3f,cellSize.toFloat() * .25f, cellSize.toFloat() * .7f, paint ) // left arm
        canvas.drawRect(cellSize.toFloat() * .75f, cellSize.toFloat() * .3f,cellSize.toFloat() * .8f, cellSize.toFloat() * .7f, paint ) // right arm
        canvas.drawRect(cellSize.toFloat() * .3f, cellSize.toFloat() * .8f,cellSize.toFloat() * .35f, cellSize.toFloat(), paint ) // left leg
        canvas.drawRect(cellSize.toFloat() * .65f, cellSize.toFloat() * .8f,cellSize.toFloat() * .7f, cellSize.toFloat(), paint ) // right leg



        paint.color = Color.rgb(102,178,255)
        canvas.drawRect(cellSize.toFloat() * .25f, cellSize.toFloat() * .3f, cellSize.toFloat() * .75f, cellSize.toFloat() * .55f, paint) //torso
        paint.color = Color.rgb(0,102,204)
        canvas.drawRect(cellSize.toFloat() * .25f, cellSize.toFloat() * .55f, cellSize.toFloat() * .75f, cellSize.toFloat() * .8f, paint) // legs


    }

    init {
        state["hasKey"] = false
        state["alive"] = true
    }
}
