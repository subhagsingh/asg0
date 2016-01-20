package games

class SixNeighbors extends GameOfLife {
	def play() {
		for(i <- 0 to size-1) {
			for(j <- 0 to size-1) {
				nextGenGrid(i)(j) = grid(i)(j)	//Copy unchanged cells over generations
				var aliveNeighborsCount = 0
				aliveNeighborsCount += processNeighbor(i-1, j)
				aliveNeighborsCount += processNeighbor(i+1, j)
				aliveNeighborsCount += processNeighbor(i, j-1)
				aliveNeighborsCount += processNeighbor(i, j+1)
				var col = if(i%2 != 0) j+1 else j-1
				aliveNeighborsCount += processNeighbor(i-1, col)
				aliveNeighborsCount += processNeighbor(i+1, col)	
				print(aliveNeighborsCount + " ")
				if(grid(i)(j)) {		//Alive cell
					if(aliveNeighborsCount < 2 || aliveNeighborsCount > 3)
						nextGenGrid(i)(j) = false
				}
				else 					//Dead cell
						nextGenGrid(i)(j) = (aliveNeighborsCount == 3)
			}
			print("| ")
		}
	}
}
