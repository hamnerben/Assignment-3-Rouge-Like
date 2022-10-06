package com.usu.rougelikev2.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.usu.rougelikev2.game.gameengine.Game
import com.usu.rougelikev2.game.gameengine.GameObject
import com.usu.rougelikev2.game.gameengine.Location
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt


class Monster(game: Game?) : GameObject(game!!) {
    var turnNumber = 0
    override fun update(elapsedTime: Long) {
        val isAlive = state.get<Boolean>("alive")
        val turn = game.gameState.get<String>("turn")
        if (turn !== "monster") return
        game.gameState["endTurn"] = true
        if (!isAlive) return
        turnNumber++
        if (turnNumber % 4 == 0) {
            return
        }
        val map = game.gameState.get<Array<Array<GameObject?>>>("map")
        if (checkForPlayer() < 5) {
            val player = game.getGameObjectWithTag("player")
            val playerLocation: Location = player!!.state.get("coords")
            val myLocation: Location = state.get("coords")
            if (myLocation.x != playerLocation.x && myLocation.y != playerLocation.y) {
                if (myLocation.y < playerLocation.y) {
                    val other = map[myLocation.y.toInt() + 1][myLocation.x.toInt()]
                    if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.y = myLocation.y + 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                if (myLocation.y > playerLocation.y) {
                    val other = map[myLocation.y.toInt() - 1][myLocation.x.toInt()]
                    if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.y = myLocation.y - 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                if (myLocation.x < playerLocation.x) {
                    val other = map[myLocation.y.toInt()][myLocation.x.toInt() + 1]
                    if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt() + 1] = null
                        myLocation.x = myLocation.x + 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                if (myLocation.x > playerLocation.x) {
                    val other = map[myLocation.y.toInt()][myLocation.x.toInt() - 1]
                    if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.x = myLocation.x - 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                moveRandom()
            } else if (myLocation.x === playerLocation.x) { // same column
                if (myLocation.y < playerLocation.y) {
                    val other = map[myLocation.y.toInt() + 1][myLocation.x.toInt()]
                    if (other is Player) {
                        // end the game
                        other.state["alive"] = false
                        game.gameState["playing"] = false
                        return
                    } else if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.y = myLocation.y + 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                if (myLocation.y > playerLocation.y) {
                    val other = map[myLocation.y.toInt() - 1][myLocation.x.toInt()]
                    if (other is Player) {
                        // end the game
                        other.state["alive"] = false
                        game.gameState["playing"] = false
                        return
                    } else if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.y = myLocation.y - 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                moveRandom()
            } else if (myLocation.y === playerLocation.y) { // same row
                if (myLocation.x < playerLocation.x) {
                    val other = map[myLocation.y.toInt()][myLocation.x.toInt() + 1]
                    if (other is Player) {
                        // end the game
                        other.state["alive"] = false
                        game.gameState["playing"] = false
                        return
                    } else if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.x = myLocation.x + 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                if (myLocation.x > playerLocation.x) {
                    val other = map[myLocation.y.toInt()][myLocation.x.toInt() - 1]
                    if (other is Player) {
                        // end the game
                        other.state["alive"] = false
                        game.gameState["playing"] = false
                        return
                    } else if (other == null) {
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                        myLocation.x = myLocation.x - 1
                        map[myLocation.y.toInt()][myLocation.x.toInt()] = this
                        return
                    }
                }
                moveRandom()
            }
        } else {
            moveRandom()
        }
    }

    private fun moveRandom() {
        val neighbors = mutableListOf<Int>()
        neighbors.add(1)
        neighbors.add(2)
        neighbors.add(3)
        neighbors.add(4)
        neighbors.shuffle()
        val map = game.gameState.get<Array<Array<GameObject?>>>("map")
        val myLocation: Location = state.get("coords")
        while (!neighbors.isEmpty()) {
            val `val` = neighbors.removeAt(0)
            if (`val` == 1) {
                if (myLocation.y > 0 && map[myLocation.y.toInt() - 1][myLocation.x.toInt()] == null) {
                    map[myLocation.y.toInt() - 1][myLocation.x.toInt()] = this
                    map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                    myLocation.y = myLocation.y - 1
                    return
                }
            }
            if (`val` == 2) {
                if (myLocation.x < map[0].size - 1 && map[myLocation.y.toInt()][myLocation.x.toInt() + 1] == null) {
                    map[myLocation.y.toInt()][myLocation.x.toInt() + 1] = this
                    map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                    myLocation.x = myLocation.x + 1
                    return
                }
            }
            if (`val` == 3) {
                if (myLocation.y < map.size - 1 && map[myLocation.y.toInt() + 1][myLocation.x.toInt()] == null) {
                    map[myLocation.y.toInt() + 1][myLocation.x.toInt()] = this
                    map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                    myLocation.y = myLocation.y + 1
                    return
                }
            }
            if (`val` == 4) {
                if (myLocation.x > 0 && map[myLocation.y.toInt()][myLocation.x.toInt() - 1] == null) {
                    map[myLocation.y.toInt()][myLocation.x.toInt() - 1] = this
                    map[myLocation.y.toInt()][myLocation.x.toInt()] = null
                    myLocation.x = myLocation.x - 1
                    return
                }
            }
        }
    }

    private fun checkForPlayer(): Double {
        val player = game.getGameObjectWithTag("player")
        val playerLocation: Location = player!!.state.get("coords")
        val myLocation: Location = state.get("coords")
        return sqrt(
            (playerLocation.x - myLocation.x).toDouble().pow(2.0) + (playerLocation.y - myLocation.y).toDouble()
                .pow(2.0)
        )
    }

    override fun render(canvas: Canvas, paint: Paint) {
//        things you can do in this render method for reference.
//        val coords: Location = state["coords"] // gets the location of the object in the grid
//        val cellSize: Int = game.gameState["cellSize"] // gets the size of each cell in the game
//        val myX = coords.x * cellSize // gets the current x position (in pixels) in screen space
//        val myY = coords.y * cellSize // gets the current y position (in pixels) in screen space
//        val alive: Boolean = state["alive"] // get whether or not the monster is alive
        val coords: Location = state["coords"]
        val cellSize: Int = game.gameState["cellSize"]
        val myX = coords.x.toInt() * cellSize
        val myY = coords.y.toInt() * cellSize
        val isAlive: Boolean = state["alive"]

        canvas.translate(myX.toFloat(), myY.toFloat())
            val BLUEHEADR = cellSize.toFloat() * .25f
        if (isAlive) {
            val BLUEHEADX = cellSize.toFloat() * .5f
            val BLUEHEADY = cellSize.toFloat() * .5f

            val BLUEBODLEFT = BLUEHEADX - BLUEHEADR
            val BlUEBODTOP = BLUEHEADY
            val BLUEBODRIGHT = BLUEHEADX + BLUEHEADR
            val BLUEBODBOTTOM = cellSize.toFloat()

            val WHITEX = BLUEHEADX * .85f
            val WHITEY = BLUEHEADY * 1.1f
            val WHITER = BLUEHEADR * .7f

            val WHITEBODRIGHT = BLUEBODRIGHT * .8f

            val LEFTEYEX = WHITEX * .7f
            val EYEY = WHITEY
            val RIGHTEYEX = WHITEX * 1.1f
            val EYER = 7f

            val BEAKLEFT = LEFTEYEX * .7f
            val BEAKTOP = EYEY * 1.1f
            val BEAKRIGHT = WHITEX * .9f
            val BEAKBOTTOM = EYEY * 1.2f

            paint.color = Color.rgb(0,51,102)
            canvas.drawCircle(BLUEHEADX, BLUEHEADY, BLUEHEADR, paint)
            canvas.drawRect(BLUEBODLEFT, BlUEBODTOP, BLUEBODRIGHT, BLUEBODBOTTOM, paint)



            paint.color = Color.rgb(255,255,204)
            canvas.drawCircle(WHITEX, WHITEY, WHITER, paint)
            canvas.drawRect(BLUEBODLEFT, BlUEBODTOP, WHITEBODRIGHT, BLUEBODBOTTOM, paint)

            paint.color = Color.BLACK
            canvas.drawCircle(LEFTEYEX, EYEY, EYER, paint)
            canvas.drawCircle(RIGHTEYEX, EYEY, EYER, paint)

            paint.color = Color.rgb(255,255,102)
            canvas.drawRect(BEAKLEFT, BEAKTOP, BEAKRIGHT, BEAKBOTTOM, paint)

        } else {
            paint.color = Color.CYAN
        }

    }

    init {
        state["alive"] = true
        turnNumber = (Math.random() * 4).toInt()
    }
}