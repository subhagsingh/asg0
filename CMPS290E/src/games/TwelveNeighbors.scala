package games
/* 
 * This class implements play() method of GameOfLife for 12 neighbor version of the game.
 */
class TwelveNeighbors extends GameOfLife {
  def play() {
    for(i <- 0 to size-1) {
      for(j <- 0 to size-1) {
        nextGenGrid(i)(j) = grid(i)(j)  //Copy unchanged cells over generations
        //First tier neighbors
        var aliveFirstNeighborsCount = 0
        aliveFirstNeighborsCount += processNeighbor(i-1, j)
        aliveFirstNeighborsCount += processNeighbor(i+1, j)
        aliveFirstNeighborsCount += processNeighbor(i, j-1)
        aliveFirstNeighborsCount += processNeighbor(i, j+1)
        val col = if(i%2 != 0) j+1 else j-1
        aliveFirstNeighborsCount += processNeighbor(i-1, col)
        aliveFirstNeighborsCount += processNeighbor(i+1, col)  
        //Second tier neighbors
        var aliveSecondNeighborsCount = 0
        aliveSecondNeighborsCount += processNeighbor(i-2, j)
        aliveSecondNeighborsCount += processNeighbor(i+2, j)
        val jOnLeft = if(i%2 != 0) j-1 else j-2
        val jOnRight = if(i%2 != 0) j+2 else j+1
        aliveSecondNeighborsCount += processNeighbor(i-1, jOnLeft)
        aliveSecondNeighborsCount += processNeighbor(i+1, jOnLeft)
        aliveSecondNeighborsCount += processNeighbor(i-1, jOnRight)
        aliveSecondNeighborsCount += processNeighbor(i+1, jOnRight)
        //Sum
        val neighborSum = 1.0 * aliveFirstNeighborsCount + 0.3 * aliveSecondNeighborsCount
        print("(" + aliveFirstNeighborsCount + "," + aliveSecondNeighborsCount + "," + neighborSum + ")")
        if(grid(i)(j)) {  //Alive cell
          if(neighborSum < 2.0 || neighborSum > 3.3)
            nextGenGrid(i)(j) = false
        }
        else {          //Dead cell
          nextGenGrid(i)(j) = (2.3 <= neighborSum && neighborSum <= 2.9)
        }              
      }
      print("| ")
    }
  }
}
